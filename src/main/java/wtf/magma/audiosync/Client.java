package wtf.magma.audiosync;

import net.fabricmc.api.ClientModInitializer;
import wtf.magma.audiosync.config.ModClientConfig;

public final class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModClientConfig.INSTANCE.load();
    }
 
    public static ModClientConfig getConfig() {
        return ModClientConfig.INSTANCE.instance();
    }
}