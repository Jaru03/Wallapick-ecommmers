package Wallapick.Controladores;

import Wallapick.Modelos.Producto;
import Wallapick.Servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")

public class ProductoControler {
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/buscar")
    public ResponseEntity<?> findProducts(@RequestParam String nombreParcial) {
        List<Producto> productos = productoServicio.buscarProductosPorNombreParcial(nombreParcial);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/")
    public ResponseEntity<?> getMyPodructs(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        List<Producto> productos = productoServicio.obtenerProductosDeUsuarioLogueado(token);

        if(productos.isEmpty()){
            return ResponseEntity.status(404).body("No se encontraron productos para el usuario logueado.");
        }
        return ResponseEntity.ok("Tus productos son: "+productos);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<Producto> productos = productoServicio.getAllProducts();
        if(productos == null) {
            return ResponseEntity.status(500).body("Servicio de productos no disponible.");
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody Producto producto, @RequestHeader("Authorization") String token){
        token = token.replace("Bearer ", "");

        int resultado = productoServicio.createProduct(producto, token);

        if (resultado == 0) {
            return ResponseEntity.status(400).body("Error al crear el producto. Verifica los datos.");
        } else if (resultado == 1) {
            return ResponseEntity.ok("Producto creado correctamente: " + producto);
        } else {
            return ResponseEntity.status(500).body("Error interno del servidor al crear el producto.");
        }
    }
}
