package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.gui.DeathScreenClassic;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

@Mixin(DeathScreen.class)
public class DeathScreenMixin extends Screen {
	protected DeathScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo i) {
		if(C030IfyClient.classicGui) {
			this.minecraft.gui.setScreen(new DeathScreenClassic(Component.literal("Game Over!")));
		}
	}
}
