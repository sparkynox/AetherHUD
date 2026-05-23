package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class SpeedModule extends HudModule {

    private float smooth = 0f;

    public SpeedModule(float x, float y) {
        super("speed", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;
        var font = client.textRenderer;

        double dx = client.player.getX() - client.player.prevX;
        double dz = client.player.getZ() - client.player.prevZ;
        float real = (float) Math.sqrt(dx * dx + dz * dz) * 20f;
        smooth += (real - smooth) * 0.15f;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconSpeed(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, "SPEED", 17, 5, AetherDraw.LABEL, false);
        ctx.drawText(font, String.format("%.1f", smooth) + " b/s", 17, 15, AetherDraw.VALUE, false);
    }

    @Override public int getWidth()  { return 78; }
    @Override public int getHeight() { return 28; }
}
