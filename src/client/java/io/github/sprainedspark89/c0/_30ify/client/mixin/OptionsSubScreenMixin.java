package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.gui.ControlsScreenClassic;
import io.github.sprainedspark89.c0._30ify.client.gui.OptionsScreenClassic;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;

@Mixin(KeyBindsScreen.class)
public abstract class OptionsSubScreenMixin extends OptionsSubScreen {

	public OptionsSubScreenMixin(Screen lastScreen, Options options, Component title) {
		super(lastScreen, options, title);
		// TODO Auto-generated constructor stub
	}

	protected void init() {
		super.init();
		if(C030IfyClient.classicGui) {
			this.minecraft.gui.setScreen(new ControlsScreenClassic(lastScreen, options));
		}
	}
}
