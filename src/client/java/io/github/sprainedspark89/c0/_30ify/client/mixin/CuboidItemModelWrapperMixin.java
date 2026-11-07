package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.sprainedspark89.c0._30ify.client.util.ClassicItemModel;
import io.github.sprainedspark89.c0._30ify.client.util.ClassicItemModels;
import io.github.sprainedspark89.c0._30ify.client.util.InternerImpl;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.cuboid.MissingCuboidModel;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.resources.Identifier;

@Mixin(CuboidItemModelWrapper.Unbaked.class)
public abstract class CuboidItemModelWrapperMixin {

    @Shadow
    public abstract Identifier model();

    @Redirect(
        method = "bake",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/resources/model/ModelBaker;getModel(Lnet/minecraft/resources/Identifier;)Lnet/minecraft/client/resources/model/ResolvedModel;"
        )
    )
    private ResolvedModel c030$useBlockModels(
            ModelBaker baker,
            Identifier originalModel) {

        Identifier id = originalModel;

        if ("item".equals(id.getPath().split("/")[0])) {
            Identifier blockId = id.withPath(
                    id.getPath().replaceFirst("^item/", "block/"));
            ResolvedModel rM = baker.getModel(blockId);
            if(!rM.debugName().contentEquals(MissingCuboidModel.LOCATION.toString())) {
            	return baker.getModel(blockId);
            }
        }

        return baker.getModel(originalModel);
    }

    @Inject(
    	    method = "bake",
    	    at = @At("RETURN")
    	)
    	private void c030$cacheClassicModel(
    	        ItemModel.BakingContext context,
    	        Matrix4fc transformation,
    	        CallbackInfoReturnable<ItemModel> cir) {

    	ModelBaker baker = context.blockModelBaker();
    	ResolvedModel resolved = baker.getModel(this.model());
    	TextureSlots slots = resolved.getTopTextureSlots();

    	TextureAtlasSprite sprite =
    	    resolved.resolveParticleMaterial(slots, baker).sprite();

    	ClassicItemModels.MODELS.put(
    	    this.model().withPath(this.model().getPath().replaceFirst("item/", "").replaceFirst("block/", "")),
    	    new ClassicItemModel(sprite, new InternerImpl())
    	);
        }
}