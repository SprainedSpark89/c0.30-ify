package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.accessor.ClassicHurtDirection;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity implements ClassicHurtDirection {

    @Unique
    private float c030$hurtDir;

    @Inject(method = "indicateDamage", at = @At("HEAD"))
    private void c030$storeDirection(double xd, double zd, CallbackInfo ci) {
        this.c030$hurtDir =
            (float)(Mth.atan2(zd, xd) * 180.0F / (float)Math.PI
                    - ((LivingEntity)(Object)this).getYRot());
    }

    @Override
    public float c030$getHurtDir() {
        return this.c030$hurtDir;
    }
}