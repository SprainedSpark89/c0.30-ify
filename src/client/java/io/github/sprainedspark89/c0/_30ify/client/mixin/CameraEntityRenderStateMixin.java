package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import io.github.sprainedspark89.c0._30ify.client.accessor.CameraEntityRenderStateAccessor;
import net.minecraft.client.renderer.state.level.CameraEntityRenderState;

@Mixin(CameraEntityRenderState.class)
public class CameraEntityRenderStateMixin implements CameraEntityRenderStateAccessor {
	@Unique
	private float classicTilt;
	
	@Override
	public float getClassicTilt() {
		return classicTilt;
	}

	@Override
	public void setClassicTilt(float classicTilt) {
		this.classicTilt = classicTilt;
	}
	
	
}
