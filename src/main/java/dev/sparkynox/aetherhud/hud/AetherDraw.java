package dev.sparkynox.aetherhud.hud;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class AetherDraw {

    // ── Palette ────────────────────────────────────────────────────────────
    // Very dark translucent base — feels like frosted glass, not solid black
    public static final int BG        = 0x99080010; // ~60% opacity, cool dark
    public static final int BG_HOVER  = 0xBB0D0018; // slightly more opaque when hovered
    public static final int GLOW      = 0x228B5CF6; // ultra-soft purple inner glow layer
    public static final int BORDER    = 0x448B5CF6; // barely-visible purple edge
    public static final int ACCENT    = 0xFF7C3AED; // left-side 2px accent bar
    public static final int PURPLE    = 0xFF8B5CF6; // icon / highlight color
    public static final int PURPLE_DIM= 0xFF6D28D9; // pressed key / secondary
    public static final int LABEL     = 0xFF7A7A8E; // muted label text
    public static final int VALUE     = 0xFFE2D9F3; // soft lavender value text
    public static final int WHITE     = 0xFFFFFFFF;

    // ── Card ───────────────────────────────────────────────────────────────
    // Compact, clean card. Fake rounded corners via corner-cut fills.
    // No heavy border — just a whisper of purple on the top edge.
    public static void drawCard(DrawContext ctx, int x, int y, int w, int h) {
        // soft purple glow behind the card (1px bleed on all sides)
        ctx.fill(x,     y + 1, x + w,     y + h - 1, GLOW);
        ctx.fill(x + 1, y,     x + w - 1, y + h,     GLOW);

        // main body — slightly inset, still covers the glow
        ctx.fill(x + 1, y,     x + w - 1, y + h,     BG);
        ctx.fill(x,     y + 1, x + w,     y + h - 1, BG);

        // clip the 4 corner pixels to fake rounding
        ctx.fill(x,         y,         x + 1,     y + 1,     0x00000000);
        ctx.fill(x + w - 1, y,         x + w,     y + 1,     0x00000000);
        ctx.fill(x,         y + h - 1, x + 1,     y + h,     0x00000000);
        ctx.fill(x + w - 1, y + h - 1, x + w,     y + h,     0x00000000);

        // top micro-highlight line (1px) — gives the card a subtle "lid"
        ctx.fill(x + 2, y, x + w - 2, y + 1, BORDER);
    }

    // 2px left accent bar — the mod's signature purple stripe
    public static void drawAccent(DrawContext ctx, int x, int y, int h) {
        ctx.fill(x, y + 3, x + 2, y + h - 3, ACCENT);
    }

    // Pixel-perfect outline — used by editor only
    public static void drawOutline(DrawContext ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x,         y,         x + w,     y + 1,     color);
        ctx.fill(x,         y + h - 1, x + w,     y + h,     color);
        ctx.fill(x,         y,         x + 1,     y + h,     color);
        ctx.fill(x + w - 1, y,         x + w,     y + h,     color);
    }

    // ── Typography helpers ──────────────────────────────────────────────────
    // Small ALL-CAPS label + larger value — clean two-level hierarchy
    public static void drawLabelValue(DrawContext ctx, TextRenderer font,
                                      String label, String value,
                                      int x, int y) {
        ctx.drawText(font, label, x, y,     LABEL, false);
        ctx.drawText(font, value, x, y + 9, VALUE, false);
    }

    // Single centered value line (for combo, big-number modules)
    public static void drawValueCentered(DrawContext ctx, TextRenderer font,
                                          String value, int cx, int cy, int color) {
        int tx = cx - font.getWidth(value) / 2;
        ctx.drawText(font, value, tx, cy - font.fontHeight / 2, color, false);
    }

    // ── Pixel icons (8×8 grid, drawn with fill calls) ──────────────────────
    // All icons share the same 8×8 footprint; caller offsets as needed.

    // Feather — FPS
    public static void drawIconFeather(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+4,y,   x+8,y+1, c);
        ctx.fill(x+2,y+1, x+7,y+2, c);
        ctx.fill(x+1,y+2, x+6,y+3, c);
        ctx.fill(x,  y+3, x+5,y+4, c);
        ctx.fill(x,  y+4, x+4,y+5, c);
        ctx.fill(x,  y+5, x+3,y+6, c);
        ctx.fill(x,  y+6, x+2,y+7, c);
        ctx.fill(x,  y+7, x+1,y+8, c);
    }

    // Signal bars — Ping
    public static void drawIconSignal(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x,   y+5, x+2, y+8, c);
        ctx.fill(x+3, y+3, x+5, y+8, c);
        ctx.fill(x+6, y,   x+8, y+8, c);
    }

    // Filled circle — CPS
    public static void drawIconCircle(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+2,y,   x+6,y+1, c);
        ctx.fill(x+1,y+1, x+7,y+3, c);
        ctx.fill(x,  y+2, x+8,y+6, c);
        ctx.fill(x+1,y+6, x+7,y+7, c);
        ctx.fill(x+2,y+7, x+6,y+8, c);
    }

    // Location pin — Coords
    public static void drawIconPin(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+2,y,   x+6,y+1, c);
        ctx.fill(x+1,y+1, x+7,y+4, c);
        ctx.fill(x+2,y+4, x+6,y+5, c);
        ctx.fill(x+3,y+5, x+5,y+7, c);
        ctx.fill(x+3,y+7, x+5,y+8, c);
        ctx.fill(x+2,y+2, x+6,y+4, BG); // hollow
    }

    // Crosshair compass — Direction
    public static void drawIconCompass(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+3,y,   x+5,y+8, c);
        ctx.fill(x,  y+3, x+8,y+5, c);
        ctx.fill(x+3,y+3, x+5,y+5, BG);
    }

    // Lightning bolt — Speed
    public static void drawIconSpeed(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+3,y,   x+7,y+1, c);
        ctx.fill(x+2,y+1, x+6,y+2, c);
        ctx.fill(x+1,y+2, x+5,y+3, c);
        ctx.fill(x+1,y+3, x+6,y+4, c);
        ctx.fill(x+3,y+4, x+7,y+5, c);
        ctx.fill(x+4,y+5, x+7,y+6, c);
        ctx.fill(x+4,y+6, x+6,y+8, c);
    }

    // Sword — Combo
    public static void drawIconSword(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+3,y,   x+5,y+5, c);
        ctx.fill(x+1,y+4, x+7,y+6, c);
        ctx.fill(x+3,y+5, x+5,y+8, c);
    }

    // Clock — Playtime
    public static void drawIconClock(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+2,y,   x+6,y+1, c);
        ctx.fill(x+1,y+1, x+7,y+2, c);
        ctx.fill(x,  y+2, x+8,y+6, c);
        ctx.fill(x+1,y+6, x+7,y+7, c);
        ctx.fill(x+2,y+7, x+6,y+8, c);
        ctx.fill(x+2,y+2, x+6,y+6, BG);
        ctx.fill(x+3,y+3, x+4,y+5, c); // minute hand
        ctx.fill(x+4,y+4, x+6,y+5, c); // hour hand
    }

    // Potion bottle — Potions
    public static void drawIconPotion(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+2,y,   x+6,y+1, c);
        ctx.fill(x+3,y+1, x+5,y+2, c);
        ctx.fill(x+1,y+2, x+7,y+3, c);
        ctx.fill(x,  y+3, x+8,y+7, c);
        ctx.fill(x+1,y+7, x+7,y+8, c);
        ctx.fill(x+1,y+4, x+3,y+6, 0x44FFFFFF); // shine
    }

    // Heart — Target HUD
    public static void drawIconHeart(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x+1,y,   x+3,y+1, c);
        ctx.fill(x+5,y,   x+7,y+1, c);
        ctx.fill(x,  y+1, x+8,y+5, c);
        ctx.fill(x+1,y+5, x+7,y+6, c);
        ctx.fill(x+2,y+6, x+6,y+7, c);
        ctx.fill(x+3,y+7, x+5,y+8, c);
    }

    // Arrow — Reach
    public static void drawIconReach(DrawContext ctx, int x, int y, int c) {
        ctx.fill(x,  y+3, x+5,y+5, c);
        ctx.fill(x+4,y+1, x+6,y+7, c);
        ctx.fill(x+5,y+2, x+7,y+6, c);
        ctx.fill(x+6,y+3, x+8,y+5, c);
    }
}
