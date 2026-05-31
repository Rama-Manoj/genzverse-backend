package com.genzverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Blog;
import com.genzverse.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Long>
{
    List<Blog> findByUser(User user);

    Page<Blog> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable
    );

    Page<Blog> findByContentContainingIgnoreCase(
            String content,
            Pageable pageable
    );
    
    List<Blog> findTop10ByOrderByViewsDesc();

    List<Blog> findTop10ByOrderByLikeCountDesc();

    List<Blog> findTop10ByOrderByCommentCountDesc();
    
    List<Blog> findByCategory_Name(
            String categoryName
    );
    
    List<Blog> findByTags_Name(String tagName);
    
    Optional<Blog> findBySlug(String slug);
    
    List<Blog> findByDeletedFalse();

    Optional<Blog> findByIdAndDeletedFalse(Long id);

    Optional<Blog> findBySlugAndDeletedFalse(String slug);

    List<Blog> findByCategory_NameAndDeletedFalse(
            String categoryName
    );

    List<Blog> findByTags_NameAndDeletedFalse(
            String tagName
    );
}