package com.genzverse.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.ForgotPasswordRequest;
import com.genzverse.dto.LoginRequest;
import com.genzverse.dto.LoginResponse;
import com.genzverse.dto.RegisterRequest;
import com.genzverse.dto.ResetPasswordRequest;
import com.genzverse.dto.VerifyEmailRequest;
import com.genzverse.entity.Role;
import com.genzverse.entity.User;
import com.genzverse.exception.ResourceNotFoundException;
import com.genzverse.exception.UnauthorizedException;
import com.genzverse.repository.UserRepository;

@Service
public class AuthService 
{
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

	private EmailService emailService;


    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService,
			EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.emailService = emailService;
	}

	public String register(RegisterRequest request)
    {
        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.USER);

        user.setCreatedAt(LocalDateTime.now());
        
        user.setEmailVerified(false);

        user.setVerificationToken(
                UUID.randomUUID().toString()
        );

        userRepository.save(user);
        
        emailService.sendEmail(
                user.getEmail(),
                "Verify Your GenZVerse Account",
                "Your verification token is: "
                        + user.getVerificationToken()
        );

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request)
    {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));
        if(!user.isEmailVerified())
        {
            throw new UnauthorizedException(
                    "Please verify your email first"
            );
        }

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!passwordMatches)
        {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getRole().name()
        );
    }
    
    public String forgotPassword(
            ForgotPasswordRequest request)
    {
        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));

        String token = UUID.randomUUID().toString();

        user.setResetToken(token);

        user.setResetTokenExpiry(
                LocalDateTime.now().plusMinutes(30)
        );

        userRepository.save(user);
        
        emailService.sendEmail(
                user.getEmail(),
                "Reset Your Password",
                "Your password reset token is: "
                        + token
        );

        return "Password reset email sent";

    }
    
    public String resetPassword(
            ResetPasswordRequest request)
    {
        User user = userRepository
                .findByResetToken(
                        request.getToken()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid token"
                        ));

        if(user.getResetTokenExpiry()
                .isBefore(LocalDateTime.now()))
        {
            throw new RuntimeException(
                    "Token expired"
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        user.setResetToken(null);

        user.setResetTokenExpiry(null);

        userRepository.save(user);

        return "Password reset successful";
    }
    
    public String verifyEmail(
            VerifyEmailRequest request)
    {
        User user = userRepository
                .findByVerificationToken(
                        request.getToken()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid verification token"
                        ));

        user.setEmailVerified(true);

        user.setVerificationToken(null);

        userRepository.save(user);

        return "Email verified successfully";
    }


}