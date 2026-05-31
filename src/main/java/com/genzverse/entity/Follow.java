package com.genzverse.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
        name = "followers",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "follower_id",
                                "following_id"
                        }
                )
        }
)
public class Follow 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime followedAt;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    public Long getId() {
        return id;
    }

    public LocalDateTime getFollowedAt() {
        return followedAt;
    }

    public User getFollower() {
        return follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFollowedAt(LocalDateTime followedAt) {
        this.followedAt = followedAt;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}