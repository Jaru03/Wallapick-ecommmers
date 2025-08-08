package Wallapick.Servicios;

import Wallapick.Modelos.Compra;
import Wallapick.Modelos.Producto;
import Wallapick.Modelos.Usuario;
import Wallapick.ModelosDTO.ProductoDTO;
import Wallapick.Repositorios.CompraRepositorio;
import Wallapick.Repositorios.ProductoRepositorio;
import Wallapick.Repositorios.UsuarioRepositorio;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductoServicio {
    @Autowired
    private  ProductoRepositorio productoRepositorio;
    @Autowired
    private  UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private CompraRepositorio compraRepositorio;
    @Autowired
    private JWTUser jwtUser;



    public List<ProductoDTO> buscarProductosPorNombreParcial(String nombreParcial) {
        List<Producto> productos = productoRepositorio.findByNombreContainingIgnoreCase(nombreParcial);

        return productos.stream()
                .map(ProductoDTO::new) // Usar el constructor que convierte de Producto a ProductoDTO
                .toList();
    }


    public List<ProductoDTO> obtenerProductosDeUsuarioLogueado(String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token); // puede lanzar excepción si el token es inválido

            if ("LOGGED".equals(usuario.getRole())) {
                List<Producto> productos = productoRepositorio.findByVendedor_Id(usuario.getId());

                return productos.stream()
                        .map(ProductoDTO::new)
                        .toList();
            } else {
                return new ArrayList<>();
            }
        } catch (Exception e) {
            // Token inválido u otro error
            return new ArrayList<>();


        }
    }

    public int crearProducto(Producto producto, String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token);

            if (usuario.getRole().equalsIgnoreCase("LOGGED")) {
                Usuario vendedor = usuarioRepositorio.findById(usuario.getId())
                        .orElse(null);
                if (vendedor == null) {
                    return 0;
                }

                producto.setVendedor(vendedor);

                // Calcular precio con 30% de impuesto
                BigDecimal precioOriginal = BigDecimal.valueOf(producto.getPrecio());
                BigDecimal impuesto = precioOriginal.multiply(BigDecimal.valueOf(0.30));
                BigDecimal precioFinal = precioOriginal.add(impuesto).setScale(2, RoundingMode.HALF_UP);
                producto.setPrecio(precioFinal.doubleValue());

                producto.setFechaPublicacion(new Date());
                productoRepositorio.save(producto);

                return 1; // éxito
            }

            return 0; // usuario no tiene rol adecuado
        } catch (Exception e) {
            e.printStackTrace(); // importante para depurar errores
            return -1; // error interno
        }
    }

    public List<ProductoDTO> buscarProductos() {
        List<Producto> productos = productoRepositorio.findAll();

        return productos.stream()
                .map(ProductoDTO::new) // utiliza el constructor DTO que ya tienes
                .toList();
    }


    public int borrarProducto(long id, String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token);
            Producto producto = productoRepositorio.findById(id).orElse(null);

            if (producto != null && producto.getVendedor().getId() == usuario.getId()) {
                productoRepositorio.delete(producto);
                return 1; // Producto eliminado correctamente
            }
            return 0; // No se pudo eliminar el producto
        } catch (Exception e) {
            return -1; // Error al intentar eliminar el producto
        }
    }

    public int actualizarProducto(Producto producto, String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token);
            Producto existingProduct = productoRepositorio.findById(producto.getId()).orElse(null);

            if (existingProduct != null && existingProduct.getVendedor().getId().equals(usuario.getId())) {
                existingProduct.setNombre(producto.getNombre());
                existingProduct.setDescripcion(producto.getDescripcion());
                existingProduct.setPrecio(producto.getPrecio());
                existingProduct.setFechaPublicacion(new Date());
                productoRepositorio.save(existingProduct);

                // Copiar los campos actualizados a producto (objeto pasado por referencia)
                producto.setNombre(existingProduct.getNombre());
                producto.setDescripcion(existingProduct.getDescripcion());
                producto.setPrecio(existingProduct.getPrecio());
                producto.setFechaPublicacion(existingProduct.getFechaPublicacion());
                producto.setVendedor(existingProduct.getVendedor());

                return 1; // Producto actualizado correctamente
            }
            return 0; // No autorizado o producto no encontrado
        } catch (Exception e) {
            return -1; // Error
        }
    }





}
