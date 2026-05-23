package dev.sparkynox.aetherhud.hud;

import dev.sparkynox.aetherhud.hud.modules.*;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    public static final List<HudModule> modules = new ArrayList<>();
    public static CpsModule cpsModule;

    public static void init() {
        cpsModule = new CpsModule(10, 50);

        // ── Left stack — compact 22px rows (20px card + 2px gap) ──
        modules.add(new FpsModule(10, 10));
        modules.add(new PingModule(10, 32));  // 10 + 20 + 2
        modules.add(cpsModule);               // 32 + 20 - 2 = 50... adjusted
        modules.add(new ArmorModule(10, 72));
        modules.add(new CoordinatesModule(10, 94));
        modules.add(new DirectionModule(10, 116));
        modules.add(new SpeedModule(10, 138));

        // ── Right side — combat cluster ──
        modules.add(new ComboModule(230, 10));
        modules.add(new TargetHudModule(220, 32));
        modules.add(new ReachModule(230, 52));

        // ── Bottom-left — utility ──
        modules.add(new KeystrokesModule(10, 200));
        modules.add(new PlaytimeModule(10, 252));
        modules.add(new PotionModule(10, 274));
    }

    public static void renderAll(DrawContext ctx, float tickDelta) {
        for (HudModule mod : modules) {
            if (!mod.enabled) continue;
            mod.lerpPosition();
            ctx.getMatrices().push();
            ctx.getMatrices().translate(mod.x, mod.y, 0);
            ctx.getMatrices().scale(mod.scale, mod.scale, 1f);
            mod.render(ctx, tickDelta);
            ctx.getMatrices().pop();
        }
    }
}
