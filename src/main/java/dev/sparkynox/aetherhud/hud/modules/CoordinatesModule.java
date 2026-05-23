package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.BlockPos;

public class CoordinatesModule extends HudModule {

    public CoordinatesModule(float x, float y) {
        super("coords", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        var font = client.textRenderer;
        var pos = client.player.getBlockPos();

        String xStr = "X: " + pos.getX();
        String yStr = "Y: " + pos.getY();
        String zStr = "Z: " + pos.getZ();

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        ctx.drawText(font, "XYZ", 6, 3, AetherDraw.LABEL, false);
        ctx.drawText(font, xStr, 6,  13, 0xFFEF4444, false); // red X
        ctx.drawText(font, yStr, 42, 13, 0xFF22C55E, false); // green Y
        ctx.drawText(font, zStr, 78, 13, 0xFF60A5FA, false); // blue Z
    }

    @Override public int getWidth()  { return 120; }
    @Override public int getHeight() { return 26; }
}
