package br.com.wswork.bestcommerceapi.controller;

import br.com.wswork.bestcommerceapi.dto.LoginResponseDTO;
import br.com.wswork.bestcommerceapi.dto.RegisterDTO;
import br.com.wswork.bestcommerceapi.exception.UserAlreadyExistsException;
import br.com.wswork.bestcommerceapi.model.User;
import br.com.wswork.bestcommerceapi.repository.UserRepository;
import br.com.wswork.bestcommerceapi.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Operation(summary = "User login", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid login credentials"),
            @ApiResponse(responseCode = "403", description = "Authentication failed")})
    @PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(
            @Parameter(description = "User credentials for login.")
            @RequestBody @Valid User user
    ) {
        try {

            var usernamePassword = new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = tokenService.generateToken((User) auth.getPrincipal());

            return new ResponseEntity<>(new LoginResponseDTO(token), HttpStatus.OK);
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid login credentials.", HttpStatus.UNAUTHORIZED);
        }
        catch (AuthenticationException e) {
            return new ResponseEntity<>("Authentication failed.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "User registration", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registration successful"),
            @ApiResponse(responseCode = "400", description = "User already exists")})

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> register(
            @Parameter(description = "User information for registration.")
            @RequestBody @Valid User user
    ) {
        try {

            if (userRepository.findByLogin(user.getLogin()) != null)
                throw new UserAlreadyExistsException("User with login " + user.getLogin() + " already exists.");

            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

            User newUser = new User(user.getLogin(), encryptedPassword, user.getRole());

            userRepository.save(newUser);

            return new ResponseEntity<>(RegisterDTO.convertUserToDTO(user), HttpStatus.OK);
        }
        catch (UserAlreadyExistsException exc) {
            return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}