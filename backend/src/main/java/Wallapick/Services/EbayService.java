package Wallapick.Services;

import Wallapick.Models.ItemEbay;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.Base64;

@Service
public class EbayService {

    @Value("${ebay.client.id}")
    private String clientId;

    @Value("${ebay.client.secret}")
    private String clientSecret;

    private String accessToken;
    private long tokenExpirationTime = 0;
    private final RestTemplate restTemplate = new RestTemplate();

    // Construye headers comunes para llamadas a la API eBay
    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.set("Accept-Language", "es-ES");
        headers.set("X-EBAY-C-MARKETPLACE-ID", "EBAY_ES");
        headers.set("X-EBAY-C-ENDUSERCTX", "contextualLocation=country=ES");
        return headers;
    }

    // Obtiene token OAuth2 usando clientId y clientSecret en Basic Auth
    private String getAccessToken() {

        if (accessToken != null && System.currentTimeMillis() < tokenExpirationTime) {
            return accessToken;
        }

        // Codifica clientId:clientSecret en Base64
        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        String body = "grant_type=client_credentials&scope=https://api.ebay.com/oauth/api_scope";

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
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

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error obteniendo token de acceso eBay.");
        }
    }

    // Busca productos
    public List<ItemEbay> getItemSummaries(String nameItem) {

        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + nameItem + "&limit=10";

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        List<ItemEbay> summaryItems = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode items = root.path("itemSummaries");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    ItemEbay summary = new ItemEbay();
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

                    double price = 0.0;
                    if (item.has("price")) {
                        JsonNode priceNode = item.get("price");
                        if (priceNode.has("value")) {
                            price = priceNode.path("value").asDouble();
                        }
                    }
                    summary.setPrice(price);

                    List<String> imagesList = new ArrayList<>();
                    if (item.has("additionalImages") && item.get("additionalImages").isArray()) {
                        for (JsonNode imageNode : item.get("additionalImages")) {
                            String imageName = imageNode.path("imageUrl").asText(null);
                            if (imageName != null) {
                                imagesList.add(imageName);
                            }
                        }
                    }
                    summary.setImages(imagesList);

                    if (item.has("thumbnailImages") && item.get("thumbnailImages").isArray()) {
                        JsonNode firstThumb = item.get("thumbnailImages").get(0);
                        if (firstThumb != null) {
                            String thumbUrl = firstThumb.path("imageUrl").asText(null);
                            summary.setMainImage(thumbUrl);
                        }
                    }

                    summaryItems.add(summary);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return summaryItems;
    }

    // Obtener detalles de un producto
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

    // Obtener categoryTreeId
    private String getCategoryTreeId() {
        String url = "https://api.ebay.com/commerce/taxonomy/v1/get_default_category_tree_id?marketplace_id=EBAY_ES";

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map body = response.getBody();
        return (String) body.get("categoryTreeId");
    }

    // Obtener categorías
    public Map getCategories() {
        String categoryTreeId = getCategoryTreeId();
        String url = "https://api.ebay.com/commerce/taxonomy/v1/category_tree/" + categoryTreeId;

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

    public List<ItemEbay> getItemsByCategory(String categoryId) {
        if (categoryId == null || categoryId.trim().isEmpty()) {
            throw new IllegalArgumentException("categoryId no puede ser null o vacío");
        }

        String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?category_ids=" + categoryId + "&limit=50";

        HttpHeaders headers = buildHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        List<ItemEbay> summaryItems = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode items = root.path("itemSummaries");

            if (items.isArray()) {
                for (JsonNode item : items) {
                    ItemEbay summary = new ItemEbay();
                    summary.setId(item.path("itemId").asText(null));
                    summary.setTitle(item.path("title").asText(null));

                    // Categorías
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

                    // Precio
                    double price = 0.0;
                    if (item.has("price")) {
                        JsonNode priceNode = item.get("price");
                        if (priceNode.has("value")) {
                            price = priceNode.path("value").asDouble();
                        }
                    }
                    summary.setPrice(price);

                    // Imágenes adicionales
                    List<String> imagesList = new ArrayList<>();
                    if (item.has("additionalImages") && item.get("additionalImages").isArray()) {
                        for (JsonNode imageNode : item.get("additionalImages")) {
                            String imageUrl = imageNode.path("imageUrl").asText(null);
                            if (imageUrl != null) {
                                imagesList.add(imageUrl);
                            }
                        }
                    }
                    summary.setImages(imagesList);

                    // Imagen principal desde thumbnailImages (si existe)
                    if (item.has("thumbnailImages") && item.get("thumbnailImages").isArray()) {
                        JsonNode firstThumb = item.get("thumbnailImages").get(0);
                        if (firstThumb != null && firstThumb.has("imageUrl")) {
                            summary.setMainImage(firstThumb.path("imageUrl").asText(null));
                        }
                    }

                    // Fallback: imagen principal desde image.imageUrl si no hay thumbnail
                    if ((summary.getMainImage() == null || summary.getMainImage().isEmpty())
                            && item.has("image") && item.get("image").has("imageUrl")) {
                        summary.setMainImage(item.get("image").path("imageUrl").asText(null));
                    }

                    summaryItems.add(summary);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return summaryItems;
    }

}
