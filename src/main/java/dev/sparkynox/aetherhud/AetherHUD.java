package dev.sparkynox.aetherhud;

import dev.sparkynox.aetherhud.config.HudConfig;
import dev.sparkynox.aetherhud.editor.HudEditorScreen;
import dev.sparkynox.aetherhud.hud.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class AetherHUD implements ClientModInitializer {

    public static final String MOD_ID = "aetherhud";

    // H key opens the drag editor
    public static KeyBinding editorKey;

    @Override
    public void onInitializeClient() {
        HudRenderer.init();
        HudConfig.load(); // load saved positions on top of defaults

        editorKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.aetherhud.editor",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "AetherHUD"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (editorKey.wasPressed() && client.currentScreen == null) {
                client.setScreen(new HudEditorScreen());
            }
        });
    }
}
