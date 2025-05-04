package saturnyx.keyitem.mixin.client;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import saturnyx.keyitem.client.KeyitemClient;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action == 1 || action == 2) { // PRESS or REPEAT
            // Get the KeyitemClient instance
            KeyitemClient client = KeyitemClient.getInstance();
            
            // Check keybindings for all items
            for (String itemId : client.keyConfig.getAllKeymaps().keySet()) {
                if (client.keyDetect.isKeybindPressed(itemId, modifiers, key)) {
                    // Handle the keybind press for this item
                    client.keyDetect.giveItem(itemId);
                    System.out.println("Keybind pressed for: " + itemId);
                }
            }
        }
    }
}