package dev.sparkynox.aetherhud.hud;

import dev.sparkynox.aetherhud.hud.modules.*;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    public static final List<HudModule> modules = new ArrayList<>();

    // exposed so MouseMixin can reach it directly
    public static CpsModule cpsModule;

    public static void init() {
        cpsModule = new CpsModule(10, 74);

        // --- left side stack (default positions) ---
        modules.add(new FpsModule(10, 10));
        modules.add(new PingModule(10, 42));
        modules.add(cpsModule);
        modules.add(new ArmorModule(10, 106));
        modules.add(new CoordinatesModule(10, 138));
        modules.add(new DirectionModule(10, 170));
        modules.add(new SpeedModule(10, 202));

        // --- combat modules (top right area by default) ---
        modules.add(new ComboModule(250, 10));
        modules.add(new TargetHudModule(250, 42));
        modules.add(new ReachModule(250, 72));

        // --- misc ---
        modules.add(new KeystrokesModule(10, 240));
        modules.add(new PlaytimeModule(10, 284));
        modules.add(new PotionModule(10, 316));
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
