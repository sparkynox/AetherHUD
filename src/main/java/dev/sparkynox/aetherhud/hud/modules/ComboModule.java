package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class ComboModule extends HudModule {
    public static int combo = 0;
    private static long lastHit = 0;
    private float display = 0f;

    public ComboModule(float x, float y) { super("combo", x, y); }

    public static void onHit() { combo++; lastHit = System.currentTimeMillis(); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        if (System.currentTimeMillis() - lastHit > 3000) combo = 0;
        display += (combo - display) * 0.2f;
        int shown = (int)(display + 0.5f);

        var font = MinecraftClient.getInstance().textRenderer;
        int col  = shown >= 15 ? 0xFFF87171 : shown >= 8 ? 0xFFFB923C : shown >= 4 ? 0xFFFBBF24 : AetherDraw.VALUE;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconSword(ctx, 5, 5, AetherDraw.PURPLE);
        ctx.drawText(font, "COMBO",    16, 2,  AetherDraw.LABEL, false);
        ctx.drawText(font, shown + "×", 16, 11, col,              false);
    }

    @Override public int getWidth()  { return 56; }
    @Override public int getHeight() { return 20; }
}
