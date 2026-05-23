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

    public TargetHudModule(float x, float y) {
        super("target", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.crosshairTarget == null) return;
        if (client.crosshairTarget.getType() != HitResult.Type.ENTITY) return;

        var hit = (EntityHitResult) client.crosshairTarget;
        if (!(hit.getEntity() instanceof LivingEntity target)) return;

        var font = client.textRenderer;
        float hp = target.getHealth();
        float maxHp = target.getMaxHealth();
        smoothHp += (hp - smoothHp) * 0.12f;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconHeart(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, target.getName().getString(), 17, 5, AetherDraw.WHITE, false);

        // hp bar
        int bx = 17, by = 17, bw = getWidth() - 22, bh = 4;
        ctx.fill(bx, by, bx + bw, by + bh, 0xFF1A0020);
        float ratio = Math.min(1f, smoothHp / maxHp);
        int fw = (int)(bw * ratio);
        if (fw > 0) ctx.fill(bx, by, bx + fw, by + bh, hpColor(ratio));

        // hp text right side
        String hpStr = String.format("%.0f", hp);
        ctx.drawText(font, hpStr, bx + bw - font.getWidth(hpStr), 5, AetherDraw.LABEL, false);
    }

    private int hpColor(float r) {
        if (r > 0.6f) return 0xFF22C55E;
        if (r > 0.3f) return 0xFFEAB308;
        return 0xFFEF4444;
    }

    @Override public int getWidth()  { return 110; }
    @Override public int getHeight() { return 26; }
}
