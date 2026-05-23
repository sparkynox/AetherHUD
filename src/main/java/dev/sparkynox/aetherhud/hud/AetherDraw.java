package dev.sparkynox.aetherhud.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class AetherDraw {

    // --- Colors --- darker & more transparent than before
    public static final int BG     = 0xB3050008; // very dark, 70% opacity
    public static final int BORDER = 0x668B5CF6; // subtle purple border
    public static final int ACCENT = 0xFF7C3AED; // deep purple accent bar
    public static final int LABEL  = 0xFF888888; // muted grey label
    public static final int VALUE  = 0xFFDDD6FE; // soft lavender value
    public static final int PURPLE = 0xFF8B5CF6; // icon purple
    public static final int WHITE  = 0xFFFFFFFF;

    // darker card — more blur-like feel, 70% opacity background
    public static void drawCard(DrawContext ctx, int x, int y, int w, int h) {
        // main body
        ctx.fill(x + 2, y,     x + w - 2, y + h,     BG);
        ctx.fill(x,     y + 2, x + w,     y + h - 2, BG);
        // top purple border (subtle)
        ctx.fill(x + 2, y, x + w - 2, y + 1, BORDER);
    }

    // left accent bar
    public static void drawAccent(DrawContext ctx, int x, int y, int h) {
        ctx.fill(x, y + 3, x + 2, y + h - 3, ACCENT);
    }

    // label (small, grey) + value (bigger, lavender) layout
    public static void drawLabelValue(DrawContext ctx, TextRenderer font,
                                      String label, String value, int x, int y) {
        ctx.drawText(font, label, x, y,      LABEL, false);
        ctx.drawText(font, value, x, y + 10, VALUE, false);
    }

    // outline — editor highlight
    public static void drawOutline(DrawContext ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x,         y,         x + w,     y + 1,     color);
        ctx.fill(x,         y + h - 1, x + w,     y + h,     color);
        ctx.fill(x,         y,         x + 1,     y + h,     color);
        ctx.fill(x + w - 1, y,         x + w,     y + h,     color);
    }

    // ---------------------------------------------------------------
    // PIXEL ICONS — drawn with ctx.fill() rectangles, purple colored
    // ---------------------------------------------------------------

    // Feather-like bird/arrow icon — for FPS
    // Looks like a small angular wing shape
    public static void drawIconFeather(DrawContext ctx, int x, int y, int color) {
        // wing top line (diagonal)
        ctx.fill(x + 4, y,     x + 8, y + 1, color);
        ctx.fill(x + 2, y + 1, x + 7, y + 2, color);
        ctx.fill(x + 1, y + 2, x + 6, y + 3, color);
        // shaft
        ctx.fill(x,     y + 3, x + 5, y + 4, color);
        ctx.fill(x,     y + 4, x + 4, y + 5, color);
        ctx.fill(x,     y + 5, x + 3, y + 6, color);
        ctx.fill(x,     y + 6, x + 2, y + 7, color);
        ctx.fill(x,     y + 7, x + 1, y + 8, color);
    }

    // Signal bars icon — for PING (3 bars, tallest = best)
    public static void drawIconSignal(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x,     y + 5, x + 2, y + 8, color); // bar 1 — short
        ctx.fill(x + 3, y + 3, x + 5, y + 8, color); // bar 2 — medium
        ctx.fill(x + 6, y,     x + 8, y + 8, color); // bar 3 — tall
    }

    // Circle dot — for CPS
    public static void drawIconCircle(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 2, y,     x + 6, y + 1, color);
        ctx.fill(x + 1, y + 1, x + 7, y + 2, color);
        ctx.fill(x,     y + 2, x + 8, y + 6, color);
        ctx.fill(x + 1, y + 6, x + 7, y + 7, color);
        ctx.fill(x + 2, y + 7, x + 6, y + 8, color);
        // hollow center
        ctx.fill(x + 2, y + 2, x + 6, y + 6, BG);
    }

    // Clock icon — for Playtime
    public static void drawIconClock(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 2, y,     x + 6, y + 1, color);
        ctx.fill(x + 1, y + 1, x + 7, y + 2, color);
        ctx.fill(x,     y + 2, x + 8, y + 6, color);
        ctx.fill(x + 1, y + 6, x + 7, y + 7, color);
        ctx.fill(x + 2, y + 7, x + 6, y + 8, color);
        ctx.fill(x + 2, y + 2, x + 6, y + 6, BG); // hollow
        // hands
        ctx.fill(x + 3, y + 3, x + 4, y + 5, color); // minute hand
        ctx.fill(x + 4, y + 4, x + 6, y + 5, color); // hour hand
    }

    // Lightning bolt — for Speed
    public static void drawIconSpeed(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 3, y,     x + 7, y + 1, color);
        ctx.fill(x + 2, y + 1, x + 6, y + 2, color);
        ctx.fill(x + 1, y + 2, x + 5, y + 3, color);
        ctx.fill(x + 1, y + 3, x + 6, y + 4, color);
        ctx.fill(x + 2, y + 4, x + 6, y + 5, color);
        ctx.fill(x + 3, y + 5, x + 7, y + 6, color);
        ctx.fill(x + 4, y + 6, x + 7, y + 7, color);
        ctx.fill(x + 4, y + 7, x + 6, y + 8, color);
    }

    // Crosshair / compass — for Direction
    public static void drawIconCompass(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 3, y,     x + 5, y + 8, color); // vertical
        ctx.fill(x,     y + 3, x + 8, y + 5, color); // horizontal
        ctx.fill(x + 3, y + 3, x + 5, y + 5, BG);    // center gap
    }

    // Sword icon — for Combo
    public static void drawIconSword(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 3, y,     x + 5, y + 5, color); // blade
        ctx.fill(x + 1, y + 4, x + 7, y + 6, color); // crossguard
        ctx.fill(x + 3, y + 5, x + 5, y + 8, color); // handle
    }

    // Potion bottle — for Potions
    public static void drawIconPotion(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 2, y,     x + 6, y + 1, color); // neck top
        ctx.fill(x + 3, y + 1, x + 5, y + 2, color); // neck
        ctx.fill(x + 1, y + 2, x + 7, y + 3, color); // shoulder
        ctx.fill(x,     y + 3, x + 8, y + 7, color); // body
        ctx.fill(x + 1, y + 7, x + 7, y + 8, color); // bottom
        // shine
        ctx.fill(x + 1, y + 4, x + 3, y + 6, 0x44FFFFFF);
    }

    // Location pin — for Coordinates
    public static void drawIconPin(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x + 2, y,     x + 6, y + 1, color);
        ctx.fill(x + 1, y + 1, x + 7, y + 4, color);
        ctx.fill(x + 2, y + 4, x + 6, y + 5, color);
        ctx.fill(x + 3, y + 5, x + 5, y + 7, color);
        ctx.fill(x + 3, y + 7, x + 5, y + 8, color);
        ctx.fill(x + 2, y + 2, x + 6, y + 4, BG); // hollow
    }

    // Heart — for Target HUD
    public static void drawIconHeart(DrawContext ctx, int x, int y, int color) {
        ctx.fill(x,     y + 1, x + 3, y + 4, color);
        ctx.fill(x + 5, y + 1, x + 8, y + 4, color);
        ctx.fill(x + 1, y,     x + 3, y + 1, color);
        ctx.fill(x + 5, y,     x + 7, y + 1, color);
        ctx.fill(x,     y + 3, x + 8, y + 6, color);
        ctx.fill(x + 1, y + 6, x + 7, y + 7, color);
        ctx.fill(x + 2, y + 7, x + 6, y + 8, color);
        ctx.fill(x + 3, y + 7, x + 5, y + 8, color);
    }

    // Arrow target / reach icon
    public static void drawIconReach(DrawContext ctx, int x, int y, int color) {
        // arrow pointing right
        ctx.fill(x,     y + 3, x + 6, y + 5, color); // shaft
        ctx.fill(x + 4, y + 1, x + 8, y + 7, color); // head broad
        ctx.fill(x + 5, y + 2, x + 8, y + 6, color);
        ctx.fill(x + 6, y + 3, x + 8, y + 5, color);
    }
}
