package Wallapick.Services;

import Wallapick.Models.Product;
import Wallapick.Repositories.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadService implements Wallapick.Repositories.FileUpload {

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private ProductRepository productRepository;

    @SuppressWarnings("unchecked")
    @Override
    public Product upload(Long id, MultipartFile file) {

        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "webp");

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        String extensions = null;

        if (file.getOriginalFilename() != null){
            String[] splitName = file.getOriginalFilename().split("\\.");
            extensions = splitName[splitName.length - 1];
        }

        if (!allowedExtensions.contains(extensions)) throw new IllegalArgumentException(
            String.format("Extension %s not allowed.", extensions)
        );

        try {

            Map<String, Object> resultUpload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "library"));
            String imageUrl = resultUpload.get("secure_url").toString();
            product.setImage(imageUrl);
            productRepository.save(product);
            return product;

        } catch (Exception e) {
            throw new RuntimeException("Error trying to upload the image to Cloudinary.", e);
        }

    }
}