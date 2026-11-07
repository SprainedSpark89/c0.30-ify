package io.github.sprainedspark89.c0._30ify.client.accessor;

public interface LivingEntityRenderStateAccessor {

	float getHurtDir();

	int getHurtDuration();

	float getHurtTime();

	void setHurtDir(float hurtDir);

	void setHurtDuration(int hurtDuration);

	void setHurtTime(float hurtTime);

}