package Wallapick.Services;

import Wallapick.Models.User;
import Wallapick.ModelsDTO.UserDTO;
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

    public String loginUser(User user){

        User existUser = userRepository.findByUsername(user.getUsername());

        if(existUser != null && passwordEncoder.matches(user.getPassword(), existUser.getPassword())){
            userRepository.save(existUser);
            return jwtUser.generateToken(existUser);
        }else{
            return "ACCESS DENIED";
        }

    }


    public UserDTO searchUser(long id) {

        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return new UserDTO(user);
    }

    public Long searchId(String token) {
        try {
            User user = jwtUser.getUser(token);
            return user.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateUser(User user, String token){

        try {

            User userLogged = jwtUser.getUser(token);
            User existingUser = userRepository.findById(user.getId()).orElse(null);

            String username = existingUser.getUsername();
            String name = existingUser.getName();
            String lastname = existingUser.getLastname();
            String email = existingUser.getEmail();

            if (existingUser == null) {
                return false;
            }

            if (existingUser.getId().equals(userLogged.getId())) {

                List<User> users = userRepository.findAll();

                // Check email is not used
                int i = 0;
                boolean emailFound = false;

                if(user.getEmail() != null){

                    while (!emailFound && i < users.size()){
                        if (users.get(i).getEmail().equals(user.getEmail())){
                            emailFound = true;
                        }
                        i++;
                    }

                    if(!emailFound){

                        existingUser.setEmail(user.getEmail());
                        user.setUsername(username);

                        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                        }

                        if (user.getName() != null && !user.getName().isEmpty()){
                            existingUser.setName(user.getName());
                        }else {
                            user.setName(name);
                        }

                        if (user.getLastname() != null && !user.getLastname().isEmpty()){
                            existingUser.setLastname(user.getLastname());
                        }else {
                            user.setLastname(lastname);
                        }

                    }else {
                        return false;
                    }

                }else {

                    user.setUsername(username);

                    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
                    }

                    if (user.getName() != null && !user.getName().isEmpty()){
                        existingUser.setName(user.getName());
                    }else {
                        user.setName(name);
                    }

                    if (user.getLastname() != null && !user.getLastname().isEmpty()){
                        existingUser.setLastname(user.getLastname());
                    }else {
                        user.setLastname(lastname);
                    }

                    user.setEmail(email);

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

            if(existingUser.getId().equals(userLogged.getId())) {
                userRepository.deleteById(id);
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
