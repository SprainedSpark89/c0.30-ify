package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.gui.OptionsScreenClassic;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {

	@Shadow
	@Final
	private Screen lastScreen;
	@Shadow
	@Final
	private Options options;
	protected OptionsScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}
	
	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo i) {
		if(C030IfyClient.classicGui) {
			this.minecraft.gui.setScreen(new OptionsScreenClassic(lastScreen, options));
		}
	}
	
	@Override
	public boolean keyPressed(final KeyEvent event) {
		if(C030IfyClient.toggleC030GUI.matches(event)) {
			C030IfyClient.classicGui = !C030IfyClient.classicGui;
			this.minecraft.gui.setScreen(new OptionsScreenClassic(lastScreen, options));
		}
		return super.keyPressed(event);
	}
}
