package Wallapick.Services;

import Wallapick.Models.Order;
import Wallapick.Models.Product;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.OrderDTO;
import Wallapick.Repositories.OrderRepository;
import Wallapick.Repositories.ProductRepository;
import Wallapick.Repositories.UserRepository;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<OrderDTO> getOrdersUser(String token) {

        try {

            User user = jwtUser.getUser(token); // Token validation
            List<Order> orders = orderRepository.findByBuyerId(user.getId());

            // Convert entities to DTO
            return orders.stream()
                    .map(order -> new OrderDTO(order,false)) // Use the constructor with a flag to avoid recursion
                    .toList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public int orderProducts(ArrayList<Long> ids, String token) {

        try {

            User buyer = jwtUser.getUser(token);

            if (!"LOGGED".equals(buyer.getRole())) {
                return 0; // User not authorized
            }

            for (Long id : ids) {

                Product product = productRepository.findById(id).orElse(null);

                if (product == null || !product.isForSale()) {
                    return -1; // Product not found or not for sale
                }

                User seller = userRepository.findById(product.getSeller().getId()).orElse(null);

                if (seller == null) {
                    return -1; // Seller not found
                }

                if(buyer.getId() == seller.getId()) {
                    return -2; // Can't order your own product
                }

                Order order = new Order(product, seller, buyer,  new Date(), product.getPrice());
                orderRepository.save(order);

                product.setForSale(false); // Mark as sold
                product.setOrder(order); // Assign order
                productRepository.save(product);
            }

            return 1; // Success

        } catch (Exception e) {
            e.printStackTrace();
            return 2; // Error trying to order
        }
    }
}