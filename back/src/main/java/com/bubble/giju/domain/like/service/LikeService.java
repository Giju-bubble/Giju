package com.bubble.giju.domain.like.service;

public interface LikeService {
    void getLike(String userId);
    void toggleLike(String userId, Long drinkId, Boolean likeRequest);
}
