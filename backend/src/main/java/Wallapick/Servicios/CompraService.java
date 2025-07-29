package Wallapick.Servicios;

import Wallapick.Modelos.Compra;
import Wallapick.Repositorios.CompraRepositorio;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraService {
    @Autowired
    private CompraRepositorio compraRepositorio;
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
}
