package Wallapick.Services;

import Wallapick.Models.User;
import Wallapick.ModelsDTO.UserDTO;
import Wallapick.Repositories.UserRepository;
import Wallapick.Utils.JWTUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JWTUser jwtUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(User user){

        User existUser = userRepository.findByUsername(user.getUsername());

        if(existUser == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return "Registered.";
        }else{
            return "Exists.";
        }
    }

    public String loginUser(User user){

        User existUser = userRepository.findByUsername(user.getUsername());

        if(existUser != null && passwordEncoder.matches(user.getPassword(),existUser.getPassword())){
            existUser.setRole("LOGGED");
            return jwtUser.generateToken(existUser);
        }else{
            return "Access denied.";
        }

    }

    public UserDTO searchUser(long id, String token){

        try {

            jwtUser.getUser(token);
            User user = userRepository.findById(id).get();
            UserDTO userDTO = new UserDTO(user);
            return userDTO;

        } catch (Exception e) {
            return null;
        }
    }

    public Long searchId(String token) {

        try {

            User userLogged = jwtUser.getUser(token);

            if (userLogged != null && "LOGGED".equals(userLogged.getRole())) {
                return userLogged.getId();
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateUser(User user, String token) {

        try {

            User userLogged = jwtUser.getUser(token);
            User existingUser = userRepository.findById(user.getId()).orElse(null);

            if (existingUser == null) {
                return false;
            }

            if (userLogged.getRole().equals("LOGGED") && existingUser.getId().equals(userLogged.getId())) {

                existingUser.setUsername(user.getUsername());
                existingUser.setName(user.getName());
                existingUser.setLastname(user.getLastname());
                existingUser.setEmail(user.getEmail());

                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                }

                userRepository.save(existingUser);
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteUser(long id, String token){

        try {

            User userLogged = jwtUser.getUser(token);
            User existingUser = userRepository.findById(id).get();

            if(userLogged.getRole().equals("LOGGED") || existingUser.getId().equals(userLogged.getId())) {
                userRepository.deleteById(id);
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}