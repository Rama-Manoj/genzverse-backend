package com.genzverse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.CommentResponse;
import com.genzverse.dto.CreateCommentRequest;
import com.genzverse.entity.Blog;
import com.genzverse.entity.Comment;
import com.genzverse.entity.NotificationType;
import com.genzverse.entity.User;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.CommentRepository;
import com.genzverse.repository.UserRepository;

@Service
public class CommentService 
{
    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;
    
    private final NotificationService notificationService;

    

    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository,
			UserRepository userRepository, NotificationService notificationService) {
		super();
		this.commentRepository = commentRepository;
		this.blogRepository = blogRepository;
		this.userRepository = userRepository;
		this.notificationService = notificationService;
	}

	public CommentResponse addComment(
            Long blogId,
            CreateCommentRequest request)
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

        Comment comment = new Comment();

        comment.setContent(request.getContent());

        comment.setCreatedAt(LocalDateTime.now());

        comment.setUser(user);

        comment.setBlog(blog);

        if(request.getParentCommentId() != null)
        {
            Comment parent = commentRepository
                    .findById(request.getParentCommentId())
                    .orElseThrow(() ->
                            new RuntimeException("Parent comment not found"));

            comment.setParentComment(parent);
        }

        Comment savedComment =
                commentRepository.save(comment);
        
        notificationService.createNotification(
                user,
                blog.getUser(),
                blog,
                NotificationType.COMMENT,
                user.getUsername() + " commented on your blog"
        );

        blog.setCommentCount(blog.getCommentCount() + 1);

        blogRepository.save(blog);

        return new CommentResponse(
                savedComment.getId(),
                savedComment.getContent(),
                savedComment.getUser().getUsername(),
                savedComment.getCreatedAt()
        );
    }

    public List<CommentResponse> getCommentsByBlog(Long blogId)
    {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found"));

        return commentRepository.findByBlog(blog)
                .stream()
                .map(comment ->
                        new CommentResponse(
                                comment.getId(),
                                comment.getContent(),
                                comment.getUser().getUsername(),
                                comment.getCreatedAt()
                        ))
                .toList();
    }

    public String deleteComment(Long commentId)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new RuntimeException("Comment not found"));

        if(!comment.getUser().getEmail().equals(email))
        {
            throw new RuntimeException("Unauthorized");
        }

        Blog blog = comment.getBlog();

        blog.setCommentCount(blog.getCommentCount() - 1);

        blogRepository.save(blog);

        commentRepository.delete(comment);

        return "Comment deleted successfully";
    }
}