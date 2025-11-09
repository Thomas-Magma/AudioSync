package wtf.magma.audiosync.util;

import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.system.MemoryUtil;
import wtf.magma.audiosync.util.nanovg.NanoUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;
import static wtf.magma.audiosync.util.nanovg.NanoVGHelper.context;

/**
 * Base64 图片解码和渲染工具
 */
public class Base64ImageRenderer {
    private static final Map<String, Integer> base64ImageCache = new HashMap<>();

    /**
     * 从 Base64 字符串创建 NanoVG 图像
     * @param base64Data Base64 编码的图片数据
     * @param cacheKey 缓存键，用于避免重复创建
     * @return NanoVG 图像ID，-1表示失败
     */
    public static int createImageFromBase64(String base64Data, String cacheKey) {
        if (base64Data == null || base64Data.isEmpty() || "No Media".equals(base64Data)) {
            return -1;
        }

        // 检查缓存
        if (cacheKey != null && base64ImageCache.containsKey(cacheKey)) {
            return base64ImageCache.get(cacheKey);
        } else {
            clearAllCache();
        }

        try {
            // 清理 Base64 数据（移除 data:image 前缀等）
            String cleanBase64 = cleanBase64Data(base64Data);
            if (cleanBase64 == null) {
                return -1;
            }
            
            // 解码 Base64
            byte[] imageData = Base64.getDecoder().decode(cleanBase64);
            
            // 创建 ByteBuffer
            ByteBuffer buffer = MemoryUtil.memAlloc(imageData.length);
            buffer.put(imageData);
            buffer.flip();
            
            // 创建 NanoVG 图像
            int imageId = nvgCreateImageMem(context, 0, buffer);
            
            // 释放内存
            MemoryUtil.memFree(buffer);
            
            if (imageId != -1 && cacheKey != null) {
                base64ImageCache.put(cacheKey, imageId);
            }
            
            return imageId;
            
        } catch (Exception e) {
            System.err.println("Base64 图像解码失败: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * 从 Base64 字符串创建 NanoVG 图像（无缓存）
     */
    public static int createImageFromBase64(String base64Data) {
        return createImageFromBase64(base64Data, null);
    }
    
    /**
     * 清理 Base64 数据，移除前缀
     */
    private static String cleanBase64Data(String base64Data) {
        if (base64Data.startsWith("data:image")) {
            int commaIndex = base64Data.indexOf(',');
            if (commaIndex != -1) {
                return base64Data.substring(commaIndex + 1);
            }
        }
        return base64Data;
    }
    
    /**
     * 渲染 Base64 图像到指定矩形区域
     */
    public static void drawBase64Image(String base64Data, String cacheKey, 
                                      float x, float y, float width, float height) {
        int imageId = createImageFromBase64(base64Data, cacheKey);
        if (imageId != -1) {
            NanoUtil.drawImageRect(imageId, x, y, width, height);
        }
    }


    /**
     * 渲染图像到指定圆角矩形区域
     */
    public static void drawBase64RoundRectImage(String base64Data, String cacheKey,
                                       float x, float y, float width, float height,float radius) {
        int imageId = createImageFromBase64(base64Data, cacheKey);
        if (imageId != -1) {
            NanoUtil.drawImageRound(imageId, x, y, width, height,radius);
        }
    }

    /**
     * 清除缓存中的图像
     */
    public static void clearCache(String cacheKey) {
        if (cacheKey != null && base64ImageCache.containsKey(cacheKey)) {
            int imageId = base64ImageCache.get(cacheKey);
            nvgDeleteImage(context, imageId);
            base64ImageCache.remove(cacheKey);
        }
    }
    
    /**
     * 清除所有缓存的图像
     */
    public static void clearAllCache() {
        for (int imageId : base64ImageCache.values()) {
            nvgDeleteImage(context, imageId);
        }
        base64ImageCache.clear();
    }
    
    /**
     * 获取 Base64 图像的尺寸
     */
    public static ImageSize getImageSize(String base64Data) {
        if (base64Data == null || base64Data.isEmpty() || "No Media".equals(base64Data)) {
            return new ImageSize(0, 0);
        }
        
        try {
            String cleanBase64 = cleanBase64Data(base64Data);
            if (cleanBase64 == null) {
                return new ImageSize(0, 0);
            }
            
            byte[] imageData = Base64.getDecoder().decode(cleanBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(bis);
            bis.close();
            
            if (image != null) {
                return new ImageSize(image.getWidth(), image.getHeight());
            }
        } catch (Exception e) {
            System.err.println("获取图像尺寸失败: " + e.getMessage());
        }
        
        return new ImageSize(0, 0);
    }
    
    /**
     * 图像尺寸类
     */
    public static class ImageSize {
        public final int width;
        public final int height;
        
        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
        
        public float getAspectRatio() {
            return height > 0 ? (float) width / height : 1.0f;
        }
    }
}