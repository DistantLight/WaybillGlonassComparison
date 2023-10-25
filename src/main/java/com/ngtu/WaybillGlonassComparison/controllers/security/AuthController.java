package com.ngtu.WaybillGlonassComparison.controllers.security;

import com.ngtu.WaybillGlonassComparison.services.security.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(@Qualifier("userDetailsServiceImpl")UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/security/login";
    }

    @GetMapping("/register")
    public String getRegistrationPage(){
        return "/security/registration";
    }

    @PostMapping("/register")
    public String registerUser(HttpServletRequest request){
        userDetailsService.registerUser(request);
        return "/index";
    }
}
