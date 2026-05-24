package dev.sparkynox.aetherhud.config;

import com.google.gson.*;
import dev.sparkynox.aetherhud.hud.HudModule;
import dev.sparkynox.aetherhud.hud.HudRenderer;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class HudConfig {

    private static final Path FILE = FabricLoader.getInstance()
        .getConfigDir().resolve("aetherhud.json");

    // bump this whenever default positions change significantly
    // if saved version != current, positions reset to defaults
    private static final int CONFIG_VERSION = 2;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save() {
        JsonObject root = new JsonObject();
        root.addProperty("version", CONFIG_VERSION);

        for (HudModule mod : HudRenderer.modules) {
            JsonObject entry = new JsonObject();
            entry.addProperty("x",       mod.x);
            entry.addProperty("y",       mod.y);
            entry.addProperty("enabled", mod.enabled);
            entry.addProperty("scale",   mod.scale);
            root.add(mod.id, entry);
        }
        try (Writer w = new FileWriter(FILE.toFile())) {
            GSON.toJson(root, w);
        } catch (IOException e) {
            System.err.println("[AetherHUD] Failed to save config: " + e.getMessage());
        }
    }

    public static void load() {
        File file = FILE.toFile();
        if (!file.exists()) return;

        try (Reader r = new FileReader(file)) {
            JsonObject root = GSON.fromJson(r, JsonObject.class);
            if (root == null) return;

            // version mismatch = skip loading positions, keep defaults
            if (!root.has("version") || root.get("version").getAsInt() != CONFIG_VERSION) {
                System.out.println("[AetherHUD] Config version changed, resetting to defaults.");
                file.delete(); // wipe old config so next save is clean
                return;
            }

            for (HudModule mod : HudRenderer.modules) {
                if (!root.has(mod.id)) continue;
                JsonObject e = root.getAsJsonObject(mod.id);
                mod.x       = mod.targetX = e.get("x").getAsFloat();
                mod.y       = mod.targetY = e.get("y").getAsFloat();
                mod.enabled = e.get("enabled").getAsBoolean();
                mod.scale   = e.get("scale").getAsFloat();
            }
        } catch (IOException e) {
            System.err.println("[AetherHUD] Failed to load config: " + e.getMessage());
        }
    }
}
