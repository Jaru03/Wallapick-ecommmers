package Wallapick.Controllers;

import Wallapick.Models.Response;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.UserDTO;
import Wallapick.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Response registerUser(@RequestBody User user){

        String response = userService.registerUser(user);

        if(response.equalsIgnoreCase("REGISTERED")){
            return new Response<String>(200, "User registered successfully.");
        } else if (response.equalsIgnoreCase("EXISTED")) {
            return new Response<String>(409,"User already exists.");
        }
        else{
            return new Response<String>(400,"Error registering the user. Please verify the data.");
        }
    }

    @PostMapping("/login")
    public Response loginUser(@RequestBody User user) {

        String response = userService.loginUser(user);

        if(response.equalsIgnoreCase("ACCESS DENIED")){
            return new Response<String>(404,"User not found or incorrect password.");
        }
        return new Response<String>(200, response);
    }

    @GetMapping("/{id}")
    public Response searchUser(@PathVariable long id, @RequestHeader("Authorization") String token){

        token = token.replace("Bearer ", "");
        UserDTO userDTO = userService.searchUser(id, token);

        if(userDTO == null){
            return new Response<String>(404,"User not found.");
        }

        return new Response<UserDTO>(200, userDTO);
    }

    @GetMapping("")
    public Response searchId(@RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");
        Long userId = userService.searchId(token);

        if (userId != null) {
            return new Response<Long>(200, userId);
        } else {
            return new Response<String>(401,"Invalid credentials.");
        }
    }

    @PatchMapping("")
    public Response updateUser(@RequestBody User user, @RequestHeader("Authorization") String token ){

        token = token.replace("Bearer ", "");

        if(userService.updateUser(user, token)){
            UserDTO userDTO = new UserDTO(user);
            return new Response<UserDTO>(200, userDTO);
        } else {
            return new Response<String>(403,"Access denied.");
        }
    }

    @DeleteMapping("/{id}")
    public Response deleteUser(@PathVariable long id, @RequestHeader("Authorization") String token) {

        token = token.replace("Bearer ", "");

        if(userService.deleteUser(id, token)){
            return new Response<String>(200,"User deleted successfully.");
        } else {
            return new Response<String>(403,"Access denied.");

        }
    }
}