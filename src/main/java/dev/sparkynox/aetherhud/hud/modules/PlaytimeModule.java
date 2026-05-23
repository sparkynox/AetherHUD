package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class PlaytimeModule extends HudModule {

    private static final long START = System.currentTimeMillis();

    public PlaytimeModule(float x, float y) {
        super("playtime", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var font = MinecraftClient.getInstance().textRenderer;
        long s = (System.currentTimeMillis() - START) / 1000;
        String t = s >= 3600
            ? String.format("%dh %02dm", s / 3600, (s % 3600) / 60)
            : String.format("%02d:%02d", s / 60, s % 60);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconClock(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, "SESSION", 17, 5, AetherDraw.LABEL, false);
        ctx.drawText(font, t, 17, 15, AetherDraw.VALUE, false);
    }

    @Override public int getWidth()  { return 80; }
    @Override public int getHeight() { return 28; }
}
