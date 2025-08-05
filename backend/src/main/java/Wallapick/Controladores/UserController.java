package Wallapick.Controladores;

import Wallapick.Modelos.Respuesta;
import Wallapick.Modelos.Usuario;
import Wallapick.ModelosDTO.UsuarioDTO;
import Wallapick.Servicios.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Respuesta registrarUsuario(@RequestBody Usuario user){

        String res = userService.resgistrar(user);
        if(res.equalsIgnoreCase("REGISTRADO")){
            return new Respuesta<String>(200, "Usuario registrado correctamente.");

        } else if (res.equalsIgnoreCase("EXISTE")) {
            return new Respuesta<String>(409,"El usuario ya existe.");
        }
        else{
            return new Respuesta<String>(400,"Error al registrar el usuario. Verifica los datos.");
        }
    }

    @PostMapping("/login")
    public Respuesta login(@RequestBody Usuario user) {
        String respuesta = userService.Login(user);
        if(respuesta.equalsIgnoreCase("ACCESO DENEGADO")){
            return new Respuesta<String>(404,"Usuario no encontrado o contraseña incorrecta");
        }
        return new Respuesta<String>(200, respuesta);
    }

    @GetMapping("/{id}")
    public Respuesta buscarUsuario(@PathVariable long id, @RequestHeader("Authorization") String token ){
        token = token.replace("Bearer ", "");
        UsuarioDTO u = userService.buscarUsuario(id, token);
        if(u == null){
            return new Respuesta<String>(404,"Usuario no encontrado");
        }
        return new Respuesta<UsuarioDTO>(200,u);
    }
    @GetMapping("")
    public Respuesta buscarId(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        Long userId = userService.obtenerIdSiTokenValido(token);

        if (userId != null) {
            return new Respuesta<Long>(200, userId);
        } else {
            return new Respuesta<String>(401,"Credenciales inválidas");
        }
    }

    @PatchMapping("/{id}")
    public Respuesta actualizarUsuario(@RequestBody Usuario user,@RequestHeader("Authorization") String token ){
        token = token.replace("Bearer ", "");

        if(userService.actualizarUsario(user, token)){
            UsuarioDTO u = new UsuarioDTO(user);
            return new Respuesta<UsuarioDTO>(200,u);
        } else {
            return new Respuesta<String>(403,"Acceso denegado.");
        }

    }

    @DeleteMapping("/{id}")
    public Respuesta borrarUsuario( @RequestHeader("Authorization") String token, @PathVariable long id) {
        token = token.replace("Bearer ", "");
        if(userService.borrarUsuario(id, token)){
            return new Respuesta<String>(200,"Usuario eliminado correctamente");
        } else {
            return new Respuesta<String>(403,"Acceso denegado.");

        }
    }

}
