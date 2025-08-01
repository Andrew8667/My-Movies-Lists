package mymovielist.mymovielist.controllers;

import java.lang.StackWalker.Option;
import java.util.Optional;

import mymovielist.mymovielist.dto.LoginDTO;
import mymovielist.mymovielist.dto.UserDTO;
import mymovielist.mymovielist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mymovielist.mymovielist.auth.JwtUtil;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.repositories.UserRepository;

/**
 * Handles REST operations dealing with user entities
 * @author Andrew Gee
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    /**
     * Handles registration of user
     * @param user to be registered
     * @return 200 response with new user if they don't exist yet, unauthorized response otherwise
     */
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        return userService.register(user);
    }

    /**
     * Checks the validity of the login details to the app
     * @param loginDTO contains the email and password inputted in login page
     * @return 200 response if the user matches, unauthorized response otherwise
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    /**
     * Gets the user that is making the request
     * @param authHeader contains the token
     * @return the user making the request
     */
    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getUser(@RequestHeader("Authorization") String authHeader){
        return userService.getUser(authHeader);
    }
}
