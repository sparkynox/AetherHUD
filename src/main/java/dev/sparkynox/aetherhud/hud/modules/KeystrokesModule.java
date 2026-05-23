package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class KeystrokesModule extends HudModule {

    public KeystrokesModule(float x, float y) {
        super("keystrokes", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        var options = client.options;
        var font = client.textRenderer;

        boolean w   = options.forwardKey.isPressed();
        boolean a   = options.leftKey.isPressed();
        boolean s   = options.backKey.isPressed();
        boolean d   = options.rightKey.isPressed();
        boolean lmb = options.attackKey.isPressed();
        boolean rmb = options.useKey.isPressed();

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());

        // layout:
        //   [W]
        // [A][S][D]
        // [LMB][RMB]
        drawKey(ctx, font, "W",   22, 2,  w);
        drawKey(ctx, font, "A",   2,  14, a);
        drawKey(ctx, font, "S",   22, 14, s);
        drawKey(ctx, font, "D",   42, 14, d);
        drawKey(ctx, font, "LMB", 2,  26, lmb);
        drawKey(ctx, font, "RMB", 34, 26, rmb);
    }

    private void drawKey(DrawContext ctx, net.minecraft.client.font.TextRenderer font,
                         String label, int x, int y, boolean pressed) {
        int w = label.length() > 1 ? 28 : 16;
        int h = 10;

        // pressed = bright purple fill, not pressed = dark fill
        int bg     = pressed ? 0xFF6D28D9 : 0xCC1A0030;
        int border = pressed ? 0xFF8B5CF6 : 0x554B5FE0;
        int textC  = pressed ? AetherDraw.WHITE : AetherDraw.LABEL;

        ctx.fill(x, y, x + w, y + h, bg);
        AetherDraw.drawOutline(ctx, x, y, w, h, border);

        int tx = x + (w - font.getWidth(label)) / 2;
        int ty = y + (h - font.fontHeight) / 2 + 1;
        ctx.drawText(font, label, tx, ty, textC, false);
    }

    @Override public int getWidth()  { return 64; }
    @Override public int getHeight() { return 38; }
}
