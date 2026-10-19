package io.github.sprainedspark89.c0._30ify.client.unused;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class ExampleClientMixin { // it's here, and it's WORSE THAN EVER!!!!!!
	@Inject(at = @At("HEAD"), method = "run")
	private void init(CallbackInfo info) {
		// This code is injected into the start of Minecraft.run()V
	}
}