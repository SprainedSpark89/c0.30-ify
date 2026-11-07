package io.github.sprainedspark89.c0._30ify.client.accessor;

import net.minecraft.client.DeltaTracker;

public interface AvatarStateAccessor {
    void updateTilt(float targetTilt);

    float getInterpolatedClassicTilt(DeltaTracker deltaTracker);

	void setClassicTilt(float interpolatedClassicTilt);

	float getClassicTilt();
}
