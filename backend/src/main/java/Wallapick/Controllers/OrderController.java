package Wallapick.Controllers;

import Wallapick.Models.Response;
import Wallapick.ModelsDTO.ProductDTO;
import Wallapick.Services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public Response getOrdersUser(@RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        List<ProductDTO> productsDTO = orderService.getOrdersUser(token);

        if (productsDTO.isEmpty()) {
            return new Response<String>(404, "Orders not found for the user with that id.");
        }

        return new Response<List<ProductDTO>>(200, productsDTO);
    }

    @PostMapping("")
    public Response orderProducts(@Valid @RequestBody ArrayList<Long> ids, @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        List<ProductDTO> productsToOrder = orderService.orderProducts(ids, token);

        if (productsToOrder == null) {
            return new Response<>(400, "Error processing the order. Check product IDs or user status.");
        }

        return new Response<List<ProductDTO>>(200, productsToOrder);
    }
}
