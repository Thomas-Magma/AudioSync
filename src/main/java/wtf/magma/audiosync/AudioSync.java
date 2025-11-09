package wtf.magma.audiosync;

import com.mojang.logging.LogUtils;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import wtf.magma.audiosync.audio.HUD;
import wtf.magma.audiosync.audio.SMTC;
import wtf.magma.audiosync.event.EventManager;

@Getter
public class AudioSync implements ModInitializer {
    public static AudioSync Instance = new AudioSync();
    private EventManager eventManager = new EventManager();
    private static final Logger LOGGER = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        if (!(Util.getOperatingSystem() == Util.OperatingSystem.WINDOWS)) {
            LOGGER.error("Audio Sync can't running on {}", Util.getOperatingSystem());
            return;
        }

        SMTC.thread();
        new HUD();
    }

}
