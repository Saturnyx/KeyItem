package saturnyx.keyitem.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class KeyDetect {
    private final KeyConfig keyConfig;

    public KeyDetect(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    public boolean isKeybindPressed(String command, int currentModifier, int currentKey) {
        KeyConfig.ItemKeyBind keyBind = keyConfig.getKeymap(command);
        if (keyBind == null)
            return false;

        // Check if the primary key matches
        if (keyBind.getPrimaryKey() != currentKey)
            return false;

        // If there's a modifier key, check if it's pressed by checking if its key code is in the list of pressed keys
        int modifierKey = keyBind.getModifierKey();
        if (modifierKey != 0) {
            // Check if the modifier key is pressed
            // For Alt key (342), check if Alt is pressed
            if (modifierKey == 342) {
                return (currentModifier & 0x4) != 0; // 0x4 is GLFW_MOD_ALT
            }
            // For Ctrl key (341), check if Ctrl is pressed
            else if (modifierKey == 341) {
                return (currentModifier & 0x2) != 0; // 0x2 is GLFW_MOD_CONTROL
            }
            // For Shift key (340), check if Shift is pressed
            else if (modifierKey == 340) {
                return (currentModifier & 0x1) != 0; // 0x1 is GLFW_MOD_SHIFT
            }
            // For other modifier keys, assume they're not pressed
            return false;
        }

        // If there's no modifier key, just check if the primary key is pressed
        return true;
    }

    // New method that uses key codes instead of modifier flags
    public boolean isKeybindPressed(String command, java.util.Set<Integer> pressedKeys, int currentKey) {
        KeyConfig.ItemKeyBind keyBind = keyConfig.getKeymap(command);
        if (keyBind == null)
            return false;

        // Check if the primary key matches
        if (keyBind.getPrimaryKey() != currentKey)
            return false;

        // If there's a modifier key, check if it's pressed by checking if its key code is in the set of pressed keys
        int modifierKey = keyBind.getModifierKey();
        if (modifierKey != 0) {
            // Check if the modifier key is pressed
            return pressedKeys.contains(modifierKey);
        }

        return true;
    }

    public void Command(String command){
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player != null) {
            player.networkHandler.sendChatCommand(command);
        }
    }
}
