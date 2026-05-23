package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class FpsModule extends HudModule {

    public FpsModule(float x, float y) {
        super("fps", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var font = MinecraftClient.getInstance().textRenderer;
        int fps = MinecraftClient.getInstance().getCurrentFps();

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawLabelValue(ctx, font, "FPS", String.valueOf(fps), 6, 4);
    }

    @Override public int getWidth()  { return 54; }
    @Override public int getHeight() { return 26; }
}
