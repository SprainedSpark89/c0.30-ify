package io.github.sprainedspark89.c0._30ify.client.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.SpriteId;

@Mixin(AtlasManager.class)
public interface AtlasManagerAccessor {
	@Accessor("spriteLookup") 
	Map<SpriteId, TextureAtlasSprite> getSpriteLookup();
	
	
}
