package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class TargetHudModule extends HudModule {
    private float smoothHp = 0f;
    public TargetHudModule(float x, float y) { super("target", x, y); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.crosshairTarget == null) return;
        if (client.crosshairTarget.getType() != HitResult.Type.ENTITY) return;
        var hit = (EntityHitResult) client.crosshairTarget;
        if (!(hit.getEntity() instanceof LivingEntity target)) return;

        var font  = client.textRenderer;
        float hp  = target.getHealth();
        float max = target.getMaxHealth();
        smoothHp += (hp - smoothHp) * 0.12f;

        String name = target.getName().getString();
        // trim long names so they don't overflow
        if (font.getWidth(name) > getWidth() - 28)
            name = font.trimToWidth(name, getWidth() - 32) + "…";

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconHeart(ctx, 5, 5, AetherDraw.PURPLE);

        ctx.drawText(font, name, 16, 2, AetherDraw.WHITE, false);

        // compact hp bar — 3px tall, tight under the name
        int bx = 16, by = 13, bw = getWidth() - 20, bh = 3;
        ctx.fill(bx, by, bx + bw, by + bh, 0xFF160020);
        float ratio = Math.min(1f, smoothHp / max);
        int fw = (int)(bw * ratio);
        if (fw > 0) {
            int col = ratio > 0.6f ? 0xFF4ADE80 : ratio > 0.3f ? 0xFFFBBF24 : 0xFFF87171;
            ctx.fill(bx, by, bx + fw, by + bh, col);
        }
        // hp fraction right-aligned
        String hps = String.format("%.0f", hp);
        ctx.drawText(font, hps, bx + bw - font.getWidth(hps), 2, AetherDraw.LABEL, false);
    }

    @Override public int getWidth()  { return 100; }
    @Override public int getHeight() { return 18; }
}
