package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class PlaytimeModule extends HudModule {

    private static final long startTime = System.currentTimeMillis();

    public PlaytimeModule(float x, float y) {
        super("playtime", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var font = MinecraftClient.getInstance().textRenderer;

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long hours   = elapsed / 3600;
        long mins    = (elapsed % 3600) / 60;
        long secs    = elapsed % 60;

        String time = hours > 0
            ? String.format("%dh %02dm", hours, mins)
            : String.format("%02d:%02d", mins, secs);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawLabelValue(ctx, font, "SESSION", time, 6, 3);
    }

    @Override public int getWidth()  { return 80; }
    @Override public int getHeight() { return 26; }
}
