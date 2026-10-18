package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
	@Unique
	private static final Identifier DIRT = Identifier.withDefaultNamespace("textures/gui/menu_background.png");

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void c030ify$renderInfdevLoadingScreen(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a,
			CallbackInfo ci) {
		if (!C030IfyClient.classicGui) { // useless until bug is fixed
			return;
		}

		int width = graphics.guiWidth();
		int height = graphics.guiHeight();
		
		graphics.blit(RenderPipelines.GUI_TEXTURED, DIRT, 0, 0, width / 32, height / 32, width, height, 32, 32,
				0xFF404040);

		graphics.text(Minecraft.getInstance().font, "Loading...", 8, height - 16, 0xFFFFFFFF);
	}
}