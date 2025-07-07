package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.JWTokenDto;
import edu.nbu.team13.gradecenter.dtos.LoginDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.services.UserService;
import edu.nbu.team13.gradecenter.utilities.JWTUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JWTokenDto> login(@RequestBody LoginDto loginRequest) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                );
        try {
            authenticationManager.authenticate(authToken);

            String token = JWTUtility.generateToken(loginRequest.getEmail());
            return ResponseEntity.ok(new JWTokenDto(token));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}
