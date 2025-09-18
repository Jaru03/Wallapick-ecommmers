package Wallapick.Services;

import Wallapick.Models.ItemEbay;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "ebayClient",
        url = "https://api.ebay.com"
)
public interface EbayClient {

    // Obtener Access Token
    @PostMapping(value = "/identity/v1/oauth2/token", consumes = "application/x-www-form-urlencoded")
    AccessTokenResponse getAccessToken(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("grant_type") String grantType,
            @RequestParam("scope") String scope
    );

    class AccessTokenResponse {
        public String access_token;
        public int expires_in;
    }

    // Buscar productos
    @GetMapping("/buy/browse/v1/item_summary/search")
    ItemSearchResponse searchItems(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("q") String query,
            @RequestParam("limit") int limit
    );

    class ItemSearchResponse {
        public ItemSummary[] itemSummaries;

        public static class ItemSummary {
            public String itemId;
        }
    }

    // Obtener detalles del producto con headers fijos
    @GetMapping("/buy/browse/v1/item/{itemId}")
    ItemEbay getItem(
            @RequestHeader("Authorization") String bearerToken,
            @RequestHeader("Accept-Language") String acceptLanguage,
            @RequestHeader("X-EBAY-C-MARKETPLACE-ID") String marketplaceId,
            @RequestHeader("X-EBAY-C-ENDUSERCTX") String endUserCtx,
            @PathVariable(value = "itemId") String itemId
    );
}
