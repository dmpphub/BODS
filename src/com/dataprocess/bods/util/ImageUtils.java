package com.dataprocess.bods.util;

/*
 * Copyright(c) 2013 Chain-Sys Corporation Inc.
 * Duplication or distribution of this code in part or in whole by any media
 * without the express written permission of Chain-Sys Corporation or its agents is
 * strictly prohibited.
 *
 * REVISION         DATE            NAME     DESCRIPTION
 * 511.101       Oct 12, 2013       MDR      Initial Code   
 */

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.net.util.Base64;

import sun.misc.BASE64Encoder;

/**
 * The Class ImageUtils.
 */
public final class ImageUtils {

    /**
     * Resize image.
     *
     * @param filePath the file path
     * @param width the width
     * @param height the height
     * @return the buffered image
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static BufferedImage resizeImage(String filePath, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(filePath));
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    /**
     * Resize image with hint.
     *
     * @param filePath the file path
     * @param width the width
     * @param height the height
     * @return the buffered image
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static BufferedImage resizeImageWithHint(String filePath, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(filePath));
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

    /**
     * Resize image with hint.
     *
     * @param imgData the img data
     * @param width the width
     * @param height the height
     * @return the buffered image
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static BufferedImage resizeImageWithHint(byte[] imgData, int width, int height) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imgData);
        BufferedImage originalImage = ImageIO.read(bais);
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }

    /**
     * Gets the image as base64.
     *
     * @param imageFile the image file
     * @return the image as base64
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String getImageAsBase64(String imageFile) throws IOException {
        return getImageAsBase64(ImageIO.read(new File(imageFile)));
    }

    /**
     * Gets the image as base64.
     *
     * @param imageFile the image file
     * @return the image as base64
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String getImageAsBase64(File imageFile) throws IOException {
        return getImageAsBase64(ImageIO.read(imageFile));
    }

    /**
     * Gets the image as base64.
     *
     * @param bufferedImage the buffered image
     * @return the image as base64
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String getImageAsBase64(BufferedImage bufferedImage) throws IOException {
        String base64 = "";
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            base64 = Base64.encodeBase64String(imageInByte);
        } catch (IOException exe) {
            throw exe;
        }
        return base64;
    }

    /**
     * Gets the image as base64.
     *
     * @param imgData the img data
     * @param width the width
     * @param height the height
     * @return the image as base64
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String getImageAsBase64(byte[] imgData, int width, int height) throws IOException {
        String base64 = "";
        BufferedImage resizeImageJpg = resizeImageWithHint(imgData, width, height);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(resizeImageJpg, "jpg", baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            base64 = Base64.encodeBase64String(imageInByte);
        } catch (IOException exe) {
            throw exe;
        }
        return base64;
    }

    public static String getImageAsStringBase64Encoder(BufferedImage bufferedImage) throws IOException {
        String base64 = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            base64 = encoder.encode(imageBytes);
        } catch (IOException e) {
            throw e;
        } finally {
            if (baos != null)
                baos.close();
        }
        return base64;
    }
}
