package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingModule extends HudModule {
    public PingModule(float x, float y) { super("ping", x, y); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        var font   = client.textRenderer;

        int ping = 0;
        if (client.getNetworkHandler() != null && client.player != null) {
            PlayerListEntry e = client.getNetworkHandler()
                .getPlayerListEntry(client.player.getUuid());
            if (e != null) ping = e.getLatency();
        }

        // value color reflects quality
        int col = ping < 80 ? 0xFF4ADE80 : ping < 150 ? 0xFFFBBF24 : 0xFFF87171;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconSignal(ctx, 5, 5, AetherDraw.PURPLE);
        ctx.drawText(font, "PING",      16, 2,  AetherDraw.LABEL, false);
        ctx.drawText(font, ping + "ms", 16, 11, col,              false);
    }

    @Override public int getWidth()  { return 54; }
    @Override public int getHeight() { return 20; }
}
