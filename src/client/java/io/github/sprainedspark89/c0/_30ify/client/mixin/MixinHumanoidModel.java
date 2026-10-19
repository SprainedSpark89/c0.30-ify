package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.util.Mth;

@Mixin(HumanoidModel.class)
public abstract class MixinHumanoidModel<T extends HumanoidRenderState> {

	@Shadow
	@Final
	public ModelPart rightArm;

	@Shadow
	@Final
	public ModelPart leftArm;

	@Shadow
	@Final
	public ModelPart rightLeg;

	@Shadow
	@Final
	public ModelPart leftLeg;

	@Inject(
			method = "setupAnim",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/model/HumanoidModel;setupAttackAnimation(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V"
					)
			)
	private void c030WalkAnimation(T state, CallbackInfo ci) {
		float swing = state.walkAnimationPos;
		float amount = state.walkAnimationSpeed;

		this.rightArm.xRot =
				Mth.cos(swing * 0.6662F + (float)Math.PI)
				* 2.0F * amount;

		this.leftArm.xRot =
				Mth.cos(swing * 0.6662F)
				* 2.0F * amount;
		if (!state.isPassenger) {
			this.rightLeg.xRot =
					Mth.cos(swing * 0.6662F)
					* 1.4F * amount;
	
			this.leftLeg.xRot =
					Mth.cos(swing * 0.6662F + (float)Math.PI)
					* 1.4F * amount;
		}
		this.rightArm.zRot =
		        (Mth.cos(swing * 0.2312F) + 1.0F)
		        * amount;

		this.leftArm.zRot =
		        (Mth.cos(swing * 0.2812F) - 1.0F)
		        * amount;
		
		this.rightLeg.yRot -= 0.005F;
		this.leftLeg.yRot -= -0.005F;
		this.rightLeg.zRot -= 0.005F;
		this.leftLeg.zRot -= -0.005F;
	}

	@Redirect(
			method = "setupAnim",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/model/AnimationUtils;bobModelPart(Lnet/minecraft/client/model/geom/ModelPart;FF)V"
					)
			)
	private void replaceBobAnimation(ModelPart part, float age, float direction) {
		part.zRot += Mth.cos(age * 0.09F) * 0.05F * direction + 0.05F * direction;
		part.xRot += Mth.sin(age * 0.067F) * 0.05F * direction;
	}
}