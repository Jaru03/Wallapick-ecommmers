package Wallapick.Servicios;

import Wallapick.Modelos.Usuario;
import Wallapick.Repositorios.UsuarioRepositorio;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private  UsuarioRepositorio userRepo ;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  JWTUser jwtUser;


    public String Register(Usuario user){
        Usuario existUser = userRepo.findByUsername(user.getUsername());

        if(existUser == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return "REGISTRADO";
        }else{
            return "EXISTE";
        }
    }
    public String Login(Usuario user){
        Usuario existUser = userRepo.findByUsername(user.getUsername());

        if(existUser != null && passwordEncoder.matches(user.getPassword(),existUser.getPassword())){
            existUser.setRole("LOGGED");
            return jwtUser.GenerateToken(existUser);
        }else{
            return "ACCESO DENEGADO";
        }

    }

    public Usuario getUser(long id,String token){
        try {
            jwtUser.ObtenerUsuario(token);
            Usuario u=  userRepo.findById(id).get();
            u.setPassword("");
            return u;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateUser(Usuario user,String token){
        try {
            Usuario userLogged = jwtUser.ObtenerUsuario(token);
            Usuario existingUser = userRepo.findById(user.getId()).get();
            if(userLogged.getRole().equals("LOGGED") || existingUser.getId().equals(userLogged.getId())) {

                userRepo.save(userLogged);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteUser(long id,String token){
        try {
            Usuario userLogged = jwtUser.ObtenerUsuario(token);
            Usuario existingUser = userRepo.findById(id).get();
            if(userLogged.getRole().equals("LOGGED") || existingUser.getId().equals(userLogged.getId())) {
                userRepo.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public Long obtenerIdSiTokenValido(String token) {
        try {
            Usuario userLogged = jwtUser.ObtenerUsuario(token);
            if (userLogged != null && "LOGGED".equals(userLogged.getRole())) {
                return userLogged.getId();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
