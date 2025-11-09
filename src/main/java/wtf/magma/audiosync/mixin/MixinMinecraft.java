package wtf.magma.audiosync.mixin;

import dsj.smtc.SmtcLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wtf.magma.audiosync.AudioSync;
import wtf.magma.audiosync.audio.SMTC;
import wtf.magma.audiosync.event.events.NanoEvent;
import wtf.magma.audiosync.event.events.TickEvent;
import wtf.magma.audiosync.util.gl.GlState;
import wtf.magma.audiosync.util.nanovg.NanoVGHelper;

@Mixin(MinecraftClient.class)
public class MixinMinecraft {

    @Inject(method = "render",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;swapBuffers(Lnet/minecraft/client/util/tracy/TracyFrameCapturer;)V"))
    public void render(boolean bl, CallbackInfo ci){
        GlState.backup();
        NanoVGHelper.beginFrame();
        AudioSync.Instance.getEventManager().call(new NanoEvent());
        NanoVGHelper.endFrame();
        GlState.restore();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    void preTickHook(CallbackInfo ci) {
        AudioSync.Instance.getEventManager().call(new TickEvent());
    }

}
