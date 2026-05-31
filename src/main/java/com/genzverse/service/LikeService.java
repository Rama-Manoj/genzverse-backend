package com.genzverse.service;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.entity.Blog;
import com.genzverse.entity.Like;
import com.genzverse.entity.NotificationType;
import com.genzverse.entity.User;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.LikeRepository;
import com.genzverse.repository.UserRepository;

@Service
public class LikeService 
{
    private final LikeRepository likeRepository;

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;
    
    private final NotificationService notificationService;


    public LikeService(LikeRepository likeRepository, BlogRepository blogRepository, UserRepository userRepository,
			NotificationService notificationService) {
		super();
		this.likeRepository = likeRepository;
		this.blogRepository = blogRepository;
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}



	public String toggleLike(Long blogId)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found"));

        Like existingLike = likeRepository
                .findByUserAndBlog(user, blog)
                .orElse(null);

        if(existingLike != null)
        {
            likeRepository.delete(existingLike);

            blog.setLikeCount(blog.getLikeCount() - 1);

            blogRepository.save(blog);

            return "Blog unliked";
        }

        Like like = new Like();

        like.setUser(user);

        like.setBlog(blog);

        like.setCreatedAt(LocalDateTime.now());

        likeRepository.save(like);
        
        notificationService.createNotification(
                user,
                blog.getUser(),
                blog,
                NotificationType.LIKE,
                user.getUsername() + " liked your blog"
        );

        blog.setLikeCount(blog.getLikeCount() + 1);

        blogRepository.save(blog);

        return "Blog liked";
    }
	
	
	
}