package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

@Mixin(FirstPersonHandsAndItemsRenderer.class)
public class ItemInHandRendererMixin {
	@Inject(at = @At("HEAD"), method = "renderPlayerArm", cancellable = true)
	private void arms(final PoseStack poseStack,
			SubmitNodeCollector submitNodeCollector,
			int lightCoords,
			float inverseArmHeight,
			float attackValue,
			HumanoidArm arm, PlayerRenderState playerRenderState, CallbackInfo info) {
//		AvatarRenderer<AbstractClientPlayer> renderer =
//			    Minecraft.getInstance()
//			        .getEntityRenderDispatcher()
//			        .getPlayerRenderer(Minecraft.getInstance().player);
//
//			PlayerModel model = renderer.getModel();
//
//		poseStack.pushPose();
//		model.leftArm.translateAndRotate(poseStack);
//		model.leftArm.resetPose();
//		model.leftArm.visible = true;
//
//		model.leftArm.xRot = 0;
//		model.leftArm.yRot = 0;
//		model.leftArm.zRot = 0;
//		submitNodeCollector.submitModelPart(
//		    model.leftArm,
//		    poseStack,
//		    RenderTypes.entityTranslucent(Minecraft.getInstance().player.getSkin().body().texturePath()),
//		    lightCoords,
//		    OverlayTexture.NO_OVERLAY,
//		    null
//		);
//
//		poseStack.popPose();

//		boolean isRightArm = arm != HumanoidArm.LEFT;
//		float invert = !isRightArm ? 1.0F : -1.0F;
//		float sqrtAttackValue = Mth.sqrt(attackValue);
//		float xSwingPosition = -0.4F * Mth.sin(sqrtAttackValue * (float)Math.PI);
//		float ySwingPosition =  0.2F * Mth.sin(sqrtAttackValue * (float)(Math.PI * 2.0));
//		float zSwingPosition = -0.2F * Mth.sin(attackValue * (float)Math.PI);
//		poseStack.translate(invert * (xSwingPosition + 0.56F), ySwingPosition + -0.52F + inverseArmHeight * -0.52F, zSwingPosition + -0.72F);
//		poseStack.mulPose(Axis.YP.rotationDegrees(invert * 45.0F));
//		float zSwingRotation = Mth.sin(attackValue * attackValue * (float) Math.PI);
//		float ySwingRotation = Mth.sin(sqrtAttackValue * (float) Math.PI);
//		poseStack.mulPose(Axis.YP.rotationDegrees(invert * ySwingRotation * 80.0F));
//		poseStack.mulPose(Axis.ZP.rotationDegrees(invert * zSwingRotation * -20.0F));
//		AbstractClientPlayer player = Minecraft.getInstance().player;
//		poseStack.translate(invert * -1.0F, 3.6F, 3.5F);
//		poseStack.mulPose(Axis.ZP.rotationDegrees(invert * -120.0F));
////		poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
//		poseStack.mulPose(Axis.YP.rotationDegrees(invert * -135.0F));
//		poseStack.translate(-invert * 5.6F, 0.0F, 3.5F);
//		AvatarRenderer<AbstractClientPlayer> avatarRenderer = Minecraft.getInstance()
//		        .getEntityRenderDispatcher()
//		        .getPlayerRenderer(Minecraft.getInstance().player);
//		Identifier skinTexture = player.getSkin().body().texturePath();
//
//		PlayerModel model = avatarRenderer.getModel();
//		if (!isRightArm) {
//			model.rightArm.resetPose();
//			model.rightArm.visible = true;
//			model.leftSleeve.visible = player.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
//			model.rightSleeve.visible = player.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
//			model.leftArm.zRot = 0.1F;
//			model.rightArm.zRot = -0.1F;
//			submitNodeCollector.submitModelPart(model.rightArm, poseStack, RenderTypes.entityTranslucent(skinTexture), lightCoords, OverlayTexture.NO_OVERLAY, null);
//		} else {
//			model.leftArm.resetPose();
//			model.leftArm.visible = true;
//			model.leftSleeve.visible = player.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
//			model.rightSleeve.visible = player.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
//			model.leftArm.zRot = 0.1F;
//			model.rightArm.zRot = -0.1F;
//			submitNodeCollector.submitModelPart(model.leftArm, poseStack, RenderTypes.entityTranslucent(skinTexture), lightCoords, OverlayTexture.NO_OVERLAY, null);
//		}


		AvatarRenderer<AbstractClientPlayer> renderer = (AvatarRenderer<AbstractClientPlayer>) Minecraft.getInstance()
		        .getEntityRenderDispatcher()
		        .getRenderer(Minecraft.getInstance().player);

		PlayerModel model = renderer.getModel();

		ModelPart armM = model.leftArm;
		Identifier skinTexture = Minecraft.getInstance().player.getSkin().body().texturePath();

		armM.resetPose();
		armM.visible = true;
		float oldX = armM.x;
		float oldY = armM.y;
		float oldZ = armM.z;
		float oldXRot = armM.xRot;
		float oldYRot = armM.yRot;
		float oldZRot = armM.zRot;

//		armM.x = 0;
//		armM.y = 0;
//		armM.z = 0;

		poseStack.translate(0, 2.0F / 16.0F, 0.0F);

		poseStack.pushPose();
		float swing = attackValue;

		float sin = Mth.sin(Mth.sqrt(swing) * (float)Math.PI);
		float sin2 = Mth.sin(Mth.sqrt(swing) * (float)(Math.PI * 2.0));
		float z = Mth.sin(swing * (float)Math.PI);

		poseStack.translate(
		    -sin * 0.4F,
		     sin2 * 0.2F,
		    -z * 0.2F
		);

		poseStack.translate(0.56F, -0.52F, -0.72F);
		poseStack.rotateDegrees(Axis.YP,(sin * 80.0F));
		poseStack.rotateDegrees(Axis.XP,(-Mth.sin(swing * swing * (float)Math.PI) * 20.0F));


		poseStack.rotateDegrees(Axis.YN,(135.0F));

		// Classic transform

		poseStack.scale(1.0F, -1.0F, -1.0F);
		poseStack.translate(0.0F, 0.2F, 0.0F);
		poseStack.rotateDegrees(Axis.ZP,(120));

		submitNodeCollector.submitModelPart(
		    armM,
		    poseStack,
		    RenderTypes.entityTranslucent(skinTexture),
		    lightCoords,
		    OverlayTexture.NO_OVERLAY,
		    null
		);


		poseStack.popPose();

		armM.x = oldX;
		armM.y = oldY;
		armM.z = oldZ;
		armM.xRot = oldXRot;
		armM.yRot = oldYRot;
		armM.zRot = oldZRot;
		info.cancel();
	}
}
