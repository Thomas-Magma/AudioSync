package wtf.magma.audiosync;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import wtf.magma.audiosync.audio.HUD;
import wtf.magma.audiosync.audio.SMTC;
import wtf.magma.audiosync.event.EventManager;

public class AudioSync implements ModInitializer {
    public static AudioSync Instance = new AudioSync();
    @Getter
    private EventManager eventManager = new EventManager();

    @Override
    public void onInitialize() {
        SMTC.thread();
        new HUD();
    }

}
