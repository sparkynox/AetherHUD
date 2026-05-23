package dev.sparkynox.aetherhud.editor;

import dev.sparkynox.aetherhud.config.HudConfig;
import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import dev.sparkynox.aetherhud.hud.HudRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class HudEditorScreen extends Screen {

    private HudModule dragging    = null;
    private float dragOffX, dragOffY;

    // right-click context menu state
    private HudModule menuTarget  = null;
    private int menuX, menuY;

    public HudEditorScreen() {
        super(Text.literal("AetherHUD Editor"));
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // semi-dark overlay so you can see the game underneath
        ctx.fill(0, 0, width, height, 0x99000000);

        // draw all modules
        for (HudModule mod : HudRenderer.modules) {
            ctx.getMatrices().push();
            ctx.getMatrices().translate(mod.x, mod.y, 0);
            ctx.getMatrices().scale(mod.scale, mod.scale, 1f);

            if (mod.enabled) {
                mod.render(ctx, delta);
            } else {
                // show disabled modules as dim ghost card
                drawGhost(ctx, mod);
            }

            ctx.getMatrices().pop();

            // outline on hover or active drag
            boolean hovered = isHovered(mod, mouseX, mouseY);
            if (mod == dragging || hovered) {
                int bx = (int) mod.x;
                int by = (int) mod.y;
                int bw = (int)(mod.getWidth()  * mod.scale);
                int bh = (int)(mod.getHeight() * mod.scale);
                AetherDraw.drawOutline(ctx, bx, by, bw, bh,
                    mod.enabled ? 0xFF8B5CF6 : 0xFF555555);
            }
        }

        // right-click context menu
        if (menuTarget != null) {
            drawContextMenu(ctx, menuX, menuY);
        }

        // bottom hint bar
        ctx.fill(0, height - 20, width, height, 0xCC0A0010);
        ctx.drawCenteredTextWithShadow(textRenderer,
            "§5Drag§7 to move  §5Right-click§7 to toggle/scale  §5ESC§7 to save & close",
            width / 2, height - 13, AetherDraw.WHITE);
    }

    private void drawGhost(DrawContext ctx, HudModule mod) {
        // just a dim rectangle placeholder for disabled modules
        ctx.fill(0, 0, mod.getWidth(), mod.getHeight(), 0x44333333);
        ctx.drawText(textRenderer, mod.id, 4, (mod.getHeight() - 8) / 2, 0xFF555555, false);
    }

    private void drawContextMenu(DrawContext ctx, int x, int y) {
        int w = 100, h = 48;
        // keep menu inside screen
        if (x + w > width)  x = width  - w - 2;
        if (y + h > height) y = height - h - 2;

        ctx.fill(x, y, x + w, y + h, 0xEE0D0020);
        AetherDraw.drawOutline(ctx, x, y, w, h, 0xFF8B5CF6);

        // toggle button
        String toggleLabel = menuTarget.enabled ? "§cDisable" : "§aEnable";
        ctx.drawText(textRenderer, toggleLabel, x + 6, y + 6, AetherDraw.WHITE, false);

        // scale buttons
        ctx.drawText(textRenderer, "Scale:  §5[-]  §7" +
            String.format("%.1f", menuTarget.scale) + "  §5[+]",
            x + 6, y + 22, AetherDraw.WHITE, false);

        ctx.drawText(textRenderer, "§7Click outside to close", x + 6, y + 36, 0xFF555555, false);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int btn) {
        // close context menu if open and clicking outside
        if (menuTarget != null) {
            int x = menuX, y = menuY, w = 100, h = 48;
            if (x + w > width)  x = width  - w - 2;
            if (y + h > height) y = height - h - 2;

            if (mx >= x && mx <= x + w && my >= y && my <= y + h) {
                // inside menu — handle clicks
                handleMenuClick((int) mx, (int) my, x, y);
                return true;
            } else {
                menuTarget = null;
                return true;
            }
        }

        if (btn == 1) {
            // right click — open context menu on hovered module
            for (HudModule mod : HudRenderer.modules) {
                if (isHovered(mod, (int) mx, (int) my)) {
                    menuTarget = mod;
                    menuX = (int) mx;
                    menuY = (int) my;
                    return true;
                }
            }
        }

        if (btn == 0) {
            // left click — start drag
            for (HudModule mod : HudRenderer.modules) {
                if (!mod.enabled) continue;
                if (isHovered(mod, (int) mx, (int) my)) {
                    dragging  = mod;
                    dragOffX  = (float)(mx - mod.x);
                    dragOffY  = (float)(my - mod.y);
                    return true;
                }
            }
        }

        return super.mouseClicked(mx, my, btn);
    }

    private void handleMenuClick(int mx, int my, int menuOriginX, int menuOriginY) {
        if (menuTarget == null) return;

        // toggle row — y range 2..18
        if (my >= menuOriginY + 2 && my <= menuOriginY + 18) {
            menuTarget.enabled = !menuTarget.enabled;
            menuTarget = null;
            return;
        }

        // scale row — y range 18..34
        if (my >= menuOriginY + 18 && my <= menuOriginY + 34) {
            // [-] is roughly x+42..x+54, [+] is x+70..x+82
            if (mx >= menuOriginX + 42 && mx <= menuOriginX + 54) {
                menuTarget.scale = Math.max(0.5f, menuTarget.scale - 0.1f);
            } else if (mx >= menuOriginX + 70 && mx <= menuOriginX + 82) {
                menuTarget.scale = Math.min(3.0f, menuTarget.scale + 0.1f);
            }
        }
    }

    @Override
    public boolean mouseDragged(double mx, double my, int btn, double dx, double dy) {
        if (dragging != null && btn == 0) {
            float nx = (float)(mx - dragOffX);
            float ny = (float)(my - dragOffY);
            // clamp inside screen
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
    public boolean shouldPause() {
        return false; // game keeps running while editor is open
    }

    private boolean isHovered(HudModule mod, int mx, int my) {
        int bw = (int)(mod.getWidth()  * mod.scale);
        int bh = (int)(mod.getHeight() * mod.scale);
        return mx >= mod.x && mx <= mod.x + bw
            && my >= mod.y && my <= mod.y + bh;
    }
}
