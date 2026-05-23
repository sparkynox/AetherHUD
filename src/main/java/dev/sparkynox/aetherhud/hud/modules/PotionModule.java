package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PotionModule extends HudModule {

    public PotionModule(float x, float y) {
        super("potions", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Collection<StatusEffectInstance> effects = client.player.getStatusEffects();
        if (effects.isEmpty()) return;

        var font = client.textRenderer;
        List<StatusEffectInstance> list = new ArrayList<>(effects);

        int totalH = list.size() * 22 + 4;
        AetherDraw.drawCard(ctx, 0, 0, getWidth(), totalH);
        AetherDraw.drawAccent(ctx, 0, 0, totalH);

        for (int i = 0; i < list.size(); i++) {
            StatusEffectInstance inst = list.get(i);
            RegistryEntry<StatusEffect> effect = inst.getEffectType();

            // effect name — trim the "effect." prefix Minecraft adds
            String name = effect.getIdAsString();
            if (name.contains(".")) name = name.substring(name.lastIndexOf('.') + 1);
            name = capitalize(name);

            // duration in seconds
            int seconds = inst.getDuration() / 20;
            String dur = seconds > 3600
                ? (seconds / 3600) + "h"
                : String.format("%d:%02d", seconds / 60, seconds % 60);

            int rowY = 4 + i * 22;

            // amplifier dot — level indicator
            int amp = inst.getAmplifier() + 1;
            String ampStr = amp > 1 ? " " + toRoman(amp) : "";

            ctx.drawText(font, name + ampStr, 6, rowY,     AetherDraw.VALUE, false);
            ctx.drawText(font, dur,           6, rowY + 9, AetherDraw.LABEL, false);
        }
    }

    private String capitalize(String s) {
        if (s.isEmpty()) return s;
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private String toRoman(int n) {
        return switch (n) {
            case 2  -> "II";
            case 3  -> "III";
            case 4  -> "IV";
            case 5  -> "V";
            default -> String.valueOf(n);
        };
    }

    @Override public int getWidth()  { return 90; }
    @Override public int getHeight() { return 26; } // dynamic in render, this is minimum
}
