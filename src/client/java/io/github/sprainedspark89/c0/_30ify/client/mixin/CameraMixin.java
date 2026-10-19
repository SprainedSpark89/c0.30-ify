package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.accessor.AvatarStateAccessor;
import io.github.sprainedspark89.c0._30ify.client.accessor.CameraEntityRenderStateAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.entity.ClientAvatarState;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;

@Mixin(Camera.class)
public class CameraMixin {

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void c030ify$copyClassicTilt(
	        CameraRenderState cameraState,
	        float partialTicks,
	        CallbackInfo ci
	) {
	    if (this.entity instanceof AbstractClientPlayer player) {
	        ClientAvatarState avatarState = player.avatarState();

	        ((CameraEntityRenderStateAccessor) cameraState.entityRenderState)
	            .setClassicTilt(
	                ((AvatarStateAccessor) avatarState)
	                    .getInterpolatedClassicTilt(partialTicks)
	            );
	    }
	}

    @Shadow
    private Entity entity;
}