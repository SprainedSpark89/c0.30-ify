package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import io.github.sprainedspark89.c0._30ify.client.accessor.ClassicHurtDirection;
import io.github.sprainedspark89.c0._30ify.client.accessor.LivingEntityRenderStateAccessor;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntityRenderer.class)
// Nostalgic tweaks was a reference while making this code, i don't really don't do modern modding, so it was quite a good reference to help do this!
// check it out!: https://github.com/Nostalgica-Reverie/Nostalgic-Tweaks/blob/1.21/common/src/main/java/mod/adrenix/nostalgic/mixin/tweak/animation/player/LivingEntityRendererMixin.java
// https://github.com/Nostalgica-Reverie/Nostalgic-Tweaks/blob/1.21/common/src/main/java/mod/adrenix/nostalgic/helper/animation/ClassicWalkHelper.java
// this code also takes a bit from c0.30 and birevan's port they made for me, thx if i haven't said that before again

// Yes, this is partly in ViaFabricPlus, as I used part of this mod to contribute to it.
public class MixinLivingEntityRenderer {

	@Redirect(
		    method = "setupRotations",
		    at = @At(
		        value = "INVOKE",
		        target = "Lcom/mojang/blaze3d/vertex/PoseStack;rotateDegrees(Lcom/mojang/math/Axis;F)V",
		        ordinal = 1
		    )
		)
		private void c030$deathRotation(PoseStack poseStack, Axis a, float f) {

		    // do nothing
		}


	@Inject(method = "setupRotations",
		    at = @At(
		        value = "INVOKE",
		        target = "Lcom/mojang/blaze3d/vertex/PoseStack;rotateDegrees(Lcom/mojang/math/Axis;F)V",
		        ordinal = 1,
		        shift = At.Shift.BEFORE // nothing is done with the fall variable beforehand, so fall = 1 can happen before this without any issues
		    ))
	private <S extends LivingEntityRenderState> void c030$deathRotation(
            S state,
            PoseStack poseStack,
            float bodyRot,
            float scale,
            CallbackInfo ci) {
		float rotation =
		        state.deathTime / 20.0F;

		rotation = rotation * rotation * 800.0F;

		if (rotation > 90.0F) {
			rotation = 90.0F;
		}

		LivingEntityRenderStateAccessor access =
		        (LivingEntityRenderStateAccessor) state;

		float hurtDir = access.getHurtDir();

		poseStack.rotateDegrees(Axis.YP,(-hurtDir));
		poseStack.rotateDegrees(Axis.ZP,(-rotation));
		poseStack.rotateDegrees(Axis.YP,(hurtDir));
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private <T extends LivingEntity, S extends LivingEntityRenderState> void c030$extract(
	        T entity,
	        S state,
	        float partialTicks,
	        CallbackInfo ci) {

	    LivingEntityRenderStateAccessor access = (LivingEntityRenderStateAccessor) state;

	    access.setHurtDir(((ClassicHurtDirection) entity).c030$getHurtDir());
	    access.setHurtTime(entity.hurtTime > 0 ? entity.hurtTime - partialTicks : 0.0F);
	    access.setHurtDuration(entity.hurtDuration);

	    state.hasRedOverlay = false;
	}

	@Inject(
	        method = "setupRotations",
	        at = @At(
	            value = "INVOKE",
	            target = "Lcom/mojang/blaze3d/vertex/PoseStack;rotateDegrees(Lcom/mojang/math/Axis;F)V",
	            ordinal = 0,
	            shift = At.Shift.AFTER
	        )
	    )
	    private <S extends LivingEntityRenderState> void c030$hurtRotation(
	            S state,
	            PoseStack poseStack,
	            float bodyRot,
	            float scale,
	            CallbackInfo ci) {

	        LivingEntityRenderStateAccessor access =
	                (LivingEntityRenderStateAccessor) state;

	        float hurt = access.getHurtTime();

	        if (hurt <= 0.0F) {
				return;
			}

	        hurt /= access.getHurtDuration();
	        hurt = Mth.sin(hurt * hurt * hurt * hurt * (float)Math.PI) * 14.0F;

	        float hurtDir = access.getHurtDir();

	        poseStack.rotateDegrees(Axis.YP,(-hurtDir));
	        poseStack.rotateDegrees(Axis.ZP,(-hurt));
	        poseStack.rotateDegrees(Axis.YP,(hurtDir));
	    }

	@Redirect(
		    method = "submit",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getWhiteOverlayProgress(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;)F"
		    )
		)
	private <S extends LivingEntityRenderState> float c030$whiteDamageOverlay(
	        LivingEntityRenderer<?, ?, ?> renderer,
	        S state
	) {

		LivingEntityRenderStateAccessor access =
	            (LivingEntityRenderStateAccessor) state;

		float hurt = access.getHurtTime();

		if (hurt > 0) {
		    hurt /= access.getHurtDuration();
		    return Mth.sin(hurt * hurt * hurt * hurt * (float)Math.PI);
		}

		    return ((LivingEntityRendererAccessor) renderer)
		            .c030$getWhiteOverlayProgress(state);
		}

//	@Inject(
//		    method = "getWhiteOverlayProgress",
//		    at = @At("HEAD"),
//		    cancellable = true
//		)
//		private <S extends LivingEntityRenderState> void c030$whiteOverlay(
//		        S state,
//		        CallbackInfoReturnable<Float> cir
//		) {
//
//		    LivingEntityRenderStateAccessor access =
//		            (LivingEntityRenderStateAccessor) state;
//
//		    if (access.getHurtTime() > 0.0F) {
//		    	cir.setReturnValue(0.75F);
//		    }
//
//		    if (state.deathTime > 0.0F) {
//		    	cir.setReturnValue(0.75F);
//		    }
//		}

	@Inject(
			method = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;submit(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", // targets the render method
			at = @At(
					ordinal = 1,
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"
					)
			)
	public <S extends LivingEntityRenderState> void oldBobbing(S livingEntityRenderState, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo callback) {

		float pos = livingEntityRenderState.walkAnimationPos;
		float speed = livingEntityRenderState.walkAnimationSpeed;

		matrixStack.translate(
		    0.0F,
		    -Math.abs(Math.cos(pos * 0.6662F)) * 0.3125F * speed,
		    0.0F
		);

	}

}