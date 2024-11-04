package org.socialmedia.sonsocialapp.service;

import jakarta.mail.MessagingException;
import org.socialmedia.sonsocialapp.dto.VerifyUserDTO;
import org.socialmedia.sonsocialapp.model.User;
import org.socialmedia.sonsocialapp.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public CustomUserDetailsServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null || user.isLogin_with_google()){
            throw new UsernameNotFoundException("Bu kullanıcı bulunamadı. "+username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }

    public void verifyUser(VerifyUserDTO input) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(input.getEmail()));
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                    throw new RuntimeException("Doğrulama Kodu süresi geçmiş.");
                }
                if (user.getVerificationCode().equals(input.getVerificationCode())) {
                    user.setEnabled(true);
                    user.setVerificationCode(null);
                    user.setVerificationCodeExpiresAt(null);
                    userRepository.save(user);
                }else {
                    throw new RuntimeException("Doğrulama kodu hatalı.");
                }
            }else {
                throw new RuntimeException("Kullanıcı bulunamadı.");
            }
    }
    public void reSendVerificationCode(String email) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            if(user.isEnabled()) {
                throw new RuntimeException("Hesap zaten doğrulanmış.");
            }
            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(user);
            userRepository.save(user);
        }else {
            throw new RuntimeException("Kullanıcı bulunamadı.");
        }
    }

    public void sendVerificationEmail(User user) {
        String subject = "Hesabınızı Doğrulayın";
        String verificationCode = user.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Yeni maceraya hoşgeldin!</h2>"
                + "<p style=\"font-size: 16px;\">Hesabı kullanabilmen için bir adım kaldı, kodu onayla ve hemen başla:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Aktivasyon Kodu:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private String generateVerificationCode() {
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

}
