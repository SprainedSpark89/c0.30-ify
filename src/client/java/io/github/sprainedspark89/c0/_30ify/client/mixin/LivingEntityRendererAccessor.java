package io.github.sprainedspark89.c0._30ify.client.mixin;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntityRenderer.class)
public interface LivingEntityRendererAccessor {

    @Invoker("getWhiteOverlayProgress")
    float c030$getWhiteOverlayProgress(LivingEntityRenderState state);

}