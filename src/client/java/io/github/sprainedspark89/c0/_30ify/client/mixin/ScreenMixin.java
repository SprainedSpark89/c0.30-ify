package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;

@Mixin(Screen.class)
public abstract class ScreenMixin extends AbstractContainerEventHandler implements Renderable {
	
	@Shadow
	public int width;
	@Shadow
	public int height;

	@Redirect(
		    method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/gui/screens/Screen;extractTransparentBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"
		    )
		)
		private void redirectTransparentBackground(Screen screen, GuiGraphicsExtractor graphics) {
		    replaceBackground(graphics);
		}
	
	@Redirect(
		    method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/gui/screens/Screen;extractMenuBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V"
		    )
		)
		private void redirectMenuBackground(Screen screen, GuiGraphicsExtractor graphics) {
		    replaceBackground(graphics);
		}
	
	private void replaceBackground(GuiGraphicsExtractor graphics) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}
	
	@Overwrite
	protected void extractPanorama(final GuiGraphicsExtractor graphics, final float a) {
		
	}
}
