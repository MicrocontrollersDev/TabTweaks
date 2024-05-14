package dev.microcontrollers.tabtweaks;

import dev.microcontrollers.tabtweaks.config.TabTweaksConfig;
import net.fabricmc.api.ModInitializer;

public class TabTweaks implements ModInitializer {
	@Override
	public void onInitialize() {
		TabTweaksConfig.CONFIG.load();
	}

}