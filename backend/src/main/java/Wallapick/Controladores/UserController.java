package Wallapick.Controladores;

import Wallapick.Modelos.Usuario;
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

    @PostMapping("/")
    public  ResponseEntity<?> registrarUsuario(@RequestBody Usuario user){

        String res = userService.resgistrar(user);
        if(res.equalsIgnoreCase("REGISTRADO")){
            return ResponseEntity.ok("Usuario registrado correctamente");
        } else if (res.equalsIgnoreCase("EXISTE")) {
            return ResponseEntity.status(409).body("El usuario ya existe");
        }
        else{
            return ResponseEntity.badRequest().body("ERROR");
        }
    }

    @PostMapping("/login")
    public ResponseEntity <?> login(@RequestBody Usuario user) {
        String respuesta = userService.Login(user);
        if(respuesta.equalsIgnoreCase("ACCESO DENEGADO")){
            return ResponseEntity.status(404).body("USUARIO NO ENCONTRADO O CONTRASEÑA INCORRECTA");
        }
        return ResponseEntity.ok("Bienvenido, su token es ---->   " + respuesta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuario(@PathVariable long id, @RequestHeader("Authorization") String token ){
        token = token.replace("Bearer ", "");
        Usuario u = userService.buscarUsuario(id, token);
        if(u == null){
            return ResponseEntity.status(404).body("USUARIO NO ENCONTRADO");
        }
        return ResponseEntity.ok(u);
    }
    @GetMapping("/")
    public ResponseEntity<?> buscarId(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        Long userId = userService.obtenerIdSiTokenValido(token);

        if (userId != null) {
            return ResponseEntity.ok("ID del usuario: " + userId);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@RequestBody Usuario user,@RequestHeader("Authorization") String token ){
        token = token.replace("Bearer ", "");

        if(userService.actualizarUsario(user, token)){
            return ResponseEntity.ok("Usuario actualizado correctamente" + user);
        } else {
            return ResponseEntity.status(403).body("ACCESO DENEGADO");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarUsuario( @RequestHeader("Authorization") String token, @PathVariable long id) {
        token = token.replace("Bearer ", "");
        if(userService.borrarUsuario(id, token)){
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(403).body("ACCESO DENEGADO");
        }

    }

}
