package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.gui.GuiMainTitle;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo i) {
		if(C030IfyClient.classicGui) {
			this.minecraft.gui.setScreen(new GuiMainTitle(Component.literal("Title Screen")));
		}
	}
	
	@Inject(method = "extractBackground", at = @At("HEAD"))
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a, CallbackInfo i) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}
}
