package Wallapick.Servicios;

import Wallapick.Modelos.Compra;
import Wallapick.Modelos.Producto;
import Wallapick.Modelos.Usuario;
import Wallapick.Repositorios.CompraRepositorio;
import Wallapick.Repositorios.ProductoRepositorio;
import Wallapick.Repositorios.UsuarioRepositorio;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraRepositorio compraRepositorio;
    @Autowired
    private ProductoRepositorio productoRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private JWTUser jwtUser;

    public List<Compra> getComprasByUserId(Long userId, String token) {
        try {
            jwtUser.ObtenerUsuario(token);
            return compraRepositorio.findByComprador_Id(userId);
        } catch (Exception e) {
            // Manejo de excepciones, como token inválido
            return null;
        }
    }

    public int comprarProductos(ArrayList<Long> ids, String token) {
        try {
            Usuario usuario = jwtUser.ObtenerUsuario(token);

            if (!"LOGGED".equals(usuario.getRole())) {
                return 0; // Usuario no autorizado
            }

            for (Long id : ids) {
                Producto producto = productoRepositorio.findById(id).orElse(null);

                if (producto == null || !producto.isEnVenta()) {
                    return -1; // Producto no encontrado o no está en venta
                }

                Usuario vendedor = usuarioRepositorio.findById(producto.getVendedor().getId()).orElse(null);
                if (vendedor == null) {
                    return -1; // Vendedor no encontrado
                }

                Compra com = new Compra(producto, usuario, vendedor, new Date(), producto.getPrecio());
                compraRepositorio.save(com);

                producto.setEnVenta(false); // Marcar como vendido
                producto.setCompra(com); // Asignar compra
                productoRepositorio.save(producto);
            }

            return 1; // Compra exitosa
        } catch (Exception e) {
            e.printStackTrace(); // Ayuda a depurar
            return 2; // Error al intentar comprar
        }
    }
}
