package com.bubble.giju.util;

import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ImageUtilsTest {


    @Test
    void 정상적으로_이미지가_리사이즈되는지_확인() throws IOException {
        // given
        File inputFile = new File("src/test/resources/sample.png"); // 테스트용 이미지 파일
        int targetWidth = 100;
        int targetHeight = 100;

        // when
        File resizedFile = ImageUtils.resize(inputFile, targetWidth, targetHeight);

        // then
        assertThat(resizedFile).exists();
        BufferedImage resizedImage = ImageIO.read(resizedFile);
        assertThat(resizedImage.getWidth()).isEqualTo(targetWidth);
        assertThat(resizedImage.getHeight()).isEqualTo(targetHeight);
    }

    @Test
    void 잘못된_확장자일_경우_예외를_던지는지_확인() {
        // given
        File invalidFile = new File("src/test/resources/sample");

        // when & then
        assertThatThrownBy(() -> ImageUtils.resize(invalidFile, 100, 100))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining(ErrorCode.INVALID_IMAGE_FORMAT.getMsg());
    }
}