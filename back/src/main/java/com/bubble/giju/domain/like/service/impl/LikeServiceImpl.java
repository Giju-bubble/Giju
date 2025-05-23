package com.bubble.giju.domain.like.service.impl;

import com.bubble.giju.domain.drink.entity.Drink;
import com.bubble.giju.domain.drink.repository.DrinkRepository;
import com.bubble.giju.domain.like.dto.LikeDto;
import com.bubble.giju.domain.like.entity.Like;
import com.bubble.giju.domain.like.repository.LikeRepository;
import com.bubble.giju.domain.like.service.LikeService;
import com.bubble.giju.domain.user.entity.User;
import com.bubble.giju.domain.user.repository.UserRepository;
import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LikeServiceImpl implements LikeService {

    private final DrinkRepository drinkRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Transactional
    @Override
    public List<LikeDto.Response> getLike(String userId) {
        userRepository.findById(UUID.fromString(userId)).orElseThrow(
                () -> new CustomException(ErrorCode.NON_EXISTENT_USER)
        );

        // TODO: 되는지 확인해야됨
        List<Like> likeList = likeRepository.findByUser_UserIdAndDeleteIsFalse(UUID.fromString(userId), Sort.by("create_at"));

        return likeList.stream().map(LikeDto.Response::fromEntity).toList();
    }

    @Transactional
    @Override
    public LikeDto.Response toggleLike(String userId, Long drinkId, Boolean likeRequest) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(
                () -> new CustomException(ErrorCode.NON_EXISTENT_USER)
        );

        Drink drink = drinkRepository.findById(drinkId).orElseThrow(
                () -> new CustomException(ErrorCode.NON_EXISTENT_DRINK)
        );

        Optional<Like> optionalLike = likeRepository.findByUser_UserIdAndId(UUID.fromString(userId), drinkId);

        /*
         * xor 연산
         * 찜 요청 ^ Optional
         * t ^ t = f
         * t ^ f = t
         * f ^ t = t
         * f ^ f = f
         * */
        if (likeRequest ^ optionalLike.isPresent()) {
            throw new CustomException(ErrorCode.INVALID_LIKE);
        }

        Like like = null;
        if (likeRequest) {
            like = Like.builder()
                    .user(user)
                    .drink(drink)
                    .delete(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            likeRepository.save(like);
        } else {
            like = optionalLike.get();

            like.deleteLike();
        }

        return LikeDto.Response.fromEntity(like);
    }
}
