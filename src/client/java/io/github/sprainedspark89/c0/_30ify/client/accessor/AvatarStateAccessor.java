package io.github.sprainedspark89.c0._30ify.client.accessor;

public interface AvatarStateAccessor {
    void updateTilt(float targetTilt);

    float getInterpolatedClassicTilt(float partialTick);

	void setClassicTilt(float interpolatedClassicTilt);

	float getClassicTilt();
}
