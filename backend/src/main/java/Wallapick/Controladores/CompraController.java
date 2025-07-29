package Wallapick.Controladores;

import Wallapick.Modelos.Compra;
import Wallapick.Servicios.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compra")
public class CompraController {
    @Autowired
    private CompraService compraService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getComprasById(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        List<Compra> compras = compraService.getComprasByUserId(id, token);

        if (compras.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron compras para el usuario con ID: " + id);
        }

        return ResponseEntity.ok(compras);
    }


}
