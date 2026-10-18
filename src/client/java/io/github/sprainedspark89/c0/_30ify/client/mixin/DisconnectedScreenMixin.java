package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.gui.ClassicErrorScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;

@Mixin(DisconnectedScreen.class)
public class DisconnectedScreenMixin extends Screen {
	
	@Shadow
	@Final
	private DisconnectionDetails details;
	
	protected DisconnectedScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Inject(method = "init", at = @At("TAIL"))
	public void init(CallbackInfo i) {
		if(C030IfyClient.classicGui) {
			this.minecraft.gui.setScreen(new ClassicErrorScreen(title.getString(), details.reason().getString(), true));
		}
	}
	
}
