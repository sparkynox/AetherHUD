package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayDeque;
import java.util.Deque;

public class CpsModule extends HudModule {
    private final Deque<Long> L = new ArrayDeque<>();
    private final Deque<Long> R = new ArrayDeque<>();

    public CpsModule(float x, float y) { super("cps", x, y); }

    public void onLeftClick()  { L.addLast(System.currentTimeMillis()); }
    public void onRightClick() { R.addLast(System.currentTimeMillis()); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        long now = System.currentTimeMillis();
        while (!L.isEmpty() && now - L.peekFirst() > 1000) L.pollFirst();
        while (!R.isEmpty() && now - R.peekFirst() > 1000) R.pollFirst();

        var font = MinecraftClient.getInstance().textRenderer;
        String val = L.size() + " · " + R.size();

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());
        AetherDraw.drawIconCircle(ctx, 5, 5, AetherDraw.PURPLE);
        ctx.drawText(font, "CPS", 16, 2,  AetherDraw.LABEL, false);
        ctx.drawText(font, val,   16, 11, AetherDraw.VALUE, false);
    }

    @Override public int getWidth()  { return 58; }
    @Override public int getHeight() { return 20; }
}
