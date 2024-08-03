package dev.microcontrollers.tabtweaks.mixin;

import dev.microcontrollers.tabtweaks.Head;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerSkinDrawer.class)
public abstract class PlayerSkinDrawerMixin implements Head {
    @Shadow
    private static void drawHat(DrawContext context, Identifier texture, int x, int y, int size, boolean upsideDown) { }

    @Override
    public void tabTweaks$draw(DrawContext context, Identifier texture, int x, int y, int size, boolean hatVisible, boolean upsideDown) {
        int i = 8 + (upsideDown ? 8 : 0);
        int j = 8 * (upsideDown ? -1 : 1);
        context.drawTexture(texture, x, y, size, size, 8.0F, (float) i, 8, j, 64, 64);
        if (hatVisible) {
            context.getMatrices().translate(-0.5F, -0.5F, 0F);
            drawHat(context, texture, x , y , 9, upsideDown);
            context.getMatrices().translate(0.5F, 0.5F, 0F);
        }
    }
}
