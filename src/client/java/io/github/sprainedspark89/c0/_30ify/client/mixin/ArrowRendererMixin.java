package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.accessor.ArrowRenderStateAccessor;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;

@Mixin(ArrowRenderer.class)
public abstract class ArrowRendererMixin<
        T extends AbstractArrow,
        S extends ArrowRenderState> {

    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void c030ify$capturePickupState(
            T entity,
            S state,
            float partialTicks,
            CallbackInfo ci
    ) {
        ((ArrowRenderStateAccessor) state).c030ify$setCanBePickedUp(
                entity.pickup != AbstractArrow.Pickup.DISALLOWED
        );
    }

    @Redirect(
            method = "submit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/ArrowRenderer;getTextureLocation(Lnet/minecraft/client/renderer/entity/state/ArrowRenderState;)Lnet/minecraft/resources/Identifier;"
            )
    )
    private Identifier c030ify$selectArrowTexture(
            ArrowRenderer<T, S> renderer,
            S state
    ) {
        if (((ArrowRenderStateAccessor) state).c030ify$canBePickedUp()) {
        	return ((ArrowRendererAccessor) (Object) renderer)
        		    .c030ify$getTextureLocation(state);
        }

        return Identifier.withDefaultNamespace(
                "textures/entity/projectiles/arrow_stuck.png"
        );
    }
}