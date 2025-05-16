package saturnyx.keyitem.client;

import net.fabricmc.api.ClientModInitializer;

public class KeyitemClient implements ClientModInitializer {

    // Static instance for global access
    private static KeyitemClient instance;

    // to be changed
    public final KeyConfig keyConfig = new KeyConfig();
    public final KeyDetect keyDetect = new KeyDetect(keyConfig);

    @Override
    public void onInitializeClient() {
        // Set the instance when initialized
        instance = this;
        keyConfig.loadKeymap();
        net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(KeyCommand.register());
        });
        System.out.println("KeyItem Client Initialized!");
    }

    // Static method to access the instance
    public static KeyitemClient getInstance() {
        return instance;
    }
}
