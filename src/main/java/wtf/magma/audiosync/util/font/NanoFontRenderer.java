package wtf.magma.audiosync.util.font;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.system.MemoryUtil;
import wtf.magma.audiosync.util.ResourceUtil;
import wtf.magma.audiosync.util.nanovg.NanoUtil;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.nanovg.NanoVG.*;
import static wtf.magma.audiosync.util.nanovg.NanoVGHelper.context;


public class NanoFontRenderer {
    @Getter
    private final String name;
    private int font;
    @Setter
    private float size;

    public NanoFontRenderer(String name, String fileName) {
        this.name = name;
        try (InputStream inputStream = ResourceUtil.getResourceAsStream(fileName + ".ttf")) {
            byte[] data = inputStream.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc(data.length);
            buffer.put(data).flip();

            font = nvgCreateFontMem(context, name, buffer, false);
            size = 16;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public float getHeight(float size) {
        nvgFontFaceId(context, font);
        nvgFontSize(context, size);
        float[] bounds = new float[4];
        nvgTextBounds(context, 0, 0, "AaBbCcDdEeFfGgJjYy", bounds);
        return (bounds[3] - bounds[1]) / 2f;
    }

    public float getHeight(String text, float size) {
        if (text == null) return 0f;
        nvgFontFaceId(context, font);
        nvgFontSize(context, size);
        float[] bounds = new float[4];
        nvgTextBounds(context, 0, 0, text, bounds);
        return (bounds[3] - bounds[1]) / 2f;
    }

    public void drawString(String text, float x, float y, int align, Color color) {
        renderPlainString(text, x, y, size, align, color);
    }

    public void drawString(String text, float x, float y, int align, Color color, boolean shadow) {
        drawString(text, x, y, size, align, color, shadow);
    }

    public void drawString(String text, float x, float y, float size, int align, Color color, boolean shadow) {
        if (shadow) {
            renderPlainString(text, x + 0.5f, y + 0.5f, size, align, new Color(20, 20, 20, color.getAlpha()));
        }

        renderPlainString(text, x, y, size, align, color);
    }

    public void drawString(String text, float x, float y, float size, Color color, boolean shadow) {
        int rgb = color.getRGB();

        if (shadow) {
            rgb = (rgb & 16579836) >> 2 | rgb & -16777216;
        }

        if (shadow) {
            renderPlainString(text, x + 0.5f, y + 0.5f, size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, new Color(rgb));
        }
        renderPlainString(text, x, y, size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, color);
    }

    public void drawString(String text, float x, float y, float size, Color color) {
        renderPlainString(text, x, y, size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, color);
    }

    public void drawCenteredString(final String text, final float x, final float y, final Color color) {
        int halfWidth = Math.round(getStringWidth(text) / 2);
        int centeredX = Math.round(x - halfWidth);
        drawString(text, centeredX, y, color);
    }

    public void drawCenteredString(final String text, final float x, final float y,int size, final Color color) {
        drawString(text, (x - getStringWidth(text) / 2f), y, size, color);
    }

    public void drawString(String text, float x, float y, Color color) {
        renderPlainString(text, x, y, size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, color);
    }

    public void drawString(String text, float x, float y, Color col, boolean shadow) {
        drawString(text, x, y, size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, col, shadow);
    }

    // Glow
    public void drawGlowString(String text, float x, float y, Color color) {
        renderGlowString(text, x, y, size, 5f, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, color, color);
    }

    public void drawGlowString(String text, float x, float y, Color color, boolean shadow) {
        int rgb = color.getRGB();

        if (shadow) {
            rgb = (rgb & 16579836) >> 2 | rgb & -16777216;
        }
        renderPlainString(text, x + 0.5f, y + 0.5f, size, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, new Color(rgb));
        drawGlowString(text, x, y, color);
    }

    public void drawGlowString(String text, float x, float y, float radius,float size, Color color) {
        renderGlowString(text, x, y, size, radius, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, color, color);
    }

    public void drawGlowString(String text, float x, float y, float size, Color color) {
        renderGlowString(text, x, y, size, 5f, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, color, color);
    }

    public void drawGlowString(String text, float x, float y, float size, int align, Color color) {
        renderGlowString(text, x, y, size, 5f, align, color, color);
    }

    public void drawGlowString(String text, float x, float y, float size, float radius, int align, Color color) {
        renderGlowString(text, x, y, size, radius, align, color, color);
    }

    public void drawGlowString(String text, float x, float y, float size, int align, Color textColor, Color glowColor) {
        renderGlowString(text, x, y, size, 5f, align, textColor, glowColor);
    }

    public void drawGlowString(String text, float x, float y, float size, Color textColor, Color glowColor) {
        renderGlowString(text, x, y, size, 5f, NVG_ALIGN_LEFT | NVG_ALIGN_TOP, textColor, glowColor);
    }

    private void renderPlainString(String text, float x, float y, float size, int align, Color color) {
        nvgBeginPath(context);
        nvgTextAlign(context, align);

        NanoUtil.fillColor(color);

        renderString(text, x, y + 1f, size);

        nvgClosePath(context);
    }

    private void renderGlowString(String text, float x, float y, float size, float radius, int align, Color textColor, Color glowColor) {
        nvgBeginPath(context);
        nvgTextAlign(context, align);

        NanoUtil.fillColor(glowColor);
        nvgFontBlur(context, radius);
        renderString(text, x, y + 1f, size);

        NanoUtil.fillColor(textColor);
        nvgFontBlur(context, 0f);
        renderString(text, x, y + 1f, size);

        nvgClosePath(context);
    }


    private void renderString(String text, float x, float y, float size) {
        nvgFontFaceId(context, font);
        nvgFontSize(context, size / 2f);
        nvgText(context, x, y, text);
    }

    public float getStringWidth(String text) {
        return getStringWidth(text, size);
    }

    public float getStringWidth(String text, float size) {
        if (text == null) return 0f;
        nvgFontFaceId(context, font);
        nvgFontSize(context, size);
        float[] bounds = new float[4];
        nvgTextBounds(context, 0, 0, text, bounds);
        return (bounds[2] - bounds[0]) / 2f;
    }

    public String trimStringToWidth(String text, float width, float size) {
        return this.trimStringToWidth(text, width, size, false, false);
    }

    public String trimStringToWidth(String text, float width) {
        return this.trimStringToWidth(text, width, size, false, false);
    }

    public String trimStringToWidth(String text, float width, boolean reverse) {
        return this.trimStringToWidth(text, width, size, reverse, false);
    }

    public String trimStringToWidth(String text, float width, float size, boolean reverse, boolean more) {
        if (text == null) {
            return "";
        } else {
            StringBuilder builder = new StringBuilder();
            int startIndex = reverse ? text.length() - 1 : 0;
            int step = reverse ? -1 : 1;

            String nextChar = "";
            for (int i = startIndex; i <= text.length() - 1 && i >= 0 && getStringWidth(builder + nextChar, size) <= width; i += step) {
                builder.append(text.charAt(i));
                nextChar = reverse ? (i == 0 ? "" : String.valueOf(text.charAt(i + step))) : (i == text.length() - 1 ? "" : String.valueOf(text.charAt(i + step)));
            }

            if (reverse) builder.reverse();
            String result = builder.toString();
            if (more && !text.equals(result)) {
                result = reverse ? "..." + result : result + "...";
            }
            return result;
        }
    }
}