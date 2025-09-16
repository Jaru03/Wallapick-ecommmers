package Wallapick.Controllers;

import Wallapick.Models.ItemEbay;
import Wallapick.Services.EbayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin // To allow calls from frontend Angular
public class EbayController {

    @Autowired
    private EbayService ebayService;

    @GetMapping("/search")
    public ResponseEntity<?> searchItems(@RequestParam String nameItem) {
        return ResponseEntity.ok(ebayService.getItemSummaries(nameItem)); // nameItem in english
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemDetails(@PathVariable String itemId) {
        return ResponseEntity.ok(ebayService.getItemDetails(itemId));
    }
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(ebayService.getCategories());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getItemsByCategory(@PathVariable String categoryId) {
        List<ItemEbay> items = ebayService.getItemsByCategory(categoryId);
        return ResponseEntity.ok(items);
    }

}
