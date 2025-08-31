package Wallapick.Repositories;

import Wallapick.Models.Product;
import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {
    Product upload(Long id, MultipartFile file);
}
