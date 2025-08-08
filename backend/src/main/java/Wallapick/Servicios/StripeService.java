package Wallapick.Servicios;

import Wallapick.ModelosDTO.ProductoDTO;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StripeService {

   /* @Value("${stripe.secretKey}")
    private String secretKey;*/

    public Session checkoutProducts(List<ProductoDTO> productosDto) throws StripeException {


        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (ProductoDTO productoDto : productosDto) {
            long precioEnCentavos = Math.round(productoDto.getPrecio() * 100);

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)  // Si quieres permitir cantidad variable, deberías añadir ese atributo a ProductoDTO
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("eur")
                                    .setUnitAmount(precioEnCentavos)
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(productoDto.getNombre())
                                                    .build()
                                    ).build()
                    ).build();

            lineItems.add(lineItem);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://www.youtube.com/watch?v=BczS-wbxgp4")
                .setCancelUrl("https://www.youtube.com/watch?v=BczS-wbxgp4")
                .addAllLineItem(lineItems)
                .build();

        return Session.create(params);
    }
}