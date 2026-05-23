package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Direction;

public class DirectionModule extends HudModule {

    public DirectionModule(float x, float y) {
        super("direction", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        var font = client.textRenderer;

        // yaw → cardinal direction
        float yaw = client.player.getYaw() % 360f;
        if (yaw < 0) yaw += 360f;

        String facing = getFacing(yaw);
        String yawStr = String.format("%.0f°", yaw);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        // big direction label
        ctx.drawText(font, facing, 6, 3, AetherDraw.VALUE, false);
        // yaw degrees smaller next to it
        ctx.drawText(font, yawStr, 6 + font.getWidth(facing) + 3, 3, AetherDraw.LABEL, false);

        // minecraft facing name (for nether fortress hunting etc)
        Direction dir = client.player.getHorizontalFacing();
        ctx.drawText(font, dir.getName().toUpperCase(), 6, 13, 0xFF888888, false);
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

    @Override public int getWidth()  { return 70; }
    @Override public int getHeight() { return 26; }
}
