package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import io.github.sprainedspark89.c0._30ify.client.gui.ClassicErrorScreen;
import io.github.sprainedspark89.c0._30ify.client.util.ClassicChunkUpdates;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

	@WrapOperation(
		    method = "run",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/Minecraft;runTick(Z)V"
		    )
		)
		private void c030ify$handleClientTick( // only catches somethings
		    Minecraft instance,
		    boolean renderLevel,
		    Operation<Void> original
		) {
		    try {
		        original.call(instance, renderLevel);
		    } catch (Exception e) {
		        instance.gui.setScreen(new ClassicErrorScreen(
		            "Client error",
		            "The game broke! [" + e + "]",
		            false
		        ));
		        e.printStackTrace();
		    }
		}

	@Inject(
	        method = "renderFrame",
	        at = @At(
	            value = "FIELD",
	            target = "Lnet/minecraft/client/Minecraft;lastTime:J",
	            opcode = org.objectweb.asm.Opcodes.PUTFIELD,
	            shift = At.Shift.AFTER
	        ),
	        require = 1
	    )
	    private void c030ify$updateClassicFps(boolean advanceGameTime, CallbackInfo ci) {
	        ClassicChunkUpdates.chunkUpdates = ClassicChunkUpdates.getAndReset();
	    }
}