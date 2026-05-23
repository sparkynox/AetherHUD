package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingModule extends HudModule {

    public PingModule(float x, float y) {
        super("ping", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        var font = client.textRenderer;

        int ping = 0;
        if (client.getNetworkHandler() != null && client.player != null) {
            PlayerListEntry entry = client.getNetworkHandler()
                .getPlayerListEntry(client.player.getUuid());
            if (entry != null) ping = entry.getLatency();
        }

        // color shifts based on ping quality
        int pingColor = ping < 80 ? 0xFF22C55E : ping < 150 ? 0xFFEAB308 : 0xFFEF4444;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        AetherDraw.drawIconSignal(ctx, 5, 10, AetherDraw.PURPLE);

        ctx.drawText(font, "PING", 17, 5, AetherDraw.LABEL, false);
        ctx.drawText(font, ping + " ms", 17, 15, pingColor, false);
    }

    @Override public int getWidth()  { return 66; }
    @Override public int getHeight() { return 28; }
}
