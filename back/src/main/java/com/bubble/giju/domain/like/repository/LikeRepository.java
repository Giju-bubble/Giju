package com.bubble.giju.domain.like.repository;

import com.bubble.giju.domain.like.entity.Like;
import com.bubble.giju.domain.user.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUser_UserIdAndDrink_id(UUID userId, Long drinkId);

    List<Like> findByUser_UserIdAndDeleteIsFalse(UUID userUserId, Sort sort);

    Optional<Like> findByUser_UserIdAndId(UUID userUserId, Long id);
}
