
package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Accessor("font")
    Font c0_30ify$getFont();

    @Accessor("width")
    int c0_30ify$getWidth();

    @Accessor("height")
    int c0_30ify$getHeight();
}