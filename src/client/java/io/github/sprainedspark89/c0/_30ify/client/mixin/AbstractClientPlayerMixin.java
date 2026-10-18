package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.accessor.AvatarStateAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
	@Inject(method = "updateBob", at = @At("TAIL"))
	private void updateClassicTilt(CallbackInfo ci) {
		AbstractClientPlayer player = Minecraft.getInstance().player;
		double yd = player.getDeltaMovement().y;

		float targetTilt = (float)Math.atan(-yd * 0.2) * 15.0F;

		if (player.onGround() || player.isDeadOrDying()) {
		    targetTilt = 0.0F;
		}
		
		AvatarStateAccessor state =
			    (AvatarStateAccessor)player.avatarState();

		state.updateTilt(targetTilt);
	}
}
