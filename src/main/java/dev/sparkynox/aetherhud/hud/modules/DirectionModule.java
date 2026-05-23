package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class DirectionModule extends HudModule {
    public DirectionModule(float x, float y) { super("direction", x, y); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;
        var font = client.textRenderer;

        float yaw = client.player.getYaw() % 360f;
        if (yaw < 0) yaw += 360f;
        String facing = getFacing(yaw);
        String sub    = String.format("%.0f°", yaw);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconCompass(ctx, 5, 5, AetherDraw.PURPLE);

        ctx.drawText(font, facing, 16, 2,  AetherDraw.VALUE, false);
        ctx.drawText(font, sub,    16, 11, AetherDraw.LABEL, false);
    }

    private String getFacing(float y) {
        if (y<22.5f||y>=337.5f) return "S";
        if (y<67.5f)  return "SW";
        if (y<112.5f) return "W";
        if (y<157.5f) return "NW";
        if (y<202.5f) return "N";
        if (y<247.5f) return "NE";
        if (y<292.5f) return "E";
        return "SE";
    }

    @Override public int getWidth()  { return 54; }
    @Override public int getHeight() { return 20; }
}
