package Wallapick.Services;

import Wallapick.Models.Product;
import Wallapick.Models.User;
import Wallapick.ModelsDTO.UserDTO;
import Wallapick.Repositories.ProductRepository;
import Wallapick.Repositories.UserRepository;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    @Autowired
    private JWTUser jwtUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public int registerUser(User user){
        User existUser = userRepository.findByUsername(user.getUsername());
        User existEmail = userRepository.findByEmail(user.getEmail());


        if(existUser != null ){
            return -1;
        }else if (existEmail != null) {
            return -2;
        }else{

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return 0;
        }
    }

    public String loginUser(String username, String password) {
        try {
            User existUser = userRepository.findByUsername(username);

            if (existUser != null && passwordEncoder.matches(password, existUser.getPassword())) {
                // Opcional: si quieres actualizar algo en el usuario, aquí va el save.
                userRepository.save(existUser);
                return jwtUser.generateToken(existUser);
            } else {
                return "ACCESS DENIED";
            }

        } catch (Exception e) {
            return "ACCESS DENIED"; // o podrías lanzar una excepción y manejarla en el controlador
        }
    }



    public UserDTO searchUser(long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }

    public int updateUser(User user, String token) {
        try {
            User userLogged = jwtUser.getUser(token);
            if (userLogged == null) {
                return 401; // Token inválido
            }

            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser == null) {
                return 404; // Usuario no encontrado
            }

            if (!existingUser.getId().equals(userLogged.getId())) {
                return 403; // Intento de modificar otro usuario
            }

            // --- Comprobación de email ---
            if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
                User userWithEmail = userRepository.findByEmail(user.getEmail());
                if (userWithEmail != null && !userWithEmail.getId().equals(existingUser.getId())) {
                    return 409; // Email en uso por otro usuario
                }
                existingUser.setEmail(user.getEmail());
            }

            // Mantener username
            user.setUsername(existingUser.getUsername());

            // Actualizar password si llega
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // Actualizar name
            if (user.getName() != null && !user.getName().isEmpty()) {
                existingUser.setName(user.getName());
            }

            // Actualizar lastname
            if (user.getLastname() != null && !user.getLastname().isEmpty()) {
                existingUser.setLastname(user.getLastname());
            }

            userRepository.save(existingUser);
            return 200; // OK

        } catch (Exception e) {
            return 500; // Error interno
        }
    }



    public boolean deleteUser(long id, String token){

        try {

            User userLogged = jwtUser.getUser(token);
            User existingUser = userRepository.findById(id).get();

            if(existingUser.getId().equals(userLogged.getId())) {
                List<Product> products = productRepository.findBySellerId(id);

                for (Product p : products){
                    p.setForSale(false);
                }
                userRepository.deleteById(id);
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}