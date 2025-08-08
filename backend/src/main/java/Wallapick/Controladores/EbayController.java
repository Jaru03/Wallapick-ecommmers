package Wallapick.Controladores;


import Wallapick.Servicios.EbayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ebay")
@CrossOrigin // para permitir llamadas desde tu frontend Angular
public class EbayController {

    @Autowired
    private EbayService ebayService;

    @GetMapping("/search")
    public ResponseEntity<?> searchItems(@RequestParam String q) {
        return ResponseEntity.ok(ebayService.getItemSummaries(q));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemDetails(@PathVariable String itemId) {
        return ResponseEntity.ok(ebayService.getItemDetails(itemId));
    }
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(ebayService.getCategories());
    }
}