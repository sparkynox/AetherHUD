package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

public class CoordinatesModule extends HudModule {
    public CoordinatesModule(float x, float y) { super("coords", x, y); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;
        var font = client.textRenderer;
        var pos  = client.player.getBlockPos();

        String xs = String.valueOf(pos.getX());
        String ys = String.valueOf(pos.getY());
        String zs = String.valueOf(pos.getZ());

        // measure to size card dynamically
        int textW = font.getWidth(xs + " " + ys + " " + zs);
        int cardW = Math.max(getWidth(), 16 + textW + 4);

        AetherDraw.drawCard(ctx, 0, 0, cardW, getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconPin(ctx, 5, 5, AetherDraw.PURPLE);

        ctx.drawText(font, "XYZ", 16, 2, AetherDraw.LABEL, false);

        // tight inline: X Y Z each in their axis color
        int tx = 16;
        ctx.drawText(font, xs, tx, 11, 0xFFF87171, false); tx += font.getWidth(xs);
        ctx.drawText(font, " ", tx, 11, AetherDraw.LABEL,  false); tx += font.getWidth(" ");
        ctx.drawText(font, ys, tx, 11, 0xFF4ADE80, false); tx += font.getWidth(ys);
        ctx.drawText(font, " ", tx, 11, AetherDraw.LABEL,  false); tx += font.getWidth(" ");
        ctx.drawText(font, zs, tx, 11, 0xFF60A5FA, false);
    }

    @Override public int getWidth()  { return 88; }
    @Override public int getHeight() { return 20; }
}
