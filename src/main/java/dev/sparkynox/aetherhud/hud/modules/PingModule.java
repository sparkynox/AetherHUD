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

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawLabelValue(ctx, font, "PING", ping + "ms", 6, 4);
    }

    @Override public int getWidth()  { return 64; }
    @Override public int getHeight() { return 26; }
}
