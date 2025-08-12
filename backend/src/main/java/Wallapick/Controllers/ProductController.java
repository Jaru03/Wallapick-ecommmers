package Wallapick.Controllers;

import Wallapick.Models.Product;
import Wallapick.Models.Response;
import Wallapick.ModelsDTO.ProductDTO;
import Wallapick.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public Response createProduct(@RequestBody Product product, @RequestHeader("Authorization") String token){

        token = token.replace("Bearer ", "");
        int response = productService.crearProducto(product, token);

        if (response == 0) {
            return new Response<String>(400,"Error creating the product. Please check de information.");
        } else if (response == 1) {
            ProductDTO productDTO = new ProductDTO(product);
            return new Response<ProductDTO>(200, productDTO);
        } else {
            return new Response<String>(500,"Internal server error while creating the product.");
        }
    }

    @GetMapping("/searchProductsPartial")
    public Response searchProductsPartial(@RequestParam String partialName) {

        List<ProductDTO> productsDTO = productService.searchProductsPartial(partialName);

        if (productsDTO.isEmpty()) {
            return new Response<String>(204,"No products found with that string.");
        }

        return new Response<String>(200, productsDTO);
    }

    @GetMapping("/searchMyProducts")
    public Response searchMyProducts(@RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        List<ProductDTO> productsDTO = productService.searchMyProducts(token);

        if (productsDTO == null || productsDTO.isEmpty()) {
            return new Response<String>(404, "No products found for the logged-in user.");
        }

        return new Response<String>(200, productsDTO);
    }

    @GetMapping("/searchAll")
    public Response searchAll(){

        List<ProductDTO> productsTO = productService.searchAll();

        if(productsTO == null) {
            return new Response<String>(500,"Product service unavailable.");
        }

        return new Response<String>(200, productsTO);
    }

    @PatchMapping("")
    public Response updateProduct(@RequestBody Product product, @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        int response = productService.updateProduct(product, token);

        if (response == 1) {
            ProductDTO productDTO = new ProductDTO(product);
            return new Response<ProductDTO>(200, productDTO);
        } else if (response == 0) {
            return new Response<String>(404, "No product found or not authorized to update.");
        }

        return new Response<String>(500, "Error trying to update the product. Please try again later.");
    }

    @DeleteMapping("/{id}")
    public Response deleteProduct(@PathVariable long id, @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        int response = productService.deleteProduct(id, token);

        if (response == 1) {
            return new Response<String>(200,"Product deleted successfully.");
        } else if (response == 0){
            return  new Response<String>(400,"No product found or not authorized to delete.");
        }

        return new Response<String>(200,"Error trying to delete the product. Please try again later.");
    }

}