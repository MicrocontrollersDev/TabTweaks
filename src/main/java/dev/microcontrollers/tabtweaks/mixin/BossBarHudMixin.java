package dev.microcontrollers.tabtweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.microcontrollers.tabtweaks.Shifter;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossBarHud.class)
public class BossBarHudMixin implements Shifter {
    @Unique
    private int distance;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;getScaledWindowHeight()I", shift = At.Shift.AFTER))
    private void getBossBarHeight(DrawContext context, CallbackInfo ci, @Local(ordinal = 1) int j) {
        distance = j - 19;
    }

    @Override
    public int tabTweaks$getShift() {
        return distance;
    }

}
