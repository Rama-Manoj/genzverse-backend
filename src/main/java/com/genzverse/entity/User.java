package com.genzverse.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(length = 500)
    private String bio;

    private String profileImage;

    private String website;

    private String linkedinUrl;

    private String githubUrl;

    private LocalDateTime createdAt;

    
    private String resetToken;

    private LocalDateTime resetTokenExpiry;
    
    private boolean emailVerified = false;

    private String verificationToken;
    

	public boolean isEmailVerified() {
		return emailVerified;
	}



	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}



	public String getVerificationToken() {
		return verificationToken;
	}



	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}



	public User(Long id, String username, String email, String password, Role role, String bio, String profileImage,
			String website, String linkedinUrl, String githubUrl, LocalDateTime createdAt, String resetToken,
			LocalDateTime resetTokenExpiry, boolean emailVerified, String verificationToken) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.bio = bio;
		this.profileImage = profileImage;
		this.website = website;
		this.linkedinUrl = linkedinUrl;
		this.githubUrl = githubUrl;
		this.createdAt = createdAt;
		this.resetToken = resetToken;
		this.resetTokenExpiry = resetTokenExpiry;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
	}



	public String getResetToken() {
		return resetToken;
	}



	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}



	public LocalDateTime getResetTokenExpiry() {
		return resetTokenExpiry;
	}



	public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
		this.resetTokenExpiry = resetTokenExpiry;
	}



	public User() {
		// TODO Auto-generated constructor stub
	}

    

	public String getBio() {
		return bio;
	}



	public void setBio(String bio) {
		this.bio = bio;
	}



	public String getProfileImage() {
		return profileImage;
	}



	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}



	public String getWebsite() {
		return website;
	}



	public void setWebsite(String website) {
		this.website = website;
	}



	public String getLinkedinUrl() {
		return linkedinUrl;
	}



	public void setLinkedinUrl(String linkedinUrl) {
		this.linkedinUrl = linkedinUrl;
	}



	public String getGithubUrl() {
		return githubUrl;
	}



	public void setGithubUrl(String githubUrl) {
		this.githubUrl = githubUrl;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
    
    

}