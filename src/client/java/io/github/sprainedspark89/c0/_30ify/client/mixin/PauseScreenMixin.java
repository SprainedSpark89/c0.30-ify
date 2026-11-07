package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.gui.PauseScreenClassic;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {
	protected PauseScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Shadow
	@Final
	private boolean showPauseMenu;

	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo i) {
		if(C030IfyClient.classicGui) {
			this.minecraft.gui.setScreen(new PauseScreenClassic(Component.literal("Game Menu")));
		}
	}

	@Inject(
		    method = "extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V",
		    at = @At("HEAD"
		    ), cancellable = true
		)
		private void redirectMenuBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a, CallbackInfo i) {
		if (this.showPauseMenu) {
			if (this.minecraft.gui.screen() == this) {
				this.extractBlurredBackground(graphics);
			}

		    replaceBackground(graphics);
			this.minecraft.gui.hud.extractDeferredSubtitles();
		}
i.cancel();
		}

	private void replaceBackground(GuiGraphicsExtractor graphics) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}
}
