package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

public class SpeedModule extends HudModule {

    // smoothed speed value so it doesn't flicker every frame
    private float smoothSpeed = 0f;

    public SpeedModule(float x, float y) {
        super("speed", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        var font = client.textRenderer;
        PlayerEntity player = client.player;

        // horizontal speed only (no Y axis — jumping skews it)
        double dx = player.getX() - player.prevX;
        double dz = player.getZ() - player.prevZ;
        float realSpeed = (float) Math.sqrt(dx * dx + dz * dz) * 20f; // blocks/sec

        // lerp toward real speed for smooth display
        smoothSpeed += (realSpeed - smoothSpeed) * 0.15f;

        String val = String.format("%.1f", smoothSpeed) + " b/s";

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawLabelValue(ctx, font, "SPEED", val, 6, 3);
    }

    @Override public int getWidth()  { return 76; }
    @Override public int getHeight() { return 26; }
}
