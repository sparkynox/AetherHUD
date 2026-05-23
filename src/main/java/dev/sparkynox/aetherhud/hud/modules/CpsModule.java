package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayDeque;
import java.util.Deque;

public class CpsModule extends HudModule {

    // timestamps of recent left/right clicks
    private final Deque<Long> leftClicks  = new ArrayDeque<>();
    private final Deque<Long> rightClicks = new ArrayDeque<>();

    public CpsModule(float x, float y) {
        super("cps", x, y);
    }

    public void onLeftClick() {
        leftClicks.addLast(System.currentTimeMillis());
    }

    public void onRightClick() {
        rightClicks.addLast(System.currentTimeMillis());
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        long now = System.currentTimeMillis();
        purge(leftClicks, now);
        purge(rightClicks, now);

        int lCps = leftClicks.size();
        int rCps = rightClicks.size();

        var font = MinecraftClient.getInstance().textRenderer;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        // label
        ctx.drawText(font, "CPS", 6, 4, AetherDraw.LABEL, false);
        // L and R values side by side
        ctx.drawText(font, "L:" + lCps, 6,  14, AetherDraw.VALUE, false);
        ctx.drawText(font, "R:" + rCps, 30, 14, AetherDraw.VALUE, false);
    }

    private void purge(Deque<Long> q, long now) {
        while (!q.isEmpty() && now - q.peekFirst() > 1000) {
            q.pollFirst();
        }
    }

    @Override public int getWidth()  { return 64; }
    @Override public int getHeight() { return 26; }
}
