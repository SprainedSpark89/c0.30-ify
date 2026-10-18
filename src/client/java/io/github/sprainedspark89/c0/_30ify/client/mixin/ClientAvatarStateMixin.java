package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import io.github.sprainedspark89.c0._30ify.client.accessor.AvatarStateAccessor;
import net.minecraft.client.entity.ClientAvatarState;
import net.minecraft.util.Mth;

@Mixin(ClientAvatarState.class)
public class ClientAvatarStateMixin implements AvatarStateAccessor {
	@Unique
	private float classicTilt;

	@Unique
	private float classicTiltO;
	
	@Override
	public float getInterpolatedClassicTilt(float partialTick) {
	    return Mth.lerp(partialTick, classicTiltO, getClassicTilt());
	}
	
	@Override
	public void updateTilt(final float tTilt) {
		this.classicTiltO = this.getClassicTilt();
		this.setClassicTilt(this.getClassicTilt() + (tTilt - this.getClassicTilt()) * 0.8F);
	}

	@Override
	public float getClassicTilt() {
		return classicTilt;
	}

	@Override
	public void setClassicTilt(float classicTilt) {
		this.classicTilt = classicTilt;
	}
}
