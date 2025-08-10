package Wallapick.Controllers;

import Wallapick.Models.Response;
import Wallapick.ModelsDTO.OrderDTO;
import Wallapick.Services.OrderService;
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
        List<OrderDTO> ordersDTO = orderService.getOrdersUser(token);

        if (ordersDTO.isEmpty()) {
            return new Response<String>(404, "Orders not found for the user with that id.");
        }

        return new Response<List<OrderDTO>>(200, ordersDTO);
    }

    @PostMapping("")
    public Response orderProducts(@RequestBody ArrayList<Long> ids, @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        int response = orderService.orderProducts(ids, token);

        switch (response){

            case -2:
                return new Response<String>(400,"You can't order your own product.");
            case -1:
                return new Response<String>(404,"Product or user not found.");
            case 0:
                return new Response<String>(401,"User not logged.");
            case 1:
                return new Response<String>(200,"Order completed successfully.");
            default:
                return new Response<String>(500,"Error trying to order. Please try again later.");

        }
    }
}