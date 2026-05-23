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
        var client = MinecraftClient.getInstance();
        var font = client.textRenderer;
        int fps = client.getCurrentFps();

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        // feather icon at left
        AetherDraw.drawIconFeather(ctx, 5, 9, AetherDraw.PURPLE);

        // FPS label + value
        ctx.drawText(font, "FPS", 17, 5, AetherDraw.LABEL, false);
        ctx.drawText(font, String.valueOf(fps), 17, 15, AetherDraw.VALUE, false);
    }

    @Override public int getWidth()  { return 58; }
    @Override public int getHeight() { return 28; }
}
