package edu.nbu.team13.gradecenter.controllers;

import edu.nbu.team13.gradecenter.dtos.JWTokenDto;
import edu.nbu.team13.gradecenter.dtos.LoginDto;
import edu.nbu.team13.gradecenter.entities.User;
import edu.nbu.team13.gradecenter.services.UserService;
import edu.nbu.team13.gradecenter.utilities.JWTUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final JWTUtility jwtUtility;
    private final UserService userService;

    private final AuthenticationManager authenticationManager;


    public UserController(JWTUtility jwtUtility, UserService userService, AuthenticationManager authenticationManager) {
        this.jwtUtility = jwtUtility;
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
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            String role = auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList()
                    .getFirst();

            String token = this.jwtUtility.generateToken(loginRequest.getEmail(), role);

            return ResponseEntity.ok(new JWTokenDto(token));
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());

            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }
        try {
            String email = authentication.getName();
            return ResponseEntity.ok(userService.findByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}
