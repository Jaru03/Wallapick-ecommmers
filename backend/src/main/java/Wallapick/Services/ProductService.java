package Wallapick.Services;

import Wallapick.Models.Product;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.ProductDTO;
import Wallapick.Repositories.OrderRepository;
import Wallapick.Repositories.ProductRepository;
import Wallapick.Repositories.UserRepository;
import Wallapick.Utils.JWTUser;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private Cloudinary cloudinary;

    public int createProduct(Product product, MultipartFile image, String token) {
        try {
            User user = jwtUser.getUser(token);
            User seller = userRepository.findById(user.getId()).orElse(null);

            if (seller == null) {
                return 0;
            }

            // Subir imagen a Cloudinary
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("secure_url");

            // Guardar datos del producto
            product.setSeller(seller);
            product.setImage(imageUrl); // Guardamos la URL en la BD

            BigDecimal originalPrice = BigDecimal.valueOf(product.getPrice());
            BigDecimal taxRate = BigDecimal.valueOf(0.30);
            BigDecimal taxAmount = originalPrice.multiply(taxRate);
            BigDecimal finalPrice = originalPrice.add(taxAmount).setScale(2, RoundingMode.HALF_UP);
            product.setPrice(finalPrice.doubleValue());

            product.setForSale(true);
            productRepository.save(product);

            return 1; // Éxito
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Error interno
        }
    }

    public List<ProductDTO> searchProductsPartial(String partialName) {

        List<Product> products = productRepository.findByNameContainingIgnoreCase(partialName);

        return products.stream()
                .filter(Product::isForSale)
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchMyProducts(String token) {

        try {

            User user = jwtUser.getUser(token); // It can throw an exception if the token is invalid
            List<Product> products = productRepository.findBySellerId(user.getId());

            return products.stream()
                    .map(ProductDTO::new)
                    .toList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public ProductDTO getProduct(long id){

        Product product = productRepository.findById(id).orElse(null);

        if (product == null){
            return null;
        }

        return new ProductDTO(product);

    }

    public List<ProductDTO> getAll() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .filter(Product::isForSale)
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    public int updateProduct(Product product, String token) {

        try {

            User user = jwtUser.getUser(token);
            Product existingProduct = productRepository.findById(product.getId()).orElse(null);

            if (existingProduct != null && existingProduct.getSeller().getId() == user.getId() && existingProduct.isForSale()) {

                existingProduct.setName(product.getName());
                existingProduct.setDescription(product.getDescription());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setCategory(product.getCategory());
                existingProduct.setStatus((product.getStatus()));
                productRepository.save(existingProduct);

                // Copy the updated fields to the product (object passed by reference)
                product.setName(existingProduct.getName());
                product.setDescription(existingProduct.getDescription());
                product.setPrice(existingProduct.getPrice());
                product.setCategory(existingProduct.getCategory());
                product.setStatus(existingProduct.getStatus());
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

            if (product != null && product.getSeller().getId() == user.getId() && product.isForSale()) {
                productRepository.delete(product);
                return 1; // Success
            }

            return 0; // Product not deleted

        } catch (Exception e) {
            return -1;
        }
    }
}
