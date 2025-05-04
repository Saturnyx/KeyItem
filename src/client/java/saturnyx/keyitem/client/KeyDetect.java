package saturnyx.keyitem.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class KeyDetect {
    private final KeyConfig keyConfig;

    public KeyDetect(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    public boolean isKeybindPressed(String itemId, int currentModifier, int currentKey) {
        KeyConfig.ItemKeyBind keyBind = keyConfig.getKeymap(itemId);
        if (keyBind == null)
            return false;

        // Check if the primary key matches
        if (keyBind.getPrimaryKey() != currentKey)
            return false;

        // For Alt key (342), check if a GLFW_MOD_ALT flag is set
        if (keyBind.getModifierKey() == 342) {
            return (currentModifier & 0x4) != 0; // 0x4 is GLFW_MOD_ALT
        }

        // Handle other modifier keys similarly

        return false;
    }
    
    public void giveItem(String itemId) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        if (player == null)
            return;

        ItemStack stack = new ItemStack(Registries.ITEM.get(Identifier.tryParse(itemId)));
        player.getInventory().setStack(player.getInventory().getSelectedSlot(), stack);
    }
}