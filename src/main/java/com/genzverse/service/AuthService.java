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

		try
		{
			String verificationLink =
			        "https://genzversefrontend.vercel.app/reset-password?token="
			        + user.getVerificationToken();
			
			String html = """
					<html>
					<body>
					    <h2>Welcome to GenZVerse</h2>

					    <p>Please verify your email address.</p>

					    <a href="%s"
					       style="
					           background:#2563eb;
					           color:white;
					           padding:12px 20px;
					           text-decoration:none;
					           border-radius:5px;">
					        Verify Email
					    </a>

					</body>
					</html>
					""".formatted(verificationLink);

					emailService.sendEmail(
					        user.getEmail(),
					        "Verify Your GenZVerse Account",
					        html
					);
			
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		    throw new RuntimeException(e);
		} 
		
		return "User registered successfully.";
    }

    public LoginResponse login(LoginRequest request)
    {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!user.isEmailVerified())
        {
        	throw new RuntimeException(
        			"Please verify your email first"
        			);
        }

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
        
        String resetLink =
                "https://your-frontend-url.vercel.app/reset-password?token="
                + token;

        String html = """
        <html>
        <body>

        <h2>Password Reset</h2>

        <p>Click the button below to reset your password.</p>

        <a href="%s"
           style="
              background:#dc2626;
              color:white;
              padding:12px 20px;
              text-decoration:none;
              border-radius:5px;">
              Reset Password
        </a>

        </body>
        </html>
        """.formatted(resetLink);

        emailService.sendEmail(
                user.getEmail(),
                "Reset Your Password",
                html
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
