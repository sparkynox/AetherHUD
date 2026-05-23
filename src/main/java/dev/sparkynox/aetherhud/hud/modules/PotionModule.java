package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import java.util.ArrayList;
import java.util.List;

public class PotionModule extends HudModule {
    public PotionModule(float x, float y) { super("potions", x, y); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;
        List<StatusEffectInstance> fx = new ArrayList<>(client.player.getStatusEffects());
        if (fx.isEmpty()) return;

        var font   = client.textRenderer;
        int lineH  = 18;
        int totalH = 6 + fx.size() * lineH;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), totalH);
        AetherDraw.drawAccent(ctx, 0, 0, totalH);
        AetherDraw.drawIconPotion(ctx, 5, 5, AetherDraw.PURPLE);

        for (int i = 0; i < fx.size(); i++) {
            StatusEffectInstance inst = fx.get(i);
            RegistryEntry<StatusEffect> type = inst.getEffectType();

            // use the translation key to get the real display name
            // e.g. "effect.minecraft.water_breathing" → "Water Breathing"
            String name = formatEffectName(type);
            int amp = inst.getAmplifier();
            if (amp > 0) name += " " + roman(amp + 1);

            int secs = inst.getDuration() / 20;
            String dur = secs >= 3600
                ? (secs / 3600) + "h"
                : String.format("%d:%02d", secs / 60, secs % 60);

            int ry = 4 + i * lineH;
            ctx.drawText(font, name, 16, ry,     AetherDraw.VALUE, false);
            ctx.drawText(font, dur,  16, ry + 9, AetherDraw.LABEL, false);
        }
    }

    // Pulls the last segment of the translation key and formats it properly.
    // "effect.minecraft.water_breathing" → "Water Breathing"
    // "effect.minecraft.strength"        → "Strength"
    private String formatEffectName(RegistryEntry<StatusEffect> type) {
        // getTranslationKey() returns "effect.minecraft.effect_name"
        String key = type.value().getTranslationKey();

        // grab everything after the last dot
        String raw = key.contains(".")
            ? key.substring(key.lastIndexOf('.') + 1)
            : key;

        // snake_case → Title Case: split on underscores, capitalize each word
        String[] words = raw.split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                if (sb.length() > 0) sb.append(' ');
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }

    private String roman(int n) {
        return switch (n) {
            case 2 -> "II"; case 3 -> "III"; case 4 -> "IV"; case 5 -> "V";
            default -> String.valueOf(n);
        };
    }

    @Override public int getWidth()  { return 90; }
    @Override public int getHeight() { return 20; }
}
