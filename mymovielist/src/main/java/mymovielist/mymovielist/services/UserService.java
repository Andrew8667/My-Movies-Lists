package mymovielist.mymovielist.services;

import mymovielist.mymovielist.auth.JwtUtil;
import mymovielist.mymovielist.dto.LoginDTO;
import mymovielist.mymovielist.dto.UserDTO;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

/**
 * Handles business logic for the UserController
 * @author Andrew Gee
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Handles the registration of users
     * Used in "/user/register" mapping
     * @param user to be registered
     * @return response entity with the newly registered user if they don't exist yet, false otherwise
     */
    public ResponseEntity<User> register(User user){
        //if the user already exists
        if(userRepository.existsById(user.getEmail())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        //user doesn't exist
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    /**
     * Checks the validity of the login details to the app
     * Used in "user/login"
     * @param loginDTO contains the email and password inputted in login page
     * @return jwt token if successful, false otherwise
     */
    public ResponseEntity<String> login(LoginDTO loginDTO){
        Optional<User> user = userRepository.findById(loginDTO.getEmail());
        if(user.isPresent()){
            //check password
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if(bCryptPasswordEncoder.matches(loginDTO.getPassword(),user.get().getPassword())){
                return ResponseEntity.ok(jwtUtil.generateToken(loginDTO.getEmail()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    /**
     * Gets the user that is making the request
     * @param authHeader contains the token
     * @return the user making the request
     */
    public ResponseEntity<UserDTO> getUser(String authHeader){
        Optional<User> user = userRepository.findById(jwtUtil.extractUsername(authHeader.substring(7)));
        if(user.isPresent()){
            UserDTO userDTO = new UserDTO(user.get().getEmail(),user.get().getPassword(),user.get().getName());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
