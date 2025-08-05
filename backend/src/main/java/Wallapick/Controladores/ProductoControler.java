package Wallapick.Controladores;

import Wallapick.Modelos.Producto;
import Wallapick.Modelos.Respuesta;
import Wallapick.ModelosDTO.ProductoDTO;
import Wallapick.Servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/producto")

public class ProductoControler {
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/buscar")
    public Respuesta buscarProductosParcial(@RequestParam String nombreParcial) {
        List<ProductoDTO> productos = productoServicio.buscarProductosPorNombreParcial(nombreParcial);
        if (productos.isEmpty()) {
            return new Respuesta<String>(204,"No hay productos con esa cadena");
        }
        return new Respuesta<String>(200, productos);
    }

    @GetMapping("")
    public Respuesta buscarMisProductos(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");

        List<ProductoDTO> productosDTO = productoServicio.obtenerProductosDeUsuarioLogueado(token);

        if (productosDTO == null || productosDTO.isEmpty()) {
            return new Respuesta<String>(404, "No se encontraron productos para el usuario logueado.");
        }

        return new Respuesta<String>(200, productosDTO);
    }

    @GetMapping("/all")
    public Respuesta buscarTodosProductos(){
        List<ProductoDTO> productos = productoServicio.buscarProductos();
        if(productos == null) {
            return new Respuesta<String>(500,"Servicio de productos no disponible.");
        }
        return new Respuesta<String>(200,productos);
    }

    @PostMapping("")
    public Respuesta crearProducto(@RequestBody Producto producto, @RequestHeader("Authorization") String token){
        token = token.replace("Bearer ", "");

        int resultado = productoServicio.crearProducto(producto, token);

        if (resultado == 0) {
            return new Respuesta<String>(400,"Error al crear el producto. Verifica los datos.");
        } else if (resultado == 1) {
            ProductoDTO productoDTO = new ProductoDTO(producto);
            return new Respuesta<ProductoDTO>(200, productoDTO);
        } else {
            return new Respuesta<String>(500,"Error interno del servidor al crear el producto.");
        }
    }

    @DeleteMapping("/{id}")
    public Respuesta borrarProducto(@PathVariable long id, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        int resul = productoServicio.borrarProducto(id, token);

        if (resul == 1) {
            return new Respuesta<String>(200,"Producto eliminado correctamente.");
        } else if (resul == 0){
            return  new Respuesta<String>(400,"Producto no encontrado o no autorizado para eliminar.");
        }
        return new Respuesta<String>(200,"Error al intentar eliminar el producto. Intentelo mas tarde.");
    }

    @PatchMapping("")
    public Respuesta actualizarProducto(@RequestBody Producto producto, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        int resul = productoServicio.actualizarProducto(producto, token);

        if (resul == 1) {
            ProductoDTO p = new ProductoDTO(producto);
            return  new Respuesta<ProductoDTO>(200,p);
        } else if (resul == 0){
            return  new Respuesta<String>(404,"Producto no encontrado o no autorizado para actualizar.");
        }
        return  new Respuesta<String>(500,"Error al intentar actualizar el producto. Intentelo mas tarde.");
    }

}
