package saturnyx.keyitem.mixin.client;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import saturnyx.keyitem.client.KeyitemClient;

import java.util.HashSet;
import java.util.Set;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @org.spongepowered.asm.mixin.Unique
    private static final Set<Integer> pressedKeys = new HashSet<>();

    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKeyPress(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action == 1 || action == 2) { // PRESS or REPEAT
            pressedKeys.add(key);
            KeyitemClient client = KeyitemClient.getInstance();

            boolean keybindProcessed = false;
            for (String command : client.keyConfig.getAllKeymaps().keySet()) {
                if (!keybindProcessed && client.keyDetect.isKeybindPressed(command, pressedKeys, key)) {
                    client.keyDetect.Command(command);

                    keybindProcessed = true;
                }
            }
        } else if (action == 0) { // RELEASE
            pressedKeys.remove(key);
        }
    }
}
