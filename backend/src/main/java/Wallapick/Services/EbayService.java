package Wallapick.Services;

import Wallapick.Models.ItemEbay;
import Wallapick.Models.Product;
import Wallapick.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EbayService {

    @Value("${ebay.client.id}")
    private String clientId;

    @Value("${ebay.client.secret}")
    private String clientSecret;

    private String accessToken;
    private long tokenExpirationTime = 0;

    @Autowired
    private EbayClient ebayClient;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Obtiene el token de acceso, lo cachea y lo renueva si expira
     */
    private String getAccessToken() {
        // Si el token es válido, lo reutilizamos
        if (accessToken != null && System.currentTimeMillis() < tokenExpirationTime) {
            return accessToken;
        }

        // Codificamos credenciales
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        // Llamamos a Feign para obtener el token
        EbayClient.AccessTokenResponse tokenResponse = ebayClient.getAccessToken(
                "Basic " + encodedCredentials,
                "client_credentials",
                "https://api.ebay.com/oauth/api_scope"
        );

        accessToken = tokenResponse.access_token;
        tokenExpirationTime = System.currentTimeMillis() + (tokenResponse.expires_in * 1000L);

        return accessToken;
    }

    /**
     * Devuelve el token con el prefijo Bearer
     */
    private String bearer() {
        return "Bearer " + getAccessToken();
    }

    /**
     * Devuelve los detalles de un producto con Feign
     */
    public ItemEbay getItemDetails(String itemId) {
        return ebayClient.getItem(
                bearer(),
                "es-ES",
                "EBAY_ES",
                "contextualLocation=country=ES",
                itemId
        );
    }


    /**
     * Devuelve la lista de productos guardados en la BD desde varias categorías
     */
    public void guardarProductosEbay(String[] categorias) {
        List<Product> productos = new ArrayList<>();
        String token = bearer();

        for (String categoria : categorias) {
            EbayClient.ItemSearchResponse items = ebayClient.searchItems(token, categoria, 40);

            if (items.itemSummaries == null) continue;

            for (EbayClient.ItemSearchResponse.ItemSummary item : items.itemSummaries) {
                ItemEbay itemEbay = ebayClient.getItem(
                        token,
                        "es-ES",
                        "EBAY_ES",
                        "contextualLocation=country=ES",
                        item.itemId
                );
                Product p = new Product(itemEbay, categoria);
                productos.add(p);
                productRepository.save(p);
            }
        }
    }

}
