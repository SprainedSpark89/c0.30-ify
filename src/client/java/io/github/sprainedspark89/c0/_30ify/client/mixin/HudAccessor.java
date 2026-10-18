package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@Mixin(Hud.class)
public interface HudAccessor {
    @Invoker("extractAirBubbles")
    void c030ify$extractAirBubbles(
        GuiGraphicsExtractor graphics,
        Player player,
        int vehicleHearts,
        int yLineAir,
        int xRight
    );
    
    @Invoker("getVehicleMaxHearts")
    int c030ify$getVehicleMaxHearts(final @Nullable LivingEntity vehicle);
}