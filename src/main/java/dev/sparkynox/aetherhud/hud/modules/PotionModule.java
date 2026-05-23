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

    public PotionModule(float x, float y) {
        super("potions", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        List<StatusEffectInstance> effects = new ArrayList<>(client.player.getStatusEffects());
        if (effects.isEmpty()) return;

        var font = client.textRenderer;
        int totalH = effects.size() * 22 + 8;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), totalH);
        AetherDraw.drawAccent(ctx, 0, 0, totalH);
        AetherDraw.drawIconPotion(ctx, 5, 6, AetherDraw.PURPLE);

        for (int i = 0; i < effects.size(); i++) {
            StatusEffectInstance inst = effects.get(i);
            RegistryEntry<StatusEffect> fx = inst.getEffectType();

            String name = fx.getIdAsString();
            if (name.contains(".")) name = name.substring(name.lastIndexOf('.') + 1);
            name = capitalize(name);
            int amp = inst.getAmplifier() + 1;
            if (amp > 1) name += " " + toRoman(amp);

            int secs = inst.getDuration() / 20;
            String dur = secs > 3600 ? (secs / 3600) + "h"
                : String.format("%d:%02d", secs / 60, secs % 60);

            int rowY = 6 + i * 22;
            ctx.drawText(font, name, 17, rowY,     AetherDraw.VALUE, false);
            ctx.drawText(font, dur,  17, rowY + 9, AetherDraw.LABEL, false);
        }
    }

    private String capitalize(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private String toRoman(int n) {
        return switch (n) { case 2 -> "II"; case 3 -> "III"; case 4 -> "IV"; case 5 -> "V"; default -> String.valueOf(n); };
    }

    @Override public int getWidth()  { return 90; }
    @Override public int getHeight() { return 28; }
}
