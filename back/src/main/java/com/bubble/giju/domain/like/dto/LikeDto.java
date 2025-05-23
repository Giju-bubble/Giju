package com.bubble.giju.domain.like.dto;

import com.bubble.giju.domain.like.entity.Like;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

public class LikeDto {

    @AllArgsConstructor
    @Getter
    public static class Response {
        String userId;
        Long drinkId;
        LocalDateTime createdAt;

        public static Response fromEntity(Like like) {
            return new Response(
                    like.getUser().getUserId().toString(),
                    like.getDrink().getId(),
                    like.getCreatedAt()
            );
        }
    }
}
