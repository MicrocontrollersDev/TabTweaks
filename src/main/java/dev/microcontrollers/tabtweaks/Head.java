package dev.microcontrollers.tabtweaks;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public interface Head {
    void tabTweaks$draw(DrawContext context, Identifier texture, int x, int y, int size, boolean hatVisible, boolean upsideDown);
}
