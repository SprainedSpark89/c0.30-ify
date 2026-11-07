package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

@Mixin(GenericMessageScreen.class)
public abstract class GenericMessageScreenMixin extends Screen {

	private static final Identifier DIRT = Identifier.withDefaultNamespace("textures/gui/menu_background.png"); // texture
	// packs
	// like
	// programmer
	// art
	// might
	// make
	// this
	// dirt?

	protected GenericMessageScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Overwrite
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		float u = this.width / 32,
				v = this.height / 32;
		graphics.blit(RenderPipelines.GUI_TEXTURED, DIRT, 0, 0, u, v, this.width, this.height,
				32, 32, 0xFF404040);
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
			final float a) {
		int i8 = this.width * 240 / this.height;
		int i3 = this.height * 240 / this.height;
		String lastWord = this.titleCase(this.title.getString().split(" ")[this.title.getString().split(" ").length - 1]);
		graphics.text(font, lastWord, (i8 - this.minecraft.font.width(lastWord)) / 2, i3 / 2 - 4 - 16, 0xFFFFFFFF);


		graphics.text(font, this.title, (i8 - this.font.width(this.title)) / 2, i3 / 2 - 4 + 8, 0xFFFFFFFF);
	}

	@Override
	@Overwrite
	protected void repositionElements() {
	}

	public String titleCase(String s) {
		String lower = s.toLowerCase();
		String upper = s.toUpperCase();
		StringBuilder builder = new StringBuilder(lower);
		builder.setCharAt(0, upper.charAt(0));
		return builder.toString();
	}
}
