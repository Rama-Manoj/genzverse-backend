package com.genzverse.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.genzverse.dto.BlogResponse;
import com.genzverse.dto.CreateBlogRequest;
import com.genzverse.dto.ShareResponse;
import com.genzverse.entity.Blog;
import com.genzverse.entity.Category;
import com.genzverse.entity.Tag;
import com.genzverse.entity.User;
import com.genzverse.exception.ResourceNotFoundException;
import com.genzverse.exception.UnauthorizedException;
import com.genzverse.repository.BlogRepository;
import com.genzverse.repository.CategoryRepository;
import com.genzverse.repository.TagRepository;
import com.genzverse.repository.UserRepository;

@Service
public class BlogService
{
    private final BlogRepository blogRepository;

    private final UserRepository userRepository;
    
    private final CategoryRepository categoryRepository;
    
    private final TagRepository tagRepository;


    public BlogService(BlogRepository blogRepository, UserRepository userRepository,
			CategoryRepository categoryRepository, TagRepository tagRepository) {
		super();
		this.blogRepository = blogRepository;
		this.userRepository = userRepository;
		this.categoryRepository = categoryRepository;
		this.tagRepository = tagRepository;
	}

	public BlogResponse createBlog(CreateBlogRequest request)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Category category = categoryRepository
                .findByName(request.getCategory())
                .orElseGet(() ->
                {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategory());
                    return categoryRepository.save(newCategory);
                });

        Blog blog = new Blog();

        blog.setTitle(request.getTitle());
        
        blog.setSlug(
                generateSlug(
                        request.getTitle()
                )
        );

        blog.setContent(request.getContent());

        blog.setThumbnailUrl(request.getThumbnailUrl());

        blog.setCategory(category);
        
        Set<Tag> tags = new HashSet<>();

        for(String tagName : request.getTags())
        {
            Tag tag = tagRepository
                    .findByName(tagName)
                    .orElseGet(() ->
                    {
                        Tag newTag = new Tag();
                        newTag.setName(tagName);
                        return tagRepository.save(newTag);
                    });

            tags.add(tag);
        }

        blog.setTags(tags);

        blog.setCreatedAt(LocalDateTime.now());

        blog.setUpdatedAt(LocalDateTime.now());

        blog.setUser(user);

        Blog savedBlog = blogRepository.save(blog);

        return mapToResponse(savedBlog);
    }

    public List<BlogResponse> getAllBlogs()
    {
        return blogRepository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public BlogResponse getBlogById(Long id)
    {
        Blog blog = blogRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Blog not found"));

        blog.setViews(blog.getViews() + 1);

        blogRepository.save(blog);

        return mapToResponse(blog);
    }

    public BlogResponse updateBlog(
            Long id,
            CreateBlogRequest request)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Blog blog = blogRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Blog not found"));

        if(!blog.getUser().getEmail().equals(email))
        {
            throw new UnauthorizedException("Unauthorized");
        }

        blog.setTitle(request.getTitle());
        
        blog.setSlug(
                generateSlug(
                        request.getTitle()
                )
        );

        blog.setContent(request.getContent());

        blog.setThumbnailUrl(request.getThumbnailUrl());

        blog.setUpdatedAt(LocalDateTime.now());

        Blog updatedBlog = blogRepository.save(blog);

        return mapToResponse(updatedBlog);
    }

    public String deleteBlog(Long id)
    {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Blog blog = blogRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Blog not found"));

        if(!blog.getUser().getEmail().equals(email))
        {
            throw new UnauthorizedException("Unauthorized");
        }
        
        blog.setDeleted(true);

        blogRepository.save(blog);

        return "Blog moved to recycle bin";

    }

    private BlogResponse mapToResponse(Blog blog)
    {
        return new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getThumbnailUrl(),
                blog.getUser().getUsername(),
                blog.getViews(),
                blog.getLikeCount(),
                blog.getCommentCount(),
                blog.getShareCount(),
                blog.getCreatedAt()
        );
    }

    public List<BlogResponse> searchBlogsByTitle(
            String keyword,
            int page,
            int size,
            String sortDirection)
    {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<Blog> blogs = blogRepository
                .findByTitleContainingIgnoreCase(
                        keyword,
                        pageable
                );

        return blogs.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<BlogResponse> searchBlogsByContent(
            String keyword,
            int page,
            int size,
            String sortDirection)
    {
        Sort sort = sortDirection.equalsIgnoreCase("asc")
                ? Sort.by("createdAt").ascending()
                : Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        Page<Blog> blogs = blogRepository
                .findByContentContainingIgnoreCase(
                        keyword,
                        pageable
                );

        return blogs.stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public List<BlogResponse> getTrendingByViews()
    {
        return blogRepository
                .findTop10ByOrderByViewsDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public List<BlogResponse> getTrendingByLikes()
    {
        return blogRepository
                .findTop10ByOrderByLikeCountDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public List<BlogResponse> getTrendingByComments()
    {
        return blogRepository
                .findTop10ByOrderByCommentCountDesc()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public ShareResponse shareBlog(Long blogId)
    {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Blog not found"
                        ));

        blog.setShareCount(
                blog.getShareCount() + 1
        );

        blogRepository.save(blog);

        String shareUrl =
                "http://localhost:8080/blogs/"
                + blog.getId();

        return new ShareResponse(
                shareUrl,
                blog.getShareCount()
        );
    }
    
    public List<BlogResponse>
    getBlogsByCategory(
            String categoryName)
    {
        return blogRepository
                .findByCategory_NameAndDeletedFalse(
                        categoryName
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    public List<BlogResponse> getBlogsByTag(
            String tagName)
    {
        return blogRepository
                .findByTags_NameAndDeletedFalse(tagName)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    private String generateSlug(String title)
    {
        return title
                .toLowerCase()
                .trim()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }
    
    public BlogResponse getBlogBySlug(
            String slug)
    {
        Blog blog = blogRepository
                .findBySlugAndDeletedFalse(slug)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Blog not found"
                        ));

        blog.setViews(
                blog.getViews() + 1
        );

        blogRepository.save(blog);

        return mapToResponse(blog);
    }
    
}
