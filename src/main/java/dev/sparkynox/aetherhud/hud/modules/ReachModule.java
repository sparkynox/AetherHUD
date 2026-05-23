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
        String val = String.format("%.2f", dist) + "b";

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawLabelValue(ctx, font, "REACH", val, 6, 3);
    }

    @Override public int getWidth()  { return 70; }
    @Override public int getHeight() { return 26; }
}
