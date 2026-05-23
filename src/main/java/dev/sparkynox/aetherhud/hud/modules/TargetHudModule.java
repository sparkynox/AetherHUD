package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class TargetHudModule extends HudModule {

    // smoothed health bar fill — lerps toward actual health
    private float smoothHealth = 0f;

    public TargetHudModule(float x, float y) {
        super("target", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null || client.crosshairTarget == null) return;
        if (client.crosshairTarget.getType() != HitResult.Type.ENTITY) return;

        var hit = (EntityHitResult) client.crosshairTarget;
        if (!(hit.getEntity() instanceof LivingEntity target)) return;

        var font = client.textRenderer;

        float health    = target.getHealth();
        float maxHealth = target.getMaxHealth();

        // smooth health for animation
        smoothHealth += (health - smoothHealth) * 0.12f;

        String name   = target.getName().getString();
        String hpText = String.format("%.0f / %.0f", health, maxHealth);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        // target name
        ctx.drawText(font, name, 6, 3, AetherDraw.WHITE, false);

        // health bar background
        int barX = 6, barY = 14, barW = getWidth() - 12, barH = 5;
        ctx.fill(barX, barY, barX + barW, barY + barH, 0xFF1A0020);

        // health fill — color shifts red as hp drops
        float ratio = Math.min(1f, smoothHealth / maxHealth);
        int fillW = (int)(barW * ratio);
        int barColor = healthColor(ratio);
        if (fillW > 0) {
            ctx.fill(barX, barY, barX + fillW, barY + barH, barColor);
        }

        // hp numbers
        ctx.drawText(font, hpText, barX + barW - font.getWidth(hpText), 3, AetherDraw.LABEL, false);
    }

    private int healthColor(float ratio) {
        if (ratio > 0.6f) return 0xFF22C55E; // green
        if (ratio > 0.3f) return 0xFFEAB308; // yellow
        return 0xFFEF4444;                     // red
    }

    @Override public int getWidth()  { return 110; }
    @Override public int getHeight() { return 24; }
}
