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
        // Resource pack registration must happen before HudRenderer.init()
        // Fabric looks for the pack at: assets/<modid>/resourcepacks/<pack-name>/
        // So our folder is:            assets/aetherhud/resourcepacks/aetherhud-textures/
        // The Identifier path segment ("aetherhud-textures") must match the folder name exactly.
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container ->
            ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "aetherhud-textures"),
                container,
                Text.literal("AetherHUD Textures"),
                ResourcePackActivationType.ALWAYS_ENABLED
            )
        );

        HudRenderer.init();
        HudConfig.load();

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
