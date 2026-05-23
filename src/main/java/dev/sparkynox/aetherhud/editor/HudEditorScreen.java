package dev.sparkynox.aetherhud.editor;

import dev.sparkynox.aetherhud.config.HudConfig;
import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import dev.sparkynox.aetherhud.hud.HudRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {

    private HudModule dragging = null;
    private float dragOffX, dragOffY;

    // size of the hide button on each module
    private static final int BTN_SIZE = 10;

    public HudEditorScreen() {
        super(Text.literal("AetherHUD Editor"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // dark overlay — keep game visible underneath
        ctx.fill(0, 0, width, height, 0x99000000);

        for (HudModule mod : HudRenderer.modules) {
            // draw module (even disabled = ghost card)
            ctx.getMatrices().push();
            ctx.getMatrices().translate(mod.x, mod.y, 0);
            ctx.getMatrices().scale(mod.scale, mod.scale, 1f);

            if (mod.enabled) {
                mod.render(ctx, delta);
            } else {
                drawGhost(ctx, mod);
            }

            ctx.getMatrices().pop();

            // hover/drag outline
            if (mod == dragging || isHoveredModule(mod, mouseX, mouseY)) {
                AetherDraw.drawOutline(ctx,
                    (int) mod.x, (int) mod.y,
                    (int)(mod.getWidth()  * mod.scale),
                    (int)(mod.getHeight() * mod.scale),
                    mod.enabled ? 0xFF8B5CF6 : 0xFF444444);
            }

            // draw the [-] hide/show button at top-right corner of every module
            drawToggleBtn(ctx, mod, mouseX, mouseY);
        }

        // bottom bar
        ctx.fill(0, height - 22, width, height, 0xDD050008);
        ctx.drawCenteredTextWithShadow(textRenderer,
            "§5Drag§7 to move  §5[-]§7 to hide/show  §5Right-click§7 to scale  §5ESC§7 to save",
            width / 2, height - 14, AetherDraw.WHITE);
    }

    // draws a small [-] or [+] button at the top-right of each module card
    private void drawToggleBtn(DrawContext ctx, HudModule mod, int mouseX, int mouseY) {
        int bx = (int)(mod.x + mod.getWidth() * mod.scale) - BTN_SIZE - 1;
        int by = (int) mod.y + 1;

        boolean hovering = mouseX >= bx && mouseX <= bx + BTN_SIZE
            && mouseY >= by && mouseY <= by + BTN_SIZE;

        // button bg
        int bg = hovering ? 0xEE8B5CF6 : (mod.enabled ? 0xCC330044 : 0xCC222222);
        ctx.fill(bx, by, bx + BTN_SIZE, by + BTN_SIZE, bg);
        AetherDraw.drawOutline(ctx, bx, by, BTN_SIZE, BTN_SIZE, 0xFF8B5CF6);

        // - symbol (hide) or + (show)
        String sym = mod.enabled ? "-" : "+";
        int tx = bx + (BTN_SIZE - textRenderer.getWidth(sym)) / 2;
        int ty = by + (BTN_SIZE - textRenderer.fontHeight) / 2 + 1;
        ctx.drawText(textRenderer, sym, tx, ty, AetherDraw.WHITE, false);
    }

    private void drawGhost(DrawContext ctx, HudModule mod) {
        ctx.fill(0, 0, mod.getWidth(), mod.getHeight(), 0x33222222);
        AetherDraw.drawOutline(ctx, 0, 0, mod.getWidth(), mod.getHeight(), 0x44555555);
        ctx.drawText(textRenderer, mod.id, 4, (mod.getHeight() - 8) / 2, 0xFF444444, false);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        // check [-] buttons first (left click only)
        if (btn == 0) {
            for (HudModule mod : HudRenderer.modules) {
                if (isHoveredBtn(mod, (int) mx, (int) my)) {
                    mod.enabled = !mod.enabled;
                    return true;
                }
            }
        }

        // right click = scale menu (quick inline)
        if (btn == 1) {
            for (HudModule mod : HudRenderer.modules) {
                if (isHoveredModule(mod, (int) mx, (int) my)) {
                    // cycle scale: 0.75 → 1.0 → 1.25 → 1.5 → 0.75
                    if (mod.scale < 0.9f)       mod.scale = 1.0f;
                    else if (mod.scale < 1.15f)  mod.scale = 1.25f;
                    else if (mod.scale < 1.4f)   mod.scale = 1.5f;
                    else                          mod.scale = 0.75f;
                    return true;
                }
            }
        }

        // left click on module body = start drag
        if (btn == 0) {
            for (HudModule mod : HudRenderer.modules) {
                if (!mod.enabled) continue;
                if (isHoveredModule(mod, (int) mx, (int) my)) {
                    dragging = mod;
                    dragOffX = (float)(mx - mod.x);
                    dragOffY = (float)(my - mod.y);
                    return true;
                }
            }
        }

        return super.mouseClicked(mx, my, btn);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if (dragging != null && btn == 0) {
            float nx = (float)(mx - dragOffX);
            float ny = (float)(my - dragOffY);
            nx = Math.max(0, Math.min(nx, width  - (int)(dragging.getWidth()  * dragging.scale)));
            ny = Math.max(0, Math.min(ny, height - (int)(dragging.getHeight() * dragging.scale)));
            dragging.x = dragging.targetX = nx;
            dragging.y = dragging.targetY = ny;
            return true;
        }
        return super.mouseDragged(mx, my, btn, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mx, double my, int btn) {
        dragging = null;
        return super.mouseReleased(mx, my, btn);
    }

    @Override
    public void close() {
        HudConfig.save();
        super.close();
    }

    @Override
    public boolean shouldPause() { return false; }

    // checks if mouse is over the module card area
    private boolean isHoveredModule(HudModule mod, int mx, int my) {
        int bw = (int)(mod.getWidth()  * mod.scale);
        int bh = (int)(mod.getHeight() * mod.scale);
        return mx >= mod.x && mx <= mod.x + bw
            && my >= mod.y && my <= mod.y + bh;
    }

    // checks if mouse is over the [-] button specifically
    private boolean isHoveredBtn(HudModule mod, int mx, int my) {
        int bx = (int)(mod.x + mod.getWidth() * mod.scale) - BTN_SIZE - 1;
        int by = (int) mod.y + 1;
        return mx >= bx && mx <= bx + BTN_SIZE
            && my >= by && my <= by + BTN_SIZE;
    }
}
