package com.genzverse.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
        name = "saved_blogs",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "blog_id"}
                )
        }
)
public class SavedBlog 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime savedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "blog_id",
            foreignKey = @ForeignKey(
                    foreignKeyDefinition =
                            "FOREIGN KEY (blog_id) REFERENCES blogs(id) ON DELETE CASCADE"
            )
    )
    private Blog blog;

    public Long getId() {
        return id;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public User getUser() {
        return user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }
}