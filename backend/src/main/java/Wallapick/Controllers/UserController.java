package Wallapick.Controllers;

import Wallapick.Models.Response;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.UserDTO;
import Wallapick.Services.UserService;
import Wallapick.Utils.JWTUser;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUser jwtUser;


    @PostMapping("")
    public Response registerUser(@Valid @RequestBody User user){


        int response = userService.registerUser(user);

        if(response == -1){
            return new Response<String>(406, "Same username");
        } else if (response == -2) {
            return new Response<String>(406,"Email exists");
        }
        else{
            return new Response<String>(200,"User created successfully.");
        }
    }

    @PostMapping("/login")
    public Response loginUser(@Valid @RequestBody JsonNode json) {
        try {
            String username = json.has("username") ? json.get("username").asText() : null;
            String password = json.has("password") ? json.get("password").asText() : null;

            if (username == null || password == null) {
                return new Response<String>(400, "Username and password are required.");
            }

            String response = userService.loginUser(username, password);

            if (response.equalsIgnoreCase("ACCESS DENIED")) {
                return new Response<String>(404, "User not found or incorrect password.");
            }

            return new Response<String>(200, response);

        } catch (Exception e) {
            return new Response<String>(500, "Internal server error.");
        }
    }



    @GetMapping("/{id}")
    public Response searchUser(@PathVariable long id, @RequestHeader("Authorization") String token) {

        UserDTO userDTO = userService.searchUser(id);

        if(userDTO == null){
            return new Response<String>(404,"User not found.");
        }

        return new Response<UserDTO>(200, userDTO);
    }

    @PatchMapping("")
    public Response updateUser(@Valid @RequestBody User user, @RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        int result = userService.updateUser(user, token);

        switch (result) {
            case 200:
                UserDTO userDTO = new UserDTO(user);
                return new Response<UserDTO>(200, userDTO);
            case 401:
                return new Response<String>(401, "Token inválido o usuario no autenticado.");
            case 403:
                return new Response<String>(403, "No tienes permiso para actualizar este usuario.");
            case 404:
                return new Response<String>(404, "Usuario no encontrado.");
            case 409:
                return new Response<String>(409, "El email ya está en uso por otro usuario.");
            case 500:
                return new Response<String>(500, "Error interno en el servidor.");
            default:
                return new Response<String>(500, "Error desconocido.");
        }
    }


    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable long id, @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");

        if(userService.deleteUser(id, token)){
            return new Response<String>(200, "User deleted successfully.");
        } else {
            return new Response<String>(403,"Access denied.");
        }
    }
}
