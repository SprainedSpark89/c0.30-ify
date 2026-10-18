package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;

@Mixin(ArrowRenderer.class)
public interface ArrowRendererAccessor {
    @Invoker("getTextureLocation")
    Identifier c030ify$getTextureLocation(ArrowRenderState state);
}
