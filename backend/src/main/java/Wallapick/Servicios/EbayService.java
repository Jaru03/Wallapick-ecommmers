package Wallapick.Servicios;

import Wallapick.Modelos.ItemSummary;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class EbayService {

    /*@Value("${ebay.client.id}")
    private String clientId;

    @Value("${ebay.client.secret}")
    private String clientSecret;*/

    private String accessToken;
    private long tokenExpirationTime = 0;

    private final RestTemplate restTemplate = new RestTemplate();

    // ====================== Obtener token OAuth2 ======================
    private String getAccessToken() {
        if (accessToken != null && System.currentTimeMillis() < tokenExpirationTime) {
            return accessToken;
        }



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=client_credentials&scope=https://api.ebay.com/oauth/api_scope";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://api.ebay.com/identity/v1/oauth2/token",
                HttpMethod.POST,
                entity,
                Map.class
        );

        Map responseBody = response.getBody();
        accessToken = (String) responseBody.get("access_token");
        int expiresIn = (Integer) responseBody.get("expires_in");
        tokenExpirationTime = System.currentTimeMillis() + (expiresIn * 1000L);

        return accessToken;
    }

    // ====================== Construir Headers Comunes ======================
    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.set("Accept-Language", "es-ES");
        headers.set("X-EBAY-C-MARKETPLACE-ID", "EBAY_ES");
        headers.set("X-EBAY-C-ENDUSERCTX", "contextualLocation=country=ES");
        return headers;
    }

    // ====================== Buscar productos ======================

    public List<ItemSummary> getItemSummaries(String query) {
        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + query + "&limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.set("Accept-Language", "es-ES");
        headers.set("X-EBAY-C-MARKETPLACE-ID", "EBAY_ES");
        headers.set("X-EBAY-C-ENDUSERCTX", "contextualLocation=country=ES");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        List<ItemSummary> summaries = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode items = root.path("itemSummaries");

            if (items.isArray()) {
                for (JsonNode item : items) {

                    ItemSummary summary = new ItemSummary();
                    summary.setId(item.path("itemId").asText(null));

                    summary.setTitle(item.path("title").asText(null));

                    List<String> categoryList = new ArrayList<>();

                    if (item.has("categories") && item.get("categories").isArray()) {
                        for (JsonNode categoryNode : item.get("categories")) {
                            String categoryName = categoryNode.path("categoryName").asText(null);
                            if (categoryName != null) {
                                categoryList.add(categoryName);
                            }
                        }
                    }

                    summary.setCategories(categoryList);

                    // Precio con moneda
                    double price = 0.0;
                    if (item.has("price")) {
                        JsonNode priceNode = item.get("price");
                        if (priceNode.has("value")) {
                            price = priceNode.path("value").asDouble();
                        }
                    }
                    summary.setPrice(price);

                    // Imagenes complementarias
                    List<String> imageList = new ArrayList<>();

                    if (item.has("additionalImages") && item.get("additionalImages").isArray()) {
                        for (JsonNode imageNode : item.get("additionalImages")) {
                            String imageName = imageNode.path("imageUrl").asText(null);
                            if (imageName != null) {
                                imageList.add(imageName);
                            }
                        }
                    }
                    summary.setImages(imageList);

                    // Imagen principal desde "thumbnailImages"
                    if (item.has("thumbnailImages") && item.get("thumbnailImages").isArray()) {
                        JsonNode firstThumb = item.get("thumbnailImages").get(0);
                        if (firstThumb != null) {
                            String thumbUrl = firstThumb.path("imageUrl").asText(null);
                            summary.setPrincipalImage(thumbUrl);
                        }
                    }

                    summaries.add(summary);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return summaries;
    }
    /*
    public Map searchItems(String query) {
        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + query + "&limit=10";

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }*/

    // ====================== Obtener detalles de producto ======================
    public Map getItemDetails(String itemId) {
        String url = "https://api.ebay.com/buy/browse/v1/item/" + itemId;

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }
    //- ====================== Obtener categorías ======================
    public Map getCategories() {
        String url = "https://api.ebay.com/commerce/v1/category_tree";

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }
}
