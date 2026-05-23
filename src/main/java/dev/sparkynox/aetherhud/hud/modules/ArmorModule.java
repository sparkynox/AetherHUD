package dev.sparkynox.aetherhud.hud.modules;

import dev.sparkynox.aetherhud.hud.AetherDraw;
import dev.sparkynox.aetherhud.hud.HudModule;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class ArmorModule extends HudModule {
    private static final EquipmentSlot[] SLOTS = {
        EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
    };

    public ArmorModule(float x, float y) { super("armor", x, y); }

    @Override
    public void render(DrawContext ctx, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // count worn pieces
        int count = 0;
        for (var slot : SLOTS)
            if (!client.player.getEquippedStack(slot).isEmpty()) count++;

        if (count == 0) return; // hide entirely when naked — no empty card

        // card just wide enough for the items: 4px pad + count×16px items + 2px pad
        int cardW = 6 + count * 16 + 4;
        int cardH = getHeight();

        AetherDraw.drawCard(ctx, 0, 0, cardW, cardH);
        AetherDraw.drawAccent(ctx, 0, 0, cardH);

        // center items vertically in the card
        int drawn = 0;
        for (var slot : SLOTS) {
            ItemStack stack = client.player.getEquippedStack(slot);
            if (!stack.isEmpty()) {
                // drawItem is 16×16; offset by 1 on Y to center in 20px card
                ctx.drawItem(stack, 5 + drawn * 16, 2);
                drawn++;
            }
        }
    }

    @Override public int getWidth()  { return 74; } // fallback for editor
    @Override public int getHeight() { return 20; }
}
