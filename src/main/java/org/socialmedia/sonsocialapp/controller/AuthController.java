package org.socialmedia.sonsocialapp.controller;

import jdk.jshell.spi.ExecutionControl;
import org.socialmedia.sonsocialapp.config.JwtProvider;
import org.socialmedia.sonsocialapp.dto.VerifyUserDTO;
import org.socialmedia.sonsocialapp.exceptions.UserException;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.repo.UserRepository;
import org.socialmedia.sonsocialapp.response.AuthResponse;
import org.socialmedia.sonsocialapp.service.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsServiceImpl customUserDetailsServiceImpl;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CustomUserDetailsServiceImpl customUserDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.customUserDetailsServiceImpl = customUserDetailsServiceImpl;
    }

    @PostMapping("/kayit-ol")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String birthDate = user.getBirthDate();

        User isEmailExist = userRepository.findByEmail(email);
        if (isEmailExist!= null) {
            throw new UserException("Email başka bir kullanıcıda kullanılmaktadır.");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setBirthDate(birthDate);
        createdUser.setVerificationCode(generateVerificationCode());
        createdUser.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        createdUser.setEnabled(false);
        customUserDetailsServiceImpl.sendVerificationEmail(user);
        userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse response = new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(response, HttpStatus.CREATED);
    }

    @PostMapping("/dogrula")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDTO verifyUserDTO) {
        try {
            customUserDetailsServiceImpl.verifyUser(verifyUserDTO);
            return ResponseEntity.ok("Kullanıcı doğrulandı");
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/tekrar-dogrula")
    public ResponseEntity<?> reSendVerificationEmail(@RequestParam String email) {
        try {
            customUserDetailsServiceImpl.reSendVerificationCode(email);
            return ResponseEntity.ok("Doğrulama kodu tekrar gönderildi.");
        }catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/giris-yap")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user){
        String username = user.getEmail();
        String password = user.getPassword();

        Authentication authentication = authenticate(username,password);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse(token,true);
        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsername(email);
        if (userDetails ==null) {
            throw new BadCredentialsException("Geçersiz kullanıcı.");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Geçersiz kullanıcı adı veya şifre.");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
    }
    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

}
