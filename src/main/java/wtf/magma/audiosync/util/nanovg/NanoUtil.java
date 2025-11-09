package wtf.magma.audiosync.util.nanovg;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NVGPaint;
import org.lwjgl.system.MemoryUtil;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.nanovg.NanoVG.*;
import static wtf.magma.audiosync.util.nanovg.NanoVGHelper.context;

public class NanoUtil {
    public static int genImageId(InputStream inputStream) {
        byte[] data;
        try {
            data = inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ByteBuffer buffer = MemoryUtil.memAlloc(data.length);
        buffer.put(data).flip();

        return nvgCreateImageMem(context, 0, buffer);
    }

    public static void drawImageRect(int imageId, float x, float y, float width, float height) {
        NVGPaint imgPaint = NVGPaint.calloc();
        nvgBeginPath(context);
        nvgRect(context, x, y, width, height);
        nvgImagePattern(context, x, y, width, height, 0, imageId, 1.0f, imgPaint);
        nvgFillPaint(context, imgPaint);
        nvgFill(context);
        imgPaint.free();
    }
    public static void drawImageRect(int imageId, float x, float y, float width, float height, Color color) {
        NVGPaint imgPaint = NVGPaint.calloc();
        nvgBeginPath(context);
        nvgRect(context, x, y, width, height);
        nvgImagePattern(context, x, y, width, height, 0, imageId, 1.0f, imgPaint);
        fillColor(color);
        nvgFillPaint(context, imgPaint);
        nvgFill(context);
        imgPaint.free();
    }

    public static void fillColor(Color color) {
        NVGColor nvgColor = NVGColor.calloc();
        nvgColor.r(color.getRed() / 255f).g(color.getGreen() / 255f).b(color.getBlue() / 255f).a(color.getAlpha() / 255f);
        nvgFillColor(context, nvgColor);
        nvgColor.free();
    }

    public static void drawImageCircle(int imageId, float x, float y, float radius) {
        drawImageCircle(imageId, x, y, radius, 0f);
    }

    public static void drawImageCircle(int imageId, float x, float y, float radius, float angle) {
        NVGPaint imgPaint = NVGPaint.calloc();

        nvgBeginPath(context);  // 开始一个新的路径
        nvgCircle(context, x, y, radius);  // 定义一个圆形区域用于绘制图像
        nvgImagePattern(context, x - radius, y - radius, radius * 2f, radius * 2f, angle, imageId, 1.0f, imgPaint);
        nvgFillPaint(context, imgPaint);  // 设置填充样式为图像
        nvgFill(context);  // 填充图像

        imgPaint.free();
    }

    public static void drawImageRound(int imageId, float x, float y, float width, float height, float radius) {
        NVGPaint imgPaint = NVGPaint.calloc();

        nvgBeginPath(context);
        nvgRoundedRect(context, x, y, width, height, radius);

        nvgImagePattern(context, x, y, width, height, 0f, imageId, 1.0f, imgPaint);
        nvgFillPaint(context, imgPaint);
        nvgFill(context);

        imgPaint.free();
    }
}
