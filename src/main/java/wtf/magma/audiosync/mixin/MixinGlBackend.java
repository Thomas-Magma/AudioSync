package wtf.magma.audiosync.mixin;

import net.minecraft.client.gl.GlBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.magma.audiosync.util.font.NanoFontLoader;
import wtf.magma.audiosync.util.nanovg.NanoVGHelper;

@Mixin(GlBackend.class)
public class MixinGlBackend {
    @Inject(method = "<init>",at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        NanoVGHelper.create();
        NanoFontLoader.registerFonts();
    }

}
