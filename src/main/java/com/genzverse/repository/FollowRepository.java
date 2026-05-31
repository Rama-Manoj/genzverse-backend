package com.genzverse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genzverse.entity.Follow;
import com.genzverse.entity.User;

public interface FollowRepository
        extends JpaRepository<Follow, Long>
{
    Optional<Follow> findByFollowerAndFollowing(
            User follower,
            User following
    );

    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowing(User following);
    
    long countByFollowing(User user);

    long countByFollower(User user);
}