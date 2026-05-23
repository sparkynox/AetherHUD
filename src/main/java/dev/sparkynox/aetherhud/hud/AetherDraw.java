package dev.sparkynox.aetherhud.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class AetherDraw {

    // --- colors ---
    public static final int BG        = 0xCC0A0010; // near-black with slight purple tint
    public static final int BORDER    = 0x998B5CF6; // purple border
    public static final int ACCENT    = 0xFF8B5CF6; // solid purple accent bar
    public static final int LABEL     = 0xFFAAAAAA; // grey label text
    public static final int VALUE     = 0xFFE2D9F3; // light purple-white value text
    public static final int WHITE     = 0xFFFFFFFF;

    // draws the dark rounded-ish card background
    // Minecraft's fill() is rectangular — we fake rounded by stacking fills
    public static void drawCard(DrawContext ctx, int x, int y, int w, int h) {
        ctx.fill(x + 2, y,     x + w - 2, y + h,     BG);
        ctx.fill(x,     y + 2, x + w,     y + h - 2, BG);

        // top purple border line
        ctx.fill(x + 2, y, x + w - 2, y + 1, BORDER);
        // bottom subtle border
        ctx.fill(x + 2, y + h - 1, x + w - 2, y + h, 0x338B5CF6);
    }

    // left-side purple accent strip
    public static void drawAccent(DrawContext ctx, int x, int y, int h) {
        ctx.fill(x, y + 2, x + 2, y + h - 2, ACCENT);
    }

    // label on top, value below — standard module layout
    public static void drawLabelValue(DrawContext ctx, TextRenderer font,
                                      String label, String value, int x, int y) {
        ctx.drawText(font, label, x, y,      LABEL, false);
        ctx.drawText(font, value, x, y + 10, VALUE, false);
    }

    // single line centered in the card — used for combo etc.
    public static void drawCentered(DrawContext ctx, TextRenderer font,
                                    String text, int cardX, int cardY, int cardW, int cardH, int color) {
        int tx = cardX + (cardW - font.getWidth(text)) / 2;
        int ty = cardY + (cardH - font.fontHeight) / 2;
        ctx.drawText(font, text, tx, ty, color, false);
    }

    // highlight border — used in editor
    public static void drawOutline(DrawContext ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x,         y,         x + w,     y + 1,     color); // top
        ctx.fill(x,         y + h - 1, x + w,     y + h,     color); // bottom
        ctx.fill(x,         y,         x + 1,     y + h,     color); // left
        ctx.fill(x + w - 1, y,         x + w,     y + h,     color); // right
    }
}
