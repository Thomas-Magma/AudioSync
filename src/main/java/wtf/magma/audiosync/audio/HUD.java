package wtf.magma.audiosync;

import net.minecraft.client.MinecraftClient;
import wtf.magma.audiosync.audio.SMTC;
import wtf.magma.audiosync.event.annotations.EventTarget;
import wtf.magma.audiosync.event.events.NanoEvent;
import wtf.magma.audiosync.util.Base64ImageRenderer;
import wtf.magma.audiosync.util.nanovg.NanoVGHelper;

public class HUD {

    public HUD(){
        AudioSync.Instance.getEventManager().register(this);
    }

    @EventTarget
    public void on2d(NanoEvent event){
        Base64ImageRenderer.drawBase64Image(SMTC.getBase64(),String.valueOf(SMTC.getBase64().length()),10, MinecraftClient.getInstance().getWindow().getScaledHeight() - 120,50,50);
    }
}
