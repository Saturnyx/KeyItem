# KEYITEM

A minecraft mod that allows you to map keybinds to slash commands, allowing you to easily run commands.

## Editing Keymap

Go to your `.minecraft/config` folder, and edit the `keymaps.json` file. You can see that each keybind is in the
following format:

```json
"Slash_Command": {
    "modifierKey": 342,
    "primaryKey": 72
},
```

- `Slash_Command` can be any command without the `/` symbol. For example, `give @s minecraft:ender_pearl`
- `modifierKey` can be `alt`, `ctrl` or any other key, remember that it will only accept [keycode](keycodes.md).
- `primaryKey` is the letter [keycode](keycodes.md) that is part of the alphabet.

```json
"give @s minecraft:ender_pearl": {
    "modifierKey": 342,
    "primaryKey": 85
},
```