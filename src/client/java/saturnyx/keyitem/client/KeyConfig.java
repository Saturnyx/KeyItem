package saturnyx.keyitem.client;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;

public class KeyConfig {
    private static final String CONFIG_FILENAME = "keymaps.json";
    private static final String DEFAULT_KEYMAP_RESOURCE = "/defaultKeymap.json";
    private static final com.google.gson.Gson GSON = new com.google.gson.GsonBuilder().setPrettyPrinting().create();
    public static final java.nio.file.Path CONFIG_PATH =
            net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILENAME);

    // Keybind datatype
    public static class ItemKeyBind {
        private int modifierKey;
        private int primaryKey;

        public ItemKeyBind(int modifierKey, int primaryKey) {
            this.modifierKey = modifierKey;
            this.primaryKey = primaryKey;
        }

        public int getModifierKey() {
            return modifierKey;
        }

        public int getPrimaryKey() {
            return primaryKey;
        }

        public void setModifierKey(int key) {
            this.modifierKey = key;
        }

        public void setPrimaryKey(int key) {
            this.primaryKey = key;
        }
    }

    // Main Keymap object
    public java.util.Map<String, ItemKeyBind> Keymap = new java.util.HashMap<>();

    // Updates `Keymap` value from Config File
    public void loadKeymap() {
        try {
            // Check if a config file exists
            if (!Files.exists(CONFIG_PATH)) {
                System.out.println("Config file does not exist!");
                Files.createDirectories(CONFIG_PATH.getParent());

                // Load the default keymap from resources
                InputStream defaultKeymapStream = KeyConfig.class.getResourceAsStream(DEFAULT_KEYMAP_RESOURCE);
                if (defaultKeymapStream == null) {
                    System.err.println("Default keymap resource not found: " + DEFAULT_KEYMAP_RESOURCE);
                    Files.writeString(CONFIG_PATH, "{}");
                    System.out.println("Keymap is null, initializing with empty map");
                } else {
                    // Read the default keymap and write to the config file
                    try (Reader reader = new InputStreamReader(defaultKeymapStream)) {
                        String defaultKeymap = GSON.toJson(GSON.fromJson(reader, Object.class));
                        Files.writeString(CONFIG_PATH, defaultKeymap);
                        System.out.println("Reset keymap to default");
                    } catch (IOException e) {
                        System.err.println("Error writing default keymap: " + e.getMessage());
                        Files.writeString(CONFIG_PATH, "{}");
                        System.out.println("Reset keymap to empty");
                    }
                }
            }

            // Read the keymap from a config file
            String keymapJson = Files.readString(CONFIG_PATH);
            Type keymapType = new TypeToken<java.util.Map<String, ItemKeyBind>>() {
            }.getType();
            Keymap = GSON.fromJson(keymapJson, keymapType);
            System.out.println("Loaded keymap");
            // If the keymap is null, initialize with an empty map
            if (Keymap == null) {
                Keymap = new java.util.HashMap<>();
                System.out.println("Keymap is null, initializing with empty map");
            }

        } catch (IOException e) {
            System.err.println("Error loading keymap: " + e.getMessage());
            // Initialize with an empty map on error
            Keymap = new java.util.HashMap<>();
            System.out.println("Keymap is null, initializing with empty map");
        }
    }

    // Save `Keymap` to Config File
    public void saveKeymap() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            String keymapJson = GSON.toJson(Keymap);
            Files.writeString(CONFIG_PATH, keymapJson);
            System.out.println("Saved keymap to config file");
        } catch (IOException e) {
            System.err.println("Error saving keymap: " + e.getMessage());
        }
    }

    // Add or update a keybind
    public void setKeymap(String command, int modifierKey, int primaryKey) {
        ItemKeyBind keyBind = new ItemKeyBind(modifierKey, primaryKey);
        Keymap.put(command, keyBind);
        saveKeymap();
    }

    // Remove a keybind
    public void removeKeymap(String command) {
        Keymap.remove(command);
        saveKeymap();
    }

    // Get a specific keybind
    public ItemKeyBind getKeymap(String command) {
        return Keymap.get(command);
    }

    // Get all keybinds
    public java.util.Map<String, ItemKeyBind> getAllKeymaps() {
        return new java.util.HashMap<>(Keymap);
    }

}
