package Wallapick.Servicios;

import Wallapick.Modelos.Producto;
import Wallapick.Modelos.Usuario;
import Wallapick.Repositorios.ProductoRepositorio;
import Wallapick.Repositorios.UsuarioRepositorio;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private JWTUser jwtUser;



    public List<Producto> buscarProductosPorNombreParcial(String nombreParcial) {
        return productoRepositorio.findByNombreContainingIgnoreCase(nombreParcial);
    }

    public List<Producto> obtenerProductosDeUsuarioLogueado(String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token);

            if ("LOGGED".equals(usuario.getRole())) {
                return productoRepositorio.findByVendedor_Id(usuario.getId());
            } else {
                //No logeado
                return new ArrayList<>();
            }
        } catch (Exception e) {
            // Token inválido u otro error
            return new ArrayList<>();


        }
    }

    public int createProduct(Producto producto, String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token);

            if (usuario.getRole().equalsIgnoreCase("LOGGED")) {
                Usuario vendedor = usuarioRepositorio.findById(usuario.getId())
                        .orElse(null);
                if (vendedor == null) {
                    return 0;
                }

                producto.setVendedor(vendedor);
                producto.setPrecio(producto.getPrecio() * 0.26);
                producto.setFechaPublicacion(new Date());
                productoRepositorio.save(producto);

                return 1; // éxito
            }

            return 0; // usuario no tiene rol adecuado
        } catch (Exception e) {
            return -1; // error interno
        }
    }
    public List<Producto> getAllProducts() {
        return productoRepositorio.findAll();
    }

}
