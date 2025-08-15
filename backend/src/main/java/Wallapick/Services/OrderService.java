package Wallapick.Services;

import Wallapick.Models.Order;
import Wallapick.Models.Product;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.OrderDTO;
import Wallapick.ModelsDTO.ProductDTO;
import Wallapick.Repositories.OrderRepository;
import Wallapick.Repositories.ProductRepository;
import Wallapick.Repositories.UserRepository;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JWTUser jwtUser;

    @Autowired
    private BlacklistService blacklistService;

    public List<OrderDTO> getOrdersUser(String token) {

        try {

            // JWT token validation is now handled by the JwtRequestFilter
            User user = jwtUser.getUser(token);
            List<Order> orders = orderRepository.findByBuyerId(user.getId());

            // Convert entities to DTO
            return orders.stream()
                    .map(order -> new OrderDTO(order,false)) // Use the constructor with a flag to avoid recursion
                    .toList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<ProductDTO> orderProducts(ArrayList<Long> ids, String token) {

        try {

            // JWT token validation is now handled by the JwtRequestFilter
            User buyer = jwtUser.getUser(token);

            if (!"LOGGED".equals(buyer.getRole())) {
                return null; // User not authorized
            }

            List<ProductDTO> orderedProducts = new ArrayList<>();

            for (Long id : ids) {

                Product product = productRepository.findById(id).orElse(null);

                if (product == null || !product.isForSale()) {
                    return null; // Product not found or not for sale
                }

                User seller = userRepository.findById(product.getSeller().getId()).orElse(null);

                if (seller == null) {
                    return null; // Seller not found
                }

                if(buyer.getId() == seller.getId()) {
                    return null; // Can't order your own product
                }

                Order order = new Order(product, seller, buyer, new Date(), product.getPrice());
                orderRepository.save(order);

                product.setForSale(false); // Mark as sold
                product.setOrder(order); // Assign order
                productRepository.save(product);

                ProductDTO productDTO = new ProductDTO(product);
                orderedProducts.add(productDTO);

            }

            return orderedProducts; // Success

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Error trying to order
        }
    }
}