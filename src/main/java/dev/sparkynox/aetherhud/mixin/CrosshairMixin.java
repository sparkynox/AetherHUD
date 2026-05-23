package dev.sparkynox.aetherhud.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class CrosshairMixin {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void onRenderCrosshair(DrawContext ctx, RenderTickCounter counter, CallbackInfo ci) {
        var client = MinecraftClient.getInstance();
        if (client.player == null || client.crosshairTarget == null) return;

        // only care about entity hits
        if (client.crosshairTarget.getType() != HitResult.Type.ENTITY) return;

        var hit = (EntityHitResult) client.crosshairTarget;

        // only turn red for player entities
        if (!(hit.getEntity() instanceof PlayerEntity)) return;

        int cx = ctx.getScaledWindowWidth()  / 2;
        int cy = ctx.getScaledWindowHeight() / 2;

        // cancel vanilla crosshair render
        ci.cancel();

        // draw our red crosshair manually
        int color = 0xFFFF3333;

        // horizontal bar
        ctx.fill(cx - 6, cy - 1, cx - 2, cy + 1, color);
        ctx.fill(cx + 2, cy - 1, cx + 6, cy + 1, color);

        // vertical bar
        ctx.fill(cx - 1, cy - 6, cx + 1, cy - 2, color);
        ctx.fill(cx - 1, cy + 2, cx + 1, cy + 6, color);

        // center dot
        ctx.fill(cx - 1, cy - 1, cx + 1, cy + 1, color);
    }
}
