package Wallapick.Services;

import Wallapick.ModelsDTO.ProductDTO;
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

    //@Value("${stripe.secretKey}")
    //private String secretKey;

    public Session checkoutProducts(List<ProductDTO> productsDto) throws StripeException {

        // Stripe.apiKey = secretKey;

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        for (ProductDTO productDto : productsDto) {

            long priceInCents = Math.round(productDto.getPrice() * 100);

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(1L)  // If you want to allow variable quantity, you should add that attribute to ProductDTO
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("eur")
                                    .setUnitAmount(priceInCents)
                                    .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                    .setName(productDto.getName())
                                                    .build()
                                    ).build()
                    ).build();

            lineItems.add(lineItem);
        }

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("https://www.google.com")
                .setCancelUrl("https://www.google.com")
                .addAllLineItem(lineItems)
                .build();

        return Session.create(params);
    }
}