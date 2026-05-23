package dev.sparkynox.aetherhud.hud;

import net.minecraft.client.gui.DrawContext;

public abstract class HudModule {

    public String id;
    public float x, y;
    public float targetX, targetY; // lerp target for smooth drag
    public boolean enabled;
    public float scale;

    public HudModule(String id, float defaultX, float defaultY) {
        this.id = id;
        this.x = this.targetX = defaultX;
        this.y = this.targetY = defaultY;
        this.enabled = true;
        this.scale = 1.0f;
    }

    // every module implements its own draw
    public abstract void render(DrawContext ctx, float tickDelta);

    // used by editor for click detection
    public abstract int getWidth();
    public abstract int getHeight();

    // smooth lerp toward target — call before rendering
    public void lerpPosition() {
        float speed = 0.18f;
        x += (targetX - x) * speed;
        y += (targetY - y) * speed;
    }
}
