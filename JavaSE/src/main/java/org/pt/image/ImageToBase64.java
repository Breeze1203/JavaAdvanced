package org.pt.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageToBase64 {
    public static String generateImageToBase64(int width, int height) {
        String base64Image = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Hello, World!", 10, 20);
            g2d.dispose();

            ImageIO.write(image, "png", bos);
            byte[] imageBytes = bos.toByteArray();

            base64Image = Base64.getEncoder().encodeToString(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return base64Image;
    }

    public static void main(String[] args) {
        // 生成一个200x100的图片并转换为Base64编码
        String base64Image = generateImageToBase64(200, 100);
        System.out.println(base64Image);
    }
}
