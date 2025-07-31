package mymovielist.mymovielist.controllers;

import java.lang.StackWalker.Option;
import java.util.Optional;

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

import mymovielist.mymovielist.Model.AuthRequest;
import mymovielist.mymovielist.auth.JwtUtil;
import mymovielist.mymovielist.entities.User;
import mymovielist.mymovielist.repositories.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        if(userRepository.findById(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body("There is already an account with that email");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); //encodes password
        newUser.setName(user.getName());
        userRepository.save(newUser);
        return ResponseEntity.ok("New user has been successfully created!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest){
        Optional<User> user = userRepository.findById(authRequest.getEmail());
        if(user.isPresent()){
            if(new BCryptPasswordEncoder().matches(authRequest.getPassword(), user.get().getPassword())){
                return ResponseEntity.ok(jwtUtil.generateToken(authRequest.getEmail()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error logging in");
    }

    @GetMapping("/getName")
    public ResponseEntity<String> getName(@RequestHeader("Authorization") String token){
        String filteredToken = token.substring(7);
        String email = jwtUtil.extractUsername(filteredToken);
        Optional<User> user = userRepository.findById(email);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get().getName());
        }
        return ResponseEntity.badRequest().body("Could not find the user's name");
    }
}
