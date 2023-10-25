package com.ngtu.WaybillGlonassComparison.services.security;

import com.ngtu.WaybillGlonassComparison.entities.security.Role;
import com.ngtu.WaybillGlonassComparison.entities.security.SecurityUser;
import com.ngtu.WaybillGlonassComparison.entities.security.Status;
import com.ngtu.WaybillGlonassComparison.entities.security.User;
import com.ngtu.WaybillGlonassComparison.repositories.security.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }

    public void registerUser(HttpServletRequest request){
        User user = new User();
        user.setEmail(request.getParameter("username"));
        user.setPassword(passwordEncoder.encode(request.getParameter("password")));
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
    }
}
