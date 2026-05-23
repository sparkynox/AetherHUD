package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

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

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconPin(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, "XYZ", 17, 5, AetherDraw.LABEL, false);
        // color-coded axes
        ctx.drawText(font, "" + pos.getX(), 17, 15, 0xFFEF4444, false);
        ctx.drawText(font, "/" + pos.getY(), 17 + font.getWidth("" + pos.getX()), 15, 0xFF22C55E, false);
        ctx.drawText(font, "/" + pos.getZ(),
            17 + font.getWidth("" + pos.getX()) + font.getWidth("/" + pos.getY()), 15, 0xFF60A5FA, false);
    }

    @Override public int getWidth()  { return 100; }
    @Override public int getHeight() { return 28; }
}
