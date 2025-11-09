package wtf.magma.audiosync.util.nanovg;

import net.minecraft.client.MinecraftClient;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import static org.lwjgl.nanovg.NanoVG.*;

public class NanoVGHelper {
    public static long context;

    public static void create() {
        context = NanoVGGL3.nvgCreate(NanoVGGL3.NVG_ANTIALIAS | NanoVGGL3.NVG_STENCIL_STROKES);
        NanoVG.nvgShapeAntiAlias(context, true);
        System.out.println("Successfully created NanoVG context");
    }

    public static void beginFrame() {
        nvgBeginFrame(context, MinecraftClient.getInstance().getWindow().getWidth(), MinecraftClient.getInstance().getWindow().getHeight(), 1f);
        scaleStart(0,  0, (float) MinecraftClient.getInstance().getWindow().getScaleFactor());
    }

    public static void endFrame() {
        nvgEndFrame(context);
    }

    public static void scaleStart(float centerX, float centerY, float scale) {
        nvgSave(context);
        nvgTranslate(context, centerX, centerY);
        nvgScale(context, scale, scale);
        nvgTranslate(context, -centerX, -centerY);
    }
}
