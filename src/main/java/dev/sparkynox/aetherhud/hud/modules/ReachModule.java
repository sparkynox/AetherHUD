package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class ReachModule extends HudModule {

    public ReachModule(float x, float y) {
        super("reach", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null || client.crosshairTarget == null) return;
        if (client.crosshairTarget.getType() != HitResult.Type.ENTITY) return;

        var hit = (EntityHitResult) client.crosshairTarget;
        double dist = client.player.distanceTo(hit.getEntity());
        var font = client.textRenderer;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconReach(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, "REACH", 17, 5, AetherDraw.LABEL, false);
        ctx.drawText(font, String.format("%.2f", dist) + "b", 17, 15, AetherDraw.VALUE, false);
    }

    @Override public int getWidth()  { return 72; }
    @Override public int getHeight() { return 28; }
}
