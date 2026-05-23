package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ArmorModule extends HudModule {

    private static final EquipmentSlot[] SLOTS = {
        EquipmentSlot.HEAD,
        EquipmentSlot.CHEST,
        EquipmentSlot.LEGS,
        EquipmentSlot.FEET
    };

    public ArmorModule(float x, float y) {
        super("armor", x, y);
    }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        AetherDraw.drawCard(ctx, 0, 0, getWidth(), getHeight());
        AetherDraw.drawAccent(ctx, 0, 0, getHeight());

        // render each armor slot left to right, skip empty slots
        int drawn = 0;
        for (EquipmentSlot slot : SLOTS) {
            ItemStack stack = client.player.getEquippedStack(slot);
            if (!stack.isEmpty()) {
                ctx.drawItem(stack, 5 + drawn * 18, 4);
                // durability bar is drawn automatically by drawItem
                drawn++;
            }
        }

        // nothing equipped — show placeholder text
        if (drawn == 0) {
            ctx.drawText(client.textRenderer, "No Armor", 6, 8, 0xFF555555, false);
        }
    }

    @Override public int getWidth()  { return 82; }
    @Override public int getHeight() { return 26; }
}
