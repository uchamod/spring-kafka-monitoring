package com.uchamod.user.Controller;

import com.uchamod.user.Model.User;
import com.uchamod.user.Model.UserAuthResponse;
import com.uchamod.user.Model.UserLoginCredientials;
import com.uchamod.user.Service.AuthServices;
import com.uchamod.user.Service.Kproducer.UserRegisterMailEvent;
import com.uchamod.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServices userServices;
    private final UserService userService;

    @GetMapping("/")
    public String testUser(){
        return "Hello user";
    }


    @PostMapping("/register")
    public ResponseEntity<UserAuthResponse> register(@RequestBody User user){
        return userServices.register(user);
    }
    @PostMapping("/login")
    public ResponseEntity<UserAuthResponse> login(@RequestBody UserLoginCredientials userLoginCredientials){
        return userServices.login(userLoginCredientials);
    }

}








