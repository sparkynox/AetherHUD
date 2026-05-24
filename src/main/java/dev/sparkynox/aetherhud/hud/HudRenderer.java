package dev.sparkynox.aetherhud.hud;

import dev.sparkynox.aetherhud.hud.modules.*;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    public static final List<HudModule> modules = new ArrayList<>();
    public static CpsModule cpsModule;

    public static void init() {
        cpsModule = new CpsModule(10, 54);

        // All modules default to visible positions on a ~854x480 screen
        // Left column — info stack, 22px per row (20px card + 2px gap)
        modules.add(new FpsModule(10, 10));
        modules.add(new PingModule(10, 32));
        modules.add(cpsModule);                       // y=54
        modules.add(new CoordinatesModule(10, 76));
        modules.add(new DirectionModule(10, 98));
        modules.add(new SpeedModule(10, 120));
        modules.add(new ArmorModule(10, 142));

        // Bottom-left
        modules.add(new KeystrokesModule(10, 300));
        modules.add(new PlaytimeModule(10, 354));
        modules.add(new PotionModule(10, 376));

        // Right column — combat
        modules.add(new ComboModule(700, 10));
        modules.add(new TargetHudModule(680, 32));
        modules.add(new ReachModule(700, 54));
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
