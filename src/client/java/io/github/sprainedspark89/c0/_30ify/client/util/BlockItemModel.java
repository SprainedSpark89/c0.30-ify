package io.github.sprainedspark89.c0._30ify.client.util;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class BlockItemModel implements ItemModel {

    private final BlockStateModel blockModel;

    public BlockItemModel(BlockStateModel model) {
        this.blockModel = model;
    }


    @Override
    public void update(
        ItemStackRenderState output,
        ItemStack stack,
        ItemModelResolver resolver,
        ItemDisplayContext context,
        ClientLevel level,
        ItemOwner owner,
        int seed
    ) {
        output.appendModelIdentityElement(this);
    }
}