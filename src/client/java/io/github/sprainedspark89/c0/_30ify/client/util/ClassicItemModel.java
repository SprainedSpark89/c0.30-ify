package io.github.sprainedspark89.c0._30ify.client.util;

import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import com.mojang.math.Quadrant;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.dispatch.BlockModelRotation;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.cuboid.CuboidFace;
import net.minecraft.client.resources.model.cuboid.FaceBakery;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.geometry.ItemQuads;
import net.minecraft.client.resources.model.geometry.QuadCollection;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public final class ClassicItemModel implements ItemModel {
	private final QuadCollection quads;
	private final QuadCollection whiteQuads = null;

	public ClassicItemModel(TextureAtlasSprite sprite, ModelBaker.Interner interner) {
		this.quads = build(sprite, interner, new BakedQuad.MaterialInfo(
			    sprite,
			    ChunkSectionLayer.CUTOUT,
			    Sheets.cutoutBlockItemSheet(),
			    Sheets.cutoutBlockItemGlintSheet(),
			    Sheets.cutoutBlockItemGlintSpecialSheet(),
			    -1,
			    null,
			    0
			));
//	    this.whiteQuads = build(sprite, interner, new BakedQuad.MaterialInfo(
//		        sprite,
//		        ChunkSectionLayer.CUTOUT,
//		        ClassicRenderType.whiteBlockItemSheet(),
//		        -1,
//		        false,
//		        0
//		    ));
	}

	@Override
	public void update(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver,
			ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
		// TODO Auto-generated method stub
		output.appendModelIdentityElement(this);

		ItemStackRenderState.LayerRenderState layer = output.newLayer();

		if (item.hasFoil()) {
		    layer.setFoilType(ItemStackRenderState.FoilType.STANDARD);
		}

		layer.setQuads(ItemQuads.split(this.quads.getAll()));

//		ItemStackRenderState.LayerRenderState whiteLayer = output.newLayer();
//
//		whiteLayer.prepareQuadList().addAll(this.whiteQuads.getAll());
	}

	private static QuadCollection build(
	        TextureAtlasSprite sprite,
	        ModelBaker.Interner interner,
	        BakedQuad.MaterialInfo materialInfo) {
		Vector3f from = new Vector3f(6,6,6);
		Vector3f to   = new Vector3f(10,10,10);
								CuboidFace.UVs uvs = new CuboidFace.UVs(
									    0.0F,
									    0.0F,
									    8.0F,
									    8.0F
									);

		QuadCollection.Builder builder = new QuadCollection.Builder();
		for(Direction dir : Direction.values()) {
			builder.addCulledFace(dir, FaceBakery.bakeQuad(
			    interner,
			    from,
			    to,
			    uvs,
			    Quadrant.R0,
			    materialInfo,
			    dir,
			    BlockModelRotation.IDENTITY,
			    null
			));
		}

		return builder.build();
	}

}