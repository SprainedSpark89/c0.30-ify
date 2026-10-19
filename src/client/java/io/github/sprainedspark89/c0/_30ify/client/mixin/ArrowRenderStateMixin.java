package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import io.github.sprainedspark89.c0._30ify.client.accessor.ArrowRenderStateAccessor;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;

@Mixin(ArrowRenderState.class)
public abstract class ArrowRenderStateMixin implements ArrowRenderStateAccessor {
    @Unique
    private boolean c030ify$canBePickedUp;

    @Override
    public boolean c030ify$canBePickedUp() {
        return this.c030ify$canBePickedUp;
    }

    @Override
    public void c030ify$setCanBePickedUp(boolean canBePickedUp) {
        this.c030ify$canBePickedUp = canBePickedUp;
    }
}