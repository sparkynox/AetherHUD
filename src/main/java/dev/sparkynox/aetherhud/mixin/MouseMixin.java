package dev.sparkynox.aetherhud.mixin;

import dev.sparkynox.aetherhud.hud.HudRenderer;
import dev.sparkynox.aetherhud.hud.modules.ComboModule;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        // action 1 = press, 0 = release
        if (action != 1) return;

        if (button == 0) {
            // left click — CPS + combo
            HudRenderer.cpsModule.onLeftClick();
            ComboModule.onHit();
        } else if (button == 1) {
            // right click — CPS only
            HudRenderer.cpsModule.onRightClick();
        }
    }
}
