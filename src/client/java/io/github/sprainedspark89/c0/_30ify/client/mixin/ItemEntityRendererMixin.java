package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity, ItemEntityRenderState> {
	
	@Shadow
	private RandomSource random;
	
	@Unique
	public float c030$rot;
	
	protected ItemEntityRendererMixin(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
//	@Inject(method = "extractRenderState", at = @At("TAIL"))
//	public void extractRenderState(final ItemEntity entity, final ItemEntityRenderState state, final float partialTicks, CallbackInfo i) {
//		float rot = this.c030$rot + state.ageInTicks * 3.0F;
//
//		float alpha = Mth.sin(rot / 10.0F);
//		alpha = alpha * 0.5F + 0.5F;
//		alpha *= alpha * alpha;
//		alpha *= 0.4F;
//
//		((ItemEntityRenderStateAccessor) state).c030$setWhiteAlpha(alpha);
//	}

	@Overwrite
	public void submit(final ItemEntityRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
		if (!state.item.isEmpty()) {
			float rot = this.c030$rot + state.ageInTicks * 3.0F;
			float bob = Mth.sin(rot / 10.0F) * 0.1F + 0.1F;
//			float spin =
//				    c030$rot +
//				    state.ageInTicks * 3.0F;
			
			poseStack.pushPose();

			poseStack.translate(0.0F, bob + 0.125F, 0.0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(rot));

			submitMultipleFromCount(
			    poseStack,
			    submitNodeCollector,
			    state.lightCoords,
			    state,
			    this.random
			);

			poseStack.popPose();

			super.submit(state, poseStack, submitNodeCollector, camera);
		}
	}
	
	@Overwrite
	public static void submitMultipleFromCount(
			final PoseStack poseStack,
			final SubmitNodeCollector submitNodeCollector,
			final int lightCoords,
			final ItemClusterRenderState state,
			final RandomSource random
		) {
			int amount = state.count;
			if (amount != 0) {
				random.setSeed(state.seed);
				ItemStackRenderState item = state.item;

				


					for (int i = 0; i < amount; i++) {
						poseStack.pushPose();
						
						if(i > 0) {
							float xo = (random.nextFloat() * 2 - 1) * 0.05F;
							float yo = (random.nextFloat() * 2 - 1) * 0.05F;
							float zo = (random.nextFloat() * 2 - 1) * 0.05F;

							poseStack.translate(xo, yo, zo);
						}
						
						item.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
						poseStack.popPose();
				}
					
					
			}
		}
}
