package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.sprainedspark89.c0._30ify.client.util.ClassicChunkUpdates;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;

@Mixin(targets = "net.minecraft.client.renderer.chunk.SectionRenderDispatcher$RenderSection$CompileTask")
public abstract class SectionCompileTaskMixin {
    @Inject(
        method = "doTask",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/chunk/SectionCompiler;compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderSectionRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;"
        ),
        require = 1
    )
    private void c030ify$countChunkUpdate(
        net.minecraft.client.renderer.SectionBufferBuilderPack buffers,
        CallbackInfoReturnable<SectionRenderDispatcher.RenderSection.SectionTask.SectionTaskResult> cir
    ) {
        ClassicChunkUpdates.increment();
    }
}