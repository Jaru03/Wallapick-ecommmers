package Wallapick.Controllers;

import Wallapick.Models.Response;
import Wallapick.ModelsDTO.ProductDTO;
import Wallapick.Services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/createCheckoutSession")
    public Response createCheckoutSession(@RequestBody List<ProductDTO> productsDTO) {

        try {

            Session session = stripeService.checkoutProducts(productsDTO);
            return new Response<String>(200, session.getUrl());

        } catch (StripeException e) {
            return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error creating the session: " + e.getMessage());
        }

    }

}