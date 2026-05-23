package dev.sparkynox.aetherhud;

import dev.sparkynox.aetherhud.config.HudConfig;
import dev.sparkynox.aetherhud.editor.HudEditorScreen;
import dev.sparkynox.aetherhud.hud.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class AetherHUD implements ClientModInitializer {

    public static final String MOD_ID = "aetherhud";

    public static KeyBinding editorKey;

    @Override
    public void onInitializeClient() {
        HudRenderer.init();
        HudConfig.load();

        // Register our textures as a built-in resource pack with ALWAYS_ENABLED.
        // This makes our gui textures override:
        //   1. Vanilla default textures
        //   2. ANY user-installed resource/texture pack
        // Because built-in mod packs load at highest priority in the pack stack.
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "aetherhud-textures"), // must match folder name in /resourcepacks/
                container,
                Text.literal("AetherHUD Textures"),
                ResourcePackActivationType.ALWAYS_ENABLED    // force-enabled, user can't disable
            );
        });

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
