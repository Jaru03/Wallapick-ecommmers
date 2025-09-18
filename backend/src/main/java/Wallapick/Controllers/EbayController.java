package Wallapick.Controllers;

import Wallapick.Models.ItemEbay;
import Wallapick.Models.Product;
import Wallapick.Models.Response;
import Wallapick.Services.EbayService;
import Wallapick.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebay")
@CrossOrigin // Permite llamadas desde el frontend Angular
public class EbayController {

    @Autowired
    private EbayService ebayService;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Busca productos en varias categorías y los guarda en la BD
     */
    @GetMapping("/search")
    public Response searchItems() {
        String[] categorias = {"ordenador", "camiseta", "dron", "libro", "juego de mesa", "pelicula", "disfraz", "pantalon", "coleccion", "comic"};
        ebayService.guardarProductosEbay(categorias);
        return new Response<>(200, "Productos importados correctamente");
    }

    /**
     * Obtiene detalles de un producto específico desde eBay
     */
    @GetMapping("/item/{itemId}")
    public Response getItemDetails(@PathVariable String itemId) {
        ItemEbay itemDetails = ebayService.getItemDetails(itemId);
        if(itemDetails == null){
            return new Response<>(404, "Producto no encontrado");
        }
        return new Response<>(200, "Detalles del producto obtenidos", itemDetails);
    }

    /**
     * Rellena la BD con productos de varias categorías
     */

}
