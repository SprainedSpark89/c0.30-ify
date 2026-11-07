package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import io.github.sprainedspark89.c0._30ify.client.accessor.LivingEntityRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements LivingEntityRenderStateAccessor {

	@Unique
	float hurtDir;
	@Unique
	float hurtTime;
	@Unique
	int hurtDuration;

	@Override
	public float getHurtDir() {
		return hurtDir;
	}
	@Override
	public void setHurtDir(float hurtDir) {
		this.hurtDir = hurtDir;
	}
	@Override
	public float getHurtTime() {
		return hurtTime;
	}
	@Override
	public void setHurtTime(float hurtTime) {
		this.hurtTime = hurtTime;
	}
	@Override
	public int getHurtDuration() {
		return hurtDuration;
	}
	@Override
	public void setHurtDuration(int hurtDuration) {
		this.hurtDuration = hurtDuration;
	}
}
