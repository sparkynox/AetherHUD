package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ComboModule extends HudModule {

    public static int combo = 0;
    private static long lastHit = 0;
    private float display = 0f;

    public ComboModule(float x, float y) {
        super("combo", x, y);
    }

    public static void onHit() {
        combo++;
        lastHit = System.currentTimeMillis();
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        if (System.currentTimeMillis() - lastHit > 3000 && combo > 0) combo = 0;
        display += (combo - display) * 0.2f;

        var font = MinecraftClient.getInstance().textRenderer;
        int shown = (int)(display + 0.5f);

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconSword(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, "COMBO", 17, 5, AetherDraw.LABEL, false);
        ctx.drawText(font, shown + "x", 17, 15, comboColor(shown), false);
    }

    private int comboColor(int c) {
        if (c >= 15) return 0xFFEF4444;
        if (c >= 8)  return 0xFFF97316;
        if (c >= 4)  return 0xFFEAB308;
        return AetherDraw.VALUE;
    }

    @Override public int getWidth()  { return 66; }
    @Override public int getHeight() { return 28; }
}
