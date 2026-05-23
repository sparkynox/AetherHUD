package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ComboModule extends HudModule {

    public static int combo = 0;
    private static long lastHitTime = 0;

    // combo resets if no hit in 3 seconds
    private static final long RESET_MS = 3000;

    // smooth display value (lerps toward real combo)
    private float displayCombo = 0;

    public ComboModule(float x, float y) {
        super("combo", x, y);
    }

    public static void onHit() {
        combo++;
        lastHitTime = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        // reset if idle
        if (System.currentTimeMillis() - lastHitTime > RESET_MS && combo > 0) {
            combo = 0;
        }

        // smooth lerp so number doesn't snap
        displayCombo += (combo - displayCombo) * 0.2f;

        var font = MinecraftClient.getInstance().textRenderer;
        int shown = (int)(displayCombo + 0.5f);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        ctx.drawText(font, "COMBO", 6, 3, AetherDraw.LABEL, false);

        // color shifts: white -> yellow -> orange -> red based on combo count
        int color = comboColor(shown);
        ctx.drawText(font, shown + "x", 6, 13, color, false);
    }

    private int comboColor(int c) {
        if (c >= 15) return 0xFFEF4444; // red
        if (c >= 8)  return 0xFFF97316; // orange
        if (c >= 4)  return 0xFFEAB308; // yellow
        return AetherDraw.VALUE;          // default purple-white
    }

    @Override public int getWidth()  { return 64; }
    @Override public int getHeight() { return 26; }
}
