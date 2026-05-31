package com.genzverse.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;


    private LocalDateTime createdAt;
    
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    
    @Column(name = "is_read")
    private boolean read = false;
    
    public Notification() {
		// TODO Auto-generated constructor stub
	}


	public Notification(Long id, String message, NotificationType type, LocalDateTime createdAt, User sender,
			User receiver, Blog blog, boolean read) {
		super();
		this.id = id;
		this.message = message;
		this.type = type;
		this.createdAt = createdAt;
		this.sender = sender;
		this.receiver = receiver;
		this.blog = blog;
		this.read = read;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public NotificationType getType() {
		return type;
	}


	public void setType(NotificationType type) {
		this.type = type;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public User getSender() {
		return sender;
	}


	public void setSender(User sender) {
		this.sender = sender;
	}


	public User getReceiver() {
		return receiver;
	}


	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}


	public Blog getBlog() {
		return blog;
	}


	public void setBlog(Blog blog) {
		this.blog = blog;
	}


	public boolean isRead() {
		return read;
	}


	public void setRead(boolean read) {
		this.read = read;
	}
   
	
    
}