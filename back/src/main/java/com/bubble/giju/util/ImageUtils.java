package com.bubble.giju.util;

import com.bubble.giju.global.config.CustomException;
import com.bubble.giju.global.config.ErrorCode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static File resize(File originalFile, int targetWidth, int targetHeight) throws IOException {
        String fileName=originalFile.getName();
        String format = getFileExtention(fileName);

        if(format==null || format.isBlank())
        {
            throw new CustomException(ErrorCode.INVALID_IMAGE_FORMAT);
        }

        BufferedImage originalImage = ImageIO.read(originalFile);

        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // 3. 파일 생성
        File outputFile = File.createTempFile(fileName, "." + format);
        ImageIO.write(resizedImage, format, outputFile);

        return outputFile;
    }

    private static String getFileExtention(String fileName) {
        int dotIndex=fileName.lastIndexOf(".");
        if(dotIndex >=0 && dotIndex<fileName.length()-1)
        {
            return fileName.substring(dotIndex+1).toLowerCase();
        }
        return null;
    }
}
