package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen.Reason;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.LevelLoadTracker;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin extends Screen {

	protected LevelLoadingScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Shadow
	@Final
	private static Component DOWNLOADING_TERRAIN_TEXT;

	@Shadow
	private float smoothedProgress;

	private static final Identifier DIRT = Identifier.withDefaultNamespace("textures/gui/menu_background.png"); // texture
	// packs
	// like
	// programmer
	// art
	// might
	// make
	// this
	// dirt?

	@Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
	private void c0_30ify$renderClassicLoadingScreen(GuiGraphicsExtractor graphics, int mouseX, int mouseY,
			float partialTick, CallbackInfo ci) {
		ScreenAccessor screen = (ScreenAccessor) (Object) this;

		int width = screen.c0_30ify$getWidth();
		int height = screen.c0_30ify$getHeight();
		int xCenter = width / 2;
		int yCenter = height / 2;

		Font font = screen.c0_30ify$getFont();
		String lastWord = this.titleCase(this.DOWNLOADING_TERRAIN_TEXT.getString().split(" ")[this.DOWNLOADING_TERRAIN_TEXT.getString().split(" ").length - 1]);
		graphics.centeredText(font, lastWord, xCenter, yCenter - 20, -1);

		graphics.centeredText(font, DOWNLOADING_TERRAIN_TEXT, xCenter, yCenter + 4, -1);

		float progress = smoothedProgress;

		int barLeft = xCenter - 50;
		int barTop = yCenter + 16;

		graphics.fill(barLeft, barTop, barLeft + 100, barTop + 2, 0xFF808080);
		graphics.fill(barLeft, barTop, barLeft + Math.round(Mth.clamp(progress, 0.0F, 1.0F) * 100), barTop + 2,
				0xFF80FF80);

		ci.cancel();
	}

	@Overwrite
	public void extractBackground(final GuiGraphicsExtractor graphics, final int
			mouseX, final int mouseY, final float a) {
		float u = this.width / 32,
				v = this.height / 32;
		graphics.blit(RenderPipelines.GUI_TEXTURED, DIRT, 0, 0, u, v, this.width,
				this.height,
				32, 32, 0xFF404040);
	}
	
	public String titleCase(String s) {
		String lower = s.toLowerCase();
		String upper = s.toUpperCase();
		StringBuilder builder = new StringBuilder(lower);
		builder.setCharAt(0, upper.charAt(0));
		return builder.toString();
	}
}