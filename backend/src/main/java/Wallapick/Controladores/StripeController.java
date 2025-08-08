package Wallapick.Controladores;

import Wallapick.ModelosDTO.ProductoDTO;
import Wallapick.Servicios.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StripeController {

    @Autowired
    StripeService stripeService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestBody List<ProductoDTO> productosDTO) {
        try {
            Session session = stripeService.checkoutProducts(productosDTO);
            return ResponseEntity.ok(session.getUrl());
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creando la sesión: " + e.getMessage());
        }

    }

}