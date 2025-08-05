package Wallapick.Servicios;

import Wallapick.ModelosDTO.ProductoDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    //@Value("${stripe.secretKey}")
    //private String secretKey;

    public Session checkoutProducts(ProductoDTO productoDto) throws StripeException {

        //Stripe.apiKey = secretKey;

        long precioEnCentavos = Math.round(productoDto.getPrecio() * 100);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://www.youtube.com/watch?v=BczS-wbxgp4")
                .setCancelUrl("https://www.youtube.com/watch?v=BczS-wbxgp4")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("eur")
                                                .setUnitAmount(precioEnCentavos)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(productoDto.getNombre())
                                                                .build()
                                                ).build()
                                ).build()
                ).build();

        return Session.create(params);
    }
}