package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class DirectionModule extends HudModule {

    public DirectionModule(float x, float y) {
        super("direction", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;
        var font = client.textRenderer;

        float yaw = client.player.getYaw() % 360f;
        if (yaw < 0) yaw += 360f;

        String facing = getFacing(yaw);
        String dir = client.player.getHorizontalFacing().getName().toUpperCase();

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconCompass(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, facing + "  " + String.format("%.0f", yaw) + "°", 17, 5, AetherDraw.VALUE, false);
        ctx.drawText(font, dir, 17, 15, AetherDraw.LABEL, false);
    }

    private String getFacing(float yaw) {
        if (yaw < 22.5f || yaw >= 337.5f) return "S";
        if (yaw < 67.5f)  return "SW";
        if (yaw < 112.5f) return "W";
        if (yaw < 157.5f) return "NW";
        if (yaw < 202.5f) return "N";
        if (yaw < 247.5f) return "NE";
        if (yaw < 292.5f) return "E";
        return "SE";
    }

    @Override public int getWidth()  { return 72; }
    @Override public int getHeight() { return 28; }
}
