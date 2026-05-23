package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class KeystrokesModule extends HudModule {
    public KeystrokesModule(float x, float y) { super("keystrokes", x, y); }

    // key size and spacing
    private static final int KS  = 14; // key square size
    private static final int GAP =  2; // gap between keys

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        var opt  = client.options;
        var font = client.textRenderer;

        boolean w   = opt.forwardKey.isPressed();
        boolean a   = opt.leftKey.isPressed();
        boolean s   = opt.backKey.isPressed();
        boolean d   = opt.rightKey.isPressed();
        boolean lmb = opt.attackKey.isPressed();
        boolean rmb = opt.useKey.isPressed();

        // Module bg
        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());

        //   [ W ]
        // [A][ S ][D]
        // [ LMB | RMB ]

        int row1y = 2;
        int row2y = row1y + KS + GAP;
        int row3y = row2y + KS + GAP;

        // W — centered above S
        int midX = (getWidth() - KS) / 2;
        drawKey(ctx, font, "W", midX, row1y, KS, KS, w);

        // A S D — full row
        int rowStart = (getWidth() - (3*KS + 2*GAP)) / 2;
        drawKey(ctx, font, "A", rowStart,                row2y, KS, KS, a);
        drawKey(ctx, font, "S", rowStart + KS + GAP,     row2y, KS, KS, s);
        drawKey(ctx, font, "D", rowStart + 2*(KS + GAP), row2y, KS, KS, d);

        // LMB / RMB — two wide keys filling the row
        int halfW = (getWidth() - GAP) / 2;
        drawKey(ctx, font, "L", 0,          row3y, halfW,             KS, lmb);
        drawKey(ctx, font, "R", halfW + GAP, row3y, getWidth()-halfW-GAP, KS, rmb);
    }

    private void drawKey(DrawContext ctx, TextRenderer font,
                         String label, int x, int y, int w, int h, boolean pressed) {
        if (pressed) {
            // pressed: filled purple with white text
            ctx.fill(x+1, y,   x+w-1, y+h,   AetherDraw.PURPLE_DIM);
            ctx.fill(x,   y+1, x+w,   y+h-1, AetherDraw.PURPLE_DIM);
            // inner glow top edge
            ctx.fill(x+1, y, x+w-1, y+1, AetherDraw.PURPLE);
        } else {
            // not pressed: dark glass matching the card style
            ctx.fill(x+1, y,   x+w-1, y+h,   0x88050010);
            ctx.fill(x,   y+1, x+w,   y+h-1, 0x88050010);
            // subtle top highlight
            ctx.fill(x+1, y, x+w-1, y+1, AetherDraw.BORDER);
        }
        // text
        int textCol = pressed ? AetherDraw.WHITE : 0xFF55556A;
        int tx = x + (w  - font.getWidth(label)) / 2;
        int ty = y + (h  - font.fontHeight) / 2 + 1;
        ctx.drawText(font, label, tx, ty, textCol, false);
    }

    @Override public int getWidth()  { return 3*KS + 2*GAP + 2; } // ~48
    @Override public int getHeight() { return 3*KS + 2*GAP + 2; } // ~48
}
