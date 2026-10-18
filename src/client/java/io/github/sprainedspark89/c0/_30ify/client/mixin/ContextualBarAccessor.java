package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBar;

@Mixin(ContextualBar.class)
public interface ContextualBarAccessor {
    @Invoker("extractExperienceLevel")
    static void c030ify$extractExperienceLevel(
        GuiGraphicsExtractor graphics,
        Font font,
        int experienceLevel
    ) {
        throw new AssertionError();
    }
}