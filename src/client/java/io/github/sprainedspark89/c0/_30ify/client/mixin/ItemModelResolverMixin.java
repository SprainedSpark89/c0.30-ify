package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.util.ClassicItemModel;
import io.github.sprainedspark89.c0._30ify.client.util.ClassicItemModels;
import io.github.sprainedspark89.c0._30ify.client.util.InternerImpl;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState.LayerRenderState;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemModelResolver.class)
public class ItemModelResolverMixin {

	@Inject(method = "updateForNonLiving", at = @At("HEAD"), cancellable = true)
	private void c030$replaceModel(ItemStackRenderState output, ItemStack item, ItemDisplayContext displayContext,
			Entity entity, CallbackInfo ci) {
		Identifier id = item.get(DataComponents.ITEM_MODEL);
		if(id != null) {
			ClassicItemModel model =
			        ClassicItemModels.MODELS.get(id);
			if(model == null) {
//				System.out.println(id + " is not in the classic models!");
//				System.out.println("Avalible options:");
//				for(Identifier ident : ClassicItemModels.MODELS.keySet()) {
//					System.out.println(ident + " : " + ClassicItemModels.MODELS.get(ident).toString());
//				}
			} else {
				model.update(output, item, (ItemModelResolver) (Object) this, displayContext,
					null, null, 0);
	
				ci.cancel();
			}
		}
	}

}
