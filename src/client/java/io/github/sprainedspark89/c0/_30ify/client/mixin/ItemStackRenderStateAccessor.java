package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;

@Mixin(ItemStackRenderState.class)
public interface ItemStackRenderStateAccessor {

    @Accessor("displayContext")
    void c030$setDisplayContext(ItemDisplayContext context);

}