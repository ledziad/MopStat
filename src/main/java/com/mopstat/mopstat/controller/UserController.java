package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.UserDTO;
import com.mopstat.mopstat.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDTO register(@Valid @RequestBody UserDTO dto) {
        return userService.register(dto);
    }
}
