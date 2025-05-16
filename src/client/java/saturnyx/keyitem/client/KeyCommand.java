package saturnyx.keyitem.client;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.awt.*;

import static net.minecraft.server.command.CommandManager.literal;

public class KeyCommand {
    public static LiteralArgumentBuilder<ServerCommandSource> register() {
        // Define the main command "ki"
        return literal("ki")
                // Define the execution logic for the main command
                .executes(context -> {
                    ServerCommandSource source = context.getSource();
                    source.sendFeedback(() -> Text.literal("Hello from KeyItem!!!"), false);
                    return 1;
                })
                // Add a subcommand "sub"
                .then(literal("reload")
                        // Define execution logic for the subcommand
                        .executes(context -> {
                            KeyitemClient.getInstance().keyConfig.loadKeymap();
                            context.getSource().sendFeedback(() -> Text.literal("Loaded Keybinds from File"), false);
                            return 1;
                        })

                )
                .then(literal("save")
                        .executes(context -> {
                            KeyitemClient.getInstance().keyConfig.saveKeymap();
                            context.getSource().sendFeedback(() -> Text.literal("Current Keybinds saved to File"),
                                    false);
                            return 1;
                        })
                )
                .then(literal("clear")
                        .executes(context -> {
                            KeyitemClient.getInstance().keyConfig.Keymap.clear();
                            KeyitemClient.getInstance().keyConfig.saveKeymap();
                            context.getSource().sendFeedback(
                                    () -> Text.literal("Cleared all keybinds. Default Keybinds put in place"), false);
                            return 1;
                        })
                )
                .then(literal("open")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.literal("Opening File"), false);
                            try {
                                net.fabricmc.loader.api.FabricLoader.getInstance().getGameDir()
                                        .resolve("config")
                                        .resolve(KeyConfig.CONFIG_PATH.getFileName())
                                        .toFile()
                                        .createNewFile();
                                Desktop.getDesktop()
                                        .open(net.fabricmc.loader.api.FabricLoader.getInstance().getGameDir()
                                                .resolve("config")
                                                .resolve(KeyConfig.CONFIG_PATH.getFileName())
                                                .toFile());
                            } catch (Exception e) {
                                context.getSource()
                                        .sendFeedback(() -> Text.literal("Failed to open file: " + e.getMessage()),
                                                false);
                            }
                            return 1;
                        })
                );
    }
}