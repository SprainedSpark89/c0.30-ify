package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.Identifier;

@Mixin(TooltipRenderUtil.class)
public class TooltipRenderUtilMixin {

	@Inject(method = "extractTooltipBackground", at = @At("HEAD"), cancellable = true)
	private static void extractTooltipBackground(
			final GuiGraphicsExtractor graphics, final int x, final int y, final int w, final int h, final @Nullable Identifier style, CallbackInfo ci
		) {
			if(C030IfyClient.classicGui) {
				graphics.fillGradient(x - 3, y - 3, x + w + 3, y + h + 3, -1607454624, -1607454624);
				ci.cancel();
			}
		}
}
