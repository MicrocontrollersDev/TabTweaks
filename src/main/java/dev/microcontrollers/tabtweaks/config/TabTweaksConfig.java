package dev.microcontrollers.tabtweaks.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.FloatSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class TabTweaksConfig {
    public static final ConfigClassHandler<TabTweaksConfig> CONFIG = ConfigClassHandler.createBuilder(TabTweaksConfig.class)
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("tabtweaks.json"))
                    .build())
            .build();

    // TODO: @SerialEntry public boolean removeNpcs = false;

    @SerialEntry public int maxTabPlayers = 80;
    @SerialEntry public boolean removeHeader = false;
    @SerialEntry public boolean removeFooter = false;
    @SerialEntry public boolean removeHeads = false;
    @SerialEntry public boolean removeNpcHeads = false;
    @SerialEntry public boolean removeHeaderShadow = false;
    @SerialEntry public boolean removeBodyShadow = false;
    @SerialEntry public boolean removeFooterShadow = false;
    @SerialEntry public boolean improvedHeads = true;
    @SerialEntry public boolean removeObjectives = false;
    @SerialEntry public boolean removePing = false;
    @SerialEntry public boolean showPingInTab = false;
    @SerialEntry public boolean removePingShadow = false;
    @SerialEntry public boolean scalePingDisplay = false;
    @SerialEntry public boolean hideFalsePing = false;

    @SerialEntry public float moveTabDown = 10F;
    @SerialEntry public boolean moveTabBelowBossBars = true;

    @SerialEntry public Color tabHeaderColor = new Color(0, 0, 0, 128);
    @SerialEntry public Color tabBodyColor = new Color(0, 0, 0, 128);
    @SerialEntry public Color tabFooterColor = new Color(0, 0, 0, 128);
    @SerialEntry public Color tabPlayerListColor = new Color(255, 255, 255, 32);

    @SerialEntry public Color pingColorOne = new Color(-15466667);
    @SerialEntry public Color pingColorTwo = new Color(-14773218);
    @SerialEntry public Color pingColorThree = new Color(-4733653);
    @SerialEntry public Color pingColorFour = new Color(-13779);
    @SerialEntry public Color pingColorFive = new Color(-6458098);
    @SerialEntry public Color pingColorSix = new Color(-4318437);

    public static Screen configScreen(Screen parent) {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
                .title(Text.translatable("tab-tweaks.tab-tweaks"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("tab-tweaks.tab-tweaks"))

                        // Visual

                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("tab-tweaks.visual"))
                                .option(Option.<Integer>createBuilder()
                                        .name(Text.translatable("tab-tweaks.max-players"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.max-players.description")))
                                        .binding(80, () -> config.maxTabPlayers, newVal -> config.maxTabPlayers = newVal)
                                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                                .range(1, 200)
                                                .step(1))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-header"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-header.description")))
                                        .binding(defaults.removeHeader, () -> config.removeHeader, newVal -> config.removeHeader = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-footer"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-footer.description")))
                                        .binding(defaults.removeFooter, () -> config.removeFooter, newVal -> config.removeFooter = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-header-shadow"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-header-shadow.description")))
                                        .binding(defaults.removeHeaderShadow, () -> config.removeHeaderShadow, newVal -> config.removeHeaderShadow = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-body-shadow"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-body-shadow.description")))
                                        .binding(defaults.removeBodyShadow, () -> config.removeBodyShadow, newVal -> config.removeBodyShadow = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-footer-shadow"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-footer-shadow.description")))
                                        .binding(defaults.removeFooterShadow, () -> config.removeFooterShadow, newVal -> config.removeFooterShadow = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-heads"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-heads.description")))
                                        .binding(defaults.removeHeads, () -> config.removeHeads, newVal -> config.removeHeads = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-npc-heads"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-npc-heads.description")))
                                        .binding(defaults.removeNpcHeads, () -> config.removeNpcHeads, newVal -> config.removeNpcHeads = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.improved-player-head-hats"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.improved-player-head-hats.description")))
                                        .binding(defaults.improvedHeads, () -> config.improvedHeads, newVal -> config.improvedHeads = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-objectives"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-objectives.description")))
                                        .binding(defaults.removeObjectives, () -> config.removeObjectives, newVal -> config.removeObjectives = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-ping"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-ping.description")))
                                        .binding(defaults.removePing, () -> config.removePing, newVal -> config.removePing = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.show-numerical-ping"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.show-numerical-ping.description")))
                                        .binding(defaults.showPingInTab, () -> config.showPingInTab, newVal -> config.showPingInTab = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.remove-numerical-ping-shadow"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.remove-numerical-ping-shadow.description")))
                                        .binding(defaults.removePingShadow, () -> config.removePingShadow, newVal -> config.removePingShadow = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.scale-numerical-ping"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.scale-numerical-ping.description")))
                                        .binding(defaults.scalePingDisplay, () -> config.scalePingDisplay, newVal -> config.scalePingDisplay = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.hide-fake-numerical-ping"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.hide-fake-numerical-ping.description")))
                                        .binding(defaults.hideFalsePing, () -> config.hideFalsePing, newVal -> config.hideFalsePing = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())

                        // Position

                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("tab-tweaks.position"))
                                .option(Option.<Float>createBuilder()
                                        .name(Text.translatable("tab-tweaks.move-tab-list-down"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.move-tab-list-down.description")))
                                        .binding(10F, () -> config.moveTabDown, newVal -> config.moveTabDown = newVal)
                                        .controller(opt -> FloatSliderControllerBuilder.create(opt)
                                                .formatValue(value -> Text.of(String.format("%,.0f", value)))
                                                .range(0F, 60F)
                                                .step(1F))
                                        .build())
                                .option(Option.<Boolean>createBuilder()
                                        .name(Text.translatable("tab-tweaks.move-tab-list-below-bossbars"))
                                        .description(OptionDescription.of(Text.translatable("tab-tweaks.move-tab-list-below-bossbars.description")))
                                        .binding(defaults.moveTabBelowBossBars, () -> config.moveTabBelowBossBars, newVal -> config.moveTabBelowBossBars = newVal)
                                        .controller(TickBoxControllerBuilder::create)
                                        .build())
                                .build())

                        // Tab Color

                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("tab-tweaks.tab-color"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.header-color"))
                                        .binding(defaults.tabHeaderColor, () -> config.tabHeaderColor, value -> config.tabHeaderColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.body-color"))
                                        .binding(defaults.tabBodyColor, () -> config.tabBodyColor, value -> config.tabBodyColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.footer-color"))
                                        .binding(defaults.tabFooterColor, () -> config.tabFooterColor, value -> config.tabFooterColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.player-widget-color"))
                                        .binding(defaults.tabPlayerListColor, () -> config.tabPlayerListColor, value -> config.tabPlayerListColor = value)
                                        .customController(opt -> new ColorController(opt, true))
                                        .build())
                                .build())

                        // Ping Color

                        .group(OptionGroup.createBuilder()
                                .name(Text.translatable("tab-tweaks.ping-color"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.ping-between-0-and-75"))
                                        .binding(defaults.pingColorOne, () -> config.pingColorOne, value -> config.pingColorOne = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.ping-between-75-and-145"))
                                        .binding(defaults.pingColorTwo, () -> config.pingColorTwo, value -> config.pingColorTwo = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.ping-between-145-and-200"))
                                        .binding(defaults.pingColorThree, () -> config.pingColorThree, value -> config.pingColorThree = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.ping-between-200-and-300"))
                                        .binding(defaults.pingColorFour, () -> config.pingColorFour, value -> config.pingColorFour = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.ping-between-300-and-400"))
                                        .binding(defaults.pingColorFive, () -> config.pingColorFive, value -> config.pingColorFive = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.translatable("tab-tweaks.ping-above-400"))
                                        .binding(defaults.pingColorSix, () -> config.pingColorSix, value -> config.pingColorSix = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())
        )).generateScreen(parent);
    }

}