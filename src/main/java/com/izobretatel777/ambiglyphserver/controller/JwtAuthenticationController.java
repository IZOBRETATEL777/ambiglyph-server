package com.izobretatel777.ambiglyphserver.controller;

import com.izobretatel777.ambiglyphserver.dto.JwtRequest;
import com.izobretatel777.ambiglyphserver.dto.JwtResponse;
import com.izobretatel777.ambiglyphserver.security.JwtUtil;
import com.izobretatel777.ambiglyphserver.service.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authenticate")
@Tag(name = "Authentication", description = "Controller for authentication")
@RequiredArgsConstructor
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @PostMapping
    @Operation(
            summary = "Login",
            description = """
                    Get JWT token and encryption/decryption key for messages by login (e-mail) and password.
                    """
    )
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getLogin(), jwtRequest.getPassword()));
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtRequest.getLogin());
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/logout")
    @Operation(
            summary = "Logout",
            description = """
                    Logout by invalidating JWT token.
                    """
    )
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "Bye";
    }
}
