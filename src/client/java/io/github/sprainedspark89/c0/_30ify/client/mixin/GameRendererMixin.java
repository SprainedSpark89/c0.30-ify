package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import io.github.sprainedspark89.c0._30ify.client.accessor.CameraEntityRenderStateAccessor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(at = @At("HEAD"), method = "bobView", cancellable = true)
	private void bobbing(CameraRenderState cameraState, PoseStack poseStack, CallbackInfo info) {
		if (cameraState.entityRenderState.isPlayer) {
			float backwardsInterpolatedWalkDistance = cameraState.entityRenderState.backwardsInterpolatedWalkDistance;
			float bob = cameraState.entityRenderState.bob;
			poseStack.translate(
				Mth.sin(backwardsInterpolatedWalkDistance * (float) Math.PI) * bob * 0.5F,
				-Math.abs(Mth.cos(backwardsInterpolatedWalkDistance * (float) Math.PI) * bob),
				0.0F
			);
			poseStack.rotateDegrees(Axis.ZP,(Mth.sin(backwardsInterpolatedWalkDistance * (float) Math.PI) * bob * 3.0F));
			poseStack.rotateDegrees(Axis.XP,(Math.abs(Mth.cos(backwardsInterpolatedWalkDistance * (float) Math.PI + 0.2F) * bob) * 5.0F));

			float tilt = ((CameraEntityRenderStateAccessor)cameraState.entityRenderState).getClassicTilt();
			poseStack.rotateDegrees(Axis.XP,(tilt));
		}
		info.cancel();
	}
}
