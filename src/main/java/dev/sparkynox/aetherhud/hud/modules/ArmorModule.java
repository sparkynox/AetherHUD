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

        // collect worn pieces first
        ItemStack[] worn = new ItemStack[4];
        int count = 0;
        for (int i = 0; i < SLOTS.length; i++) {
            ItemStack s = client.player.getEquippedStack(SLOTS[i]);
            if (!s.isEmpty()) { worn[i] = s; count++; }
        }
        if (count == 0) return; // nothing equipped, hide entirely

        var font = client.textRenderer;

        // each slot column = 16px item + 2px gap, + small % label below
        // row height: 16 item + 2 gap + 7 text = 25 per slot
        // card: NO background — fully transparent, just items floating
        // only in editor do we show a ghost outline

        int slotW = 18; // 16px item + 2px right gap
        int cardW  = 4 + count * slotW;
        int cardH  = 26; // 16 item + 2 gap + 8 text

        // NO drawCard — intentionally transparent, clean floating look
        // just a very subtle accent dot on left so editor can grab it
        ctx.fill(0, 4, 1, cardH - 4, 0x44A78BFA);

        int col = 0;
        for (int i = 0; i < SLOTS.length; i++) {
            if (worn[i] == null) continue;

            int ix = 3 + col * slotW;

            // draw the item (16x16)
            ctx.drawItem(worn[i], ix, 0);

            // durability %
            int maxDur  = worn[i].getMaxDamage();
            int curDmg  = worn[i].getDamage();
            String pct;
            int pctColor;

            if (maxDur <= 0) {
                // unbreakable item (e.g. elytra with mending, or creative)
                pct = "∞";
                pctColor = AetherDraw.LABEL;
            } else {
                float ratio = 1f - ((float) curDmg / maxDur);
                int p = (int)(ratio * 100);
                pct = p + "%";
                // color by durability remaining
                pctColor = p > 60 ? 0xFF4ADE80   // green
                         : p > 30 ? 0xFFFBBF24   // yellow
                         :          0xFFF87171;  // red
            }

            // center the % label under the item
            int tx = ix + (16 - font.getWidth(pct)) / 2;
            ctx.drawText(font, pct, tx, 18, pctColor, false);

            col++;
        }
    }

    // used by editor hit detection — real width depends on equipped count
    // return a reasonable max so the editor grab area works
    @Override public int getWidth()  { return 4 + 4 * 18; } // max 4 pieces
    @Override public int getHeight() { return 26; }
}