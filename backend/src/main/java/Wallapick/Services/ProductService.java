package Wallapick.Services;

import Wallapick.Models.Product;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.ProductDTO;
import Wallapick.Repositories.OrderRepository;
import Wallapick.Repositories.ProductRepository;
import Wallapick.Repositories.UserRepository;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private JWTUser jwtUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public int crearProducto(Product product, String token) {

        try {

            User user = jwtUser.getUser(token);

            if (user.getRole().equalsIgnoreCase("LOGGED")) {

                User seller = userRepository.findById(user.getId())
                        .orElse(null);

                if (seller == null) {
                    return 0;
                }

                product.setSeller(seller);

                // Calculate price with 30% tax
                BigDecimal originalPrice = BigDecimal.valueOf(product.getPrice());
                BigDecimal tax = originalPrice.multiply(BigDecimal.valueOf(0.30));
                BigDecimal finalPrice = originalPrice.add(tax).setScale(2, RoundingMode.HALF_UP);
                product.setPrice(finalPrice.doubleValue());

                product.setReleaseDate(new Date());
                productRepository.save(product);

                return 1; // Success
            }

            return 0; // User doesn't have the appropriate role

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Intern error
        }
    }

    public List<ProductDTO> searchProductsPartial(String partialName) {

        List<Product> products = productRepository.findByPartialNameContainingIgnoreCase(partialName);

        return products.stream()
                .map(ProductDTO::new) // Use the constructor that converts from Product to ProductDTO
                .toList();
    }

    public List<ProductDTO> searchMyProducts(String token) {

        try {

            User user = jwtUser.getUser(token); // It can throw an exception if the token is invalid

            if ("LOGGED".equals(user.getRole())) {

                List<Product> products = productRepository.findBySellerId(user.getId());

                return products.stream()
                        .map(ProductDTO::new)
                        .toList();

            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<ProductDTO> searchAll() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductDTO::new) // Use the DTO constructor
                .toList();
    }

    public int updateProduct(Product product, String token) {

        try {

            User user = jwtUser.getUser(token);
            Product existingProduct = productRepository.findById(product.getId()).orElse(null);

            if (existingProduct != null && existingProduct.getSeller().getId().equals(user.getId())) {

                existingProduct.setName(product.getName());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setReleaseDate(new Date());
                productRepository.save(existingProduct);

                // Copy the updated fields to the product (object passed by reference)
                product.setName(existingProduct.getName());
                product.setDescription(existingProduct.getDescription());
                product.setPrice(existingProduct.getPrice());
                product.setReleaseDate(existingProduct.getReleaseDate());
                product.setSeller(existingProduct.getSeller());

                return 1; // Success
            }

            return 0; // Not authorized or product not found

        } catch (Exception e) {
            return -1;
        }
    }

    public int deleteProduct(long id, String token) {

        try {

            User user = jwtUser.getUser(token);
            Product product = productRepository.findById(id).orElse(null);

            if (product != null && product.getSeller().getId() == user.getId()) {
                productRepository.delete(product);
                return 1; // Success
            }

            return 0; // Product not deleted

        } catch (Exception e) {
            return -1;
        }
    }

}