package Wallapick.Controllers;

import Wallapick.Models.Response;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.UserDTO;
import Wallapick.Services.UserService;
import Wallapick.Utils.JWTUser;
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
    public Response registerUser(@RequestBody User user){

        int response = userService.registerUser(user);

        if(response == -1){
            return new Response<String>(406, "Username igual");
        } else if (response == -2) {
            return new Response<String>(406,"Email existe");
        }
        else{
            return new Response<String>(200,"ta joya");
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
    public Response searchUser(@PathVariable long id, @RequestHeader("Authorization") String token) {

        UserDTO userDTO = userService.searchUser(id);

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
            return new Response<String>(200, "User deleted successfully.");
        } else {
            return new Response<String>(403,"Access denied.");
        }
    }
}
