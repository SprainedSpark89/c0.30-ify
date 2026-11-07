package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import io.github.sprainedspark89.c0._30ify.client.accessor.ModelManagerAccessor;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.resources.model.ModelManager;

@Mixin(ModelManager.class)
public interface ModelManagerMixin extends ModelManagerAccessor {
	@Override
	@Accessor("blockStateModelSet")
    BlockStateModelSet c030$getBlockStateModels();
}
