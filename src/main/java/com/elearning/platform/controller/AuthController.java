package com.elearning.platform.controller;

import com.elearning.platform.dto.AuthRequest;
import com.elearning.platform.dto.RegisterRequest;
import com.elearning.platform.dto.UserDTO;
import com.elearning.platform.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserDTO login(@Valid @RequestBody AuthRequest request) {
        return userService.authenticate(request);
    }

    @GetMapping("/users")
    public List<UserDTO> list() {
        return userService.listAll();
    }
}
