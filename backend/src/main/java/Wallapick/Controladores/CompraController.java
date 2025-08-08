package Wallapick.Controladores;

import Wallapick.Modelos.Compra;
import Wallapick.Modelos.Respuesta;
import Wallapick.ModelosDTO.CompraDTO;
import Wallapick.Servicios.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/compra")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @GetMapping("")
    public Respuesta obtenerComprasId( @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        List<CompraDTO> compras = compraService.getComprasByUserId( token);

        if (compras.isEmpty()) {
            return new Respuesta<String>(404, "No se encontraron compras para el usuario con ese ID");
        }

        return new Respuesta<List<CompraDTO>>(200, compras);
    }

    @PostMapping("")
    public Respuesta comprarProducto(@RequestBody ArrayList<Long> ids, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        int resul = compraService.comprarProductos(ids, token);

        switch (resul){
            case -2:
                return new Respuesta<String>(400,"No puedes comprar tu propio producto.");
            case -1:
                return new Respuesta<String>(404,"Producto o usuarios no encontrado.");
            case 0:
                return new Respuesta<String>(401,"Usuario no logeado.");
            case 1:
                return new Respuesta<String>(200,"Compra realizada correctamente.");
            default:
                return new Respuesta<String>(500,"Error al intentar comprar los productos.Intentelo mas tarde.");
        }

    }


}
