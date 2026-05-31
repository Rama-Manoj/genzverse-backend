package com.genzverse.dto;

public class UserProfileResponse
{
    private Long id;
    private String username;
    private String email;
    private String bio;
    private String profileImage;
    private String website;
    private String linkedinUrl;
    private String githubUrl;

    public UserProfileResponse(
            Long id,
            String username,
            String email,
            String bio,
            String profileImage,
            String website,
            String linkedinUrl,
            String githubUrl)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.profileImage = profileImage;
        this.website = website;
        this.linkedinUrl = linkedinUrl;
        this.githubUrl = githubUrl;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getProfileImage() { return profileImage; }
    public String getWebsite() { return website; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public String getGithubUrl() { return githubUrl; }
}