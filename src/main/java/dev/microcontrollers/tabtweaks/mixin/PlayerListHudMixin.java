package dev.microcontrollers.tabtweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.microcontrollers.tabtweaks.Head;
import dev.microcontrollers.tabtweaks.Shifter;
import dev.microcontrollers.tabtweaks.config.TabTweaksConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerListHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;
    @SuppressWarnings("InstantiationOfUtilityClass")
    @Unique
    PlayerSkinDrawer playerSkinDrawer = new PlayerSkinDrawer();

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getTextBackgroundColor(I)I"))
    private int modifyTabPlayerWidgetColor(int color) {
        return TabTweaksConfig.CONFIG.instance().tabPlayerListColor.getRGB();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 0), index = 4)
    private int modifyTabHeaderColor(int color) {
        return TabTweaksConfig.CONFIG.instance().tabHeaderColor.getRGB();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 1), index = 4)
    private int modifyTabBodyColor(int color) {
        return TabTweaksConfig.CONFIG.instance().tabBodyColor.getRGB();
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal = 3), index = 4)
    private int modifyTabFooterColor(int color) {
        return TabTweaksConfig.CONFIG.instance().tabFooterColor.getRGB();
    }

    @Inject(method = "renderLatencyIcon", at = @At("HEAD"), cancellable = true)
    private void textLatency(DrawContext context, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci) {
        if (!TabTweaksConfig.CONFIG.instance().showPingInTab) return;
        int ping = entry.getLatency();
        int color = -5636096;
        if (ping >= 0 && ping < 75) color = TabTweaksConfig.CONFIG.instance().pingColorOne.getRGB();
        else if (ping >= 75 && ping < 145) color = TabTweaksConfig.CONFIG.instance().pingColorTwo.getRGB();
        else if (ping >= 145 && ping < 200) color = TabTweaksConfig.CONFIG.instance().pingColorThree.getRGB();
        else if (ping >= 200 && ping < 300) color = TabTweaksConfig.CONFIG.instance().pingColorFour.getRGB();
        else if (ping >= 300 && ping < 400) color = TabTweaksConfig.CONFIG.instance().pingColorFive.getRGB();
        else if (ping >= 400) color = TabTweaksConfig.CONFIG.instance().pingColorSix.getRGB();
        String pingString = String.valueOf(ping);
        if (TabTweaksConfig.CONFIG.instance().hideFalsePing && (ping <= 1 || ping >= 999)) pingString = "";
        int pingStringLength = client.textRenderer.getWidth(pingString);
        boolean shadow = !TabTweaksConfig.CONFIG.instance().removePingShadow;
        if (TabTweaksConfig.CONFIG.instance().scalePingDisplay) {
            context.getMatrices().scale(0.5F, 0.5F, 0.5F);
            context.drawText(client.textRenderer, pingString, 2 * (x + width) - pingStringLength - 4, 2 * y + 4, color, shadow);
            context.getMatrices().scale(2F, 2F, 2F);
        } else context.drawText(client.textRenderer, pingString, x + width - pingStringLength, y, color, shadow);
        ci.cancel();
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 13))
    private int changeWidgetWidth(int original) {
        return TabTweaksConfig.CONFIG.instance().showPingInTab ? 30 : original;
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void moveTabDown(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        float distance = TabTweaksConfig.CONFIG.instance().moveTabBelowBossBars
                ? ((Shifter) client.inGameHud.getBossBarHud()).tabTweaks$getShift() > TabTweaksConfig.CONFIG.instance().moveTabDown
                    ? ((Shifter) client.inGameHud.getBossBarHud()).tabTweaks$getShift()
                    : TabTweaksConfig.CONFIG.instance().moveTabDown
                : TabTweaksConfig.CONFIG.instance().moveTabDown;
        context.getMatrices().translate(0, distance, 0);
    }

    @ModifyExpressionValue(method = "collectPlayerEntries", at = @At(value = "CONSTANT", args = "longValue=80"))
    private long changePlayerCount(long original) {
        return TabTweaksConfig.CONFIG.instance().maxTabPlayers;
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/PlayerSkinDrawer;draw(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;IIIZZ)V"))
    private boolean removeHeadRendering(DrawContext context, Identifier texture, int x, int y, int size, boolean hatVisible, boolean upsideDown) {
        return !TabTweaksConfig.CONFIG.instance().removeHeads;
    }

    // This is heavily inspired by VanillaHUD shout-out to ImToggle because I basically stole his idea
    // https://github.com/Polyfrost/VanillaHUD
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/PlayerSkinDrawer;draw(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;IIIZZ)V"))
    private void betterHatLayer(DrawContext context, Identifier texture, int x, int y, int size, boolean hatVisible, boolean upsideDown, Operation<Void> original) {
        if (TabTweaksConfig.CONFIG.instance().improvedHeads) {
            ((Head) playerSkinDrawer).tabTweaks$draw(context, texture, x, y, size, hatVisible, upsideDown);
        } else {
            original.call(context, texture, x, y, size, hatVisible, upsideDown);
        }
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;renderLatencyIcon(Lnet/minecraft/client/gui/DrawContext;IIILnet/minecraft/client/network/PlayerListEntry;)V"))
    private boolean removeLatencyRendering(PlayerListHud instance, DrawContext context, int width, int x, int y, PlayerListEntry entry) {
        return !TabTweaksConfig.CONFIG.instance().removePing;
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I"), index = 2)
    private int modifyNamePosition(int x) {
        if (TabTweaksConfig.CONFIG.instance().removeHeads) return x - 8;
        else if (TabTweaksConfig.CONFIG.instance().improvedHeads) return x + 1;
        return x;
    }

    @WrapOperation(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;header:Lnet/minecraft/text/Text;", ordinal = 0, opcode = Opcodes.GETFIELD))
    private Text removeHeader(PlayerListHud instance, Operation<Text> original) {
        if (TabTweaksConfig.CONFIG.instance().removeHeader) return null;
        return original.call(instance);
    }

    @WrapOperation(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/hud/PlayerListHud;footer:Lnet/minecraft/text/Text;", ordinal = 0, opcode = Opcodes.GETFIELD))
    private Text removeFooter(PlayerListHud instance, Operation<Text> original) {
        if (TabTweaksConfig.CONFIG.instance().removeFooter) return null;
        return original.call(instance);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", ordinal = 0))
    private int removeHeaderShadow(DrawContext instance, TextRenderer textRenderer, OrderedText text, int x, int y, int color, Operation<Integer> original) {
        if (TabTweaksConfig.CONFIG.instance().removeHeaderShadow) return instance.drawText(textRenderer, text, x, y, color, false);
        else return original.call(instance, textRenderer, text, x, y, color);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)I", ordinal = 0))
    private int removeBodyShadow(DrawContext instance, TextRenderer textRenderer, Text text, int x, int y, int color, Operation<Integer> original) {
        if (TabTweaksConfig.CONFIG.instance().removeBodyShadow) return instance.drawText(textRenderer, text, x, y, color, false);
        else return original.call(instance, textRenderer, text, x, y, color);
    }

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", ordinal = 1))
    private int removeFooterShadow(DrawContext instance, TextRenderer textRenderer, OrderedText text, int x, int y, int color, Operation<Integer> original) {
        if (TabTweaksConfig.CONFIG.instance().removeFooterShadow) return instance.drawText(textRenderer, text, x, y, color, false);
        else return original.call(instance, textRenderer, text, x, y, color);
    }
}
