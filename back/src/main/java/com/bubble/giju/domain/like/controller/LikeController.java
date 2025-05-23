package com.bubble.giju.domain.like.controller;

import com.bubble.giju.domain.like.service.LikeService;
import com.bubble.giju.domain.user.dto.CustomPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "찜 API", description = "주류 찜 API 입니다.")
@RequiredArgsConstructor
@RestController
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "찜하기", description = "회원이 빈하트를 누르면 찜하기 됩니다.")
    @PostMapping("/products/{drinkId}/wishlist")
    public void addLike(@AuthenticationPrincipal CustomPrincipal customPrincipal, @PathVariable Long drinkId) {
        likeService.toggleLike(customPrincipal.getUserId(), drinkId, true);
    }

    @Operation(summary = "찜하기", description = "회원이 채워진하트를 누르면 찜하기 취소 됩니다.")
    @DeleteMapping("/products/{drinkId}/wishlist")
    public void deleteLike(@AuthenticationPrincipal CustomPrincipal customPrincipal, @PathVariable Long drinkId) {
        likeService.toggleLike(customPrincipal.getUserId(), drinkId, false);
    }
}
