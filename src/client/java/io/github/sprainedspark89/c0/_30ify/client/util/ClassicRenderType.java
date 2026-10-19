package io.github.sprainedspark89.c0._30ify.client.util;

import java.util.function.Function;

import com.mojang.blaze3d.PrimitiveTopology;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.minecraft.client.renderer.BindGroupLayouts;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

public class ClassicRenderType {
	private static final RenderPipeline.Snippet GLOBALS_SNIPPET = RenderPipeline.builder().withBindGroupLayout(BindGroupLayouts.GLOBALS).buildSnippet();
	private static final RenderPipeline.Snippet MATRICES_FOG_SNIPPET = RenderPipeline.builder(GLOBALS_SNIPPET)
			.withBindGroupLayout(BindGroupLayouts.MATRICES_PROJECTION)
			.withBindGroupLayout(BindGroupLayouts.FOG)
			.buildSnippet();
	private static final RenderPipeline.Snippet MATRICES_FOG_LIGHT_DIR_SNIPPET = RenderPipeline.builder(MATRICES_FOG_SNIPPET)
			.withBindGroupLayout(BindGroupLayouts.LIGHTING)
			.buildSnippet();
	private static final RenderPipeline.Snippet ITEM_WHITE_SNIPPET =
		    RenderPipeline.builder(MATRICES_FOG_LIGHT_DIR_SNIPPET)
		        .withVertexShader("core/item")
		        .withFragmentShader("core/item_white")
		        .withBindGroupLayout(BindGroupLayouts.SAMPLER0_SAMPLER1_SAMPLER2)
		        .withVertexBinding(0, DefaultVertexFormat.ENTITY)
		        .withPrimitiveTopology(PrimitiveTopology.QUADS)
		        .withColorTargetState(new ColorTargetState(BlendFunction.ADDITIVE))
		        .withShaderDefine("NO_TEXTURE")
		        .buildSnippet();
	
	public static final RenderPipeline ITEM_WHITE = RenderPipelines.register(
		    RenderPipeline.builder(ITEM_WHITE_SNIPPET)
		        .withLocation("pipeline/item_white")
		        .build()
		);
	
	private static final Function<Identifier, RenderType> WHITE_ITEM = Util.memoize(
			texture -> {
				RenderSetup state = RenderSetup.builder(ITEM_WHITE)
					.withTexture("Sampler0", texture)
					.useLightmap()
					.useOverlay()
					.affectsCrumbling()
					.setOutline(RenderSetup.OutlineProperty.AFFECTS_OUTLINE)
					.createRenderSetup();
				return RenderType.create("item_white", state);
			}
		);
	
	public static RenderType whiteItem(final Identifier texture) {
		return WHITE_ITEM.apply(texture);
	}
	
	private static final RenderType WHITE_BLOCK_ITEM_SHEET = whiteItem(TextureAtlas.LOCATION_BLOCKS);
	
	public static RenderType whiteBlockItemSheet() {
		return WHITE_BLOCK_ITEM_SHEET;
	}
}
