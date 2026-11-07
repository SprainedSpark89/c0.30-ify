package io.github.sprainedspark89.c0._30ify.client.gui; // from indev!

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class GuiMainTitle extends Screen {
	private static final Identifier DIRT = Identifier.withDefaultNamespace("textures/gui/menu_background.png"); // texture
																												// packs
																												// like
																												// programmer
																												// art
																												// might
																												// make
																												// this
																												// dirt?

	private static final Identifier LOGO = Identifier.withDefaultNamespace("textures/gui/title/minecraft.png"); // will
																												// replace
																												// with
																												// either
																												// the
																												// indev
																												// or
																												// one
																												// of
																												// the
																												// old
																												// logos
																												// that
																												// notch
																												// posted
																												// before
																												// the
																												// titlescreen
																												// was
																												// added

	@Override
	public boolean shouldCloseOnEsc() {
		this.minecraft.gui.setScreen(new TitleScreen());
		return false;
	}
	
	public GuiMainTitle(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	private float updateCounter = 0.0F;

	@Override
	public void tick() {
		this.updateCounter += 0.01F;
	}

	// should implement something like this to actually escape the fake titlescreen
	// protected final void keyTyped(char c1, int i2) {
	// }

	@Override
	protected void init() {
		// this.controlList.clear();
		// TODO make it open the actual create world screen
		// below is how the world selection screen does it
		// this.playWorldButton =
		// rowHelper.addChild(Button.builder(LevelSummary.PLAY_WORLD, button ->
		// list.getSelectedOpt().ifPresent(joinWorld)).build(), 2);
		// rowHelper.addChild(
		// Button.builder(Component.translatable("selectWorld.create"), button ->
		// CreateWorldScreen.openFresh(this.minecraft, list::returnToScreen)).build(), 2
		// );
		Button newLevel = this.addRenderableWidget(Button
				.builder(Component.literal("Generate new level..."),
						var1 -> this.minecraft.gui.setScreen(new NewLevelScreen(this)
							))
				.bounds(this.width / 2 - 100, this.height / 4 + 48, 200, 20).build());
		// this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 +
		// 48, "Generate new level..."));
		Button loadLevel = this.addRenderableWidget(Button
				.builder(Component.literal("Load level.."),
						var1 -> this.minecraft.gui.setScreen(new LoadLevelScreen(this)))
				.bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build());

		// this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 +
		// 72, "Load level.."));
		// need to make options between realms and normal multiplayer
		Button tutorial = this.addRenderableWidget(Button
				.builder(Component.literal("Connect to level..."),
						var1 -> this.minecraft.gui.setScreen(new MultiplayerScreenClassicStyle(this)))
				.bounds(this.width / 2 - 100, this.height / 4 + 96, 200, 20).build());
		// tutorial.active = false;
		// this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 +
		// 96, "Play tutorial level"));
		Button options = this
				.addRenderableWidget(Button
						.builder(Component.literal("Options..."),
								var1 -> this.minecraft.gui
										.setScreen(new OptionsScreen(this, this.minecraft.options)))
						.bounds(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20).build());
		// this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 +
		// 120 + 12, "Options..."));
		// ((GuiButton)this.controlList.get(2)).enabled = false;
//		tutorial.active = this.minecraft.allowsMultiplayer() && !this.minecraft.isNameBanned();
		if (this.minecraft.getUser() == null) {
			loadLevel.active = false;
			tutorial.active = false;
		}

	}

	// protected final void actionPerformed(GuiButton guiButton1) {
	// if(guiButton1.id == 0) {
	// this.mc.displayGuiScreen(new GuiOptions(this, this.mc.options));
	// }
	//
	// if(guiButton1.id == 1) {
	// this.mc.displayGuiScreen(new GuiNewLevel(this));
	// }
	//
	// if(this.mc.session != null && guiButton1.id == 2) {
	// this.mc.displayGuiScreen(new GuiLoadLevel(this));
	// }
	//
	// }
	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
			final float a) {
		// GL11.glDisable(GL11.GL_LIGHTING);
		// GL11.glDisable(GL11.GL_FOG);
		// Tessellator tessellator3 = Tessellator.instance; // there's no more
		// tessellator
		// GL11.glBindTexture(GL11.GL_TEXTURE_2D,
		// this.mc.renderEngine.getTexture("/dirt.png")); // likely should change to
		// /assets/minecraft/textures/gui/menu_background.png
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float u = this.width / 32, 
				v = this.height / 32 + updateCounter;
		graphics.blit(RenderPipelines.GUI_TEXTURED, DIRT, 0, 0, u, v, this.width, this.height,
				32, 32, 0xFF404040);
		// tessellator3.startDrawingQuads();
		// tessellator3.setColorOpaque_I(4210752);
		// tessellator3.addVertexWithUV(0.0F, (float)this.height, 0.0F, 0.0F,
		// (float)this.height / 32.0F + this.updateCounter);
		// tessellator3.addVertexWithUV((float)this.width, (float)this.height, 0.0F,
		// (float)this.width / 32.0F, (float)this.height / 32.0F + this.updateCounter);
		// tessellator3.addVertexWithUV((float)this.width, 0.0F, 0.0F, (float)this.width
		// / 32.0F, 0.0F + this.updateCounter);
		// tessellator3.addVertexWithUV(0.0F, 0.0F, 0.0F, 0.0F, 0.0F +
		// this.updateCounter);
		// tessellator3.draw();
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
			final float a) {
		// logo
		// GL11.glBindTexture(GL11.GL_TEXTURE_2D,
		// this.mc.renderEngine.getTexture("/gui/logo.png")); // likely should change to
		// /assets/minecraft/textures/gui/title/minecraft.png
		// GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// tessellator3.setColorOpaque_I(0xFFFFFF);
		// graphics.fillGradient((this.width - 256) / 2, 30, 0, 0, 256, 49);
		// original 256x256, modern 1024x256, want to use 753x143, originates from
		// minecraft.net in 2009
		graphics.blit(RenderPipelines.GUI_TEXTURED, LOGO, (this.width - 256) / 2, 30, 0.0F, 0.0F, 256, 256 * 143 / 753, 256, 256 * 143 / 753);
		// this.drawTexturedModalRect((this.width - 256) / 2, 30, 0, 0, 256, 49);
		graphics.pose().pushMatrix();
		// GL11.glPushMatrix();
		// splash
		graphics.pose().translate((float) (this.width / 2 + 90), 70.0F);
		// GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
		graphics.pose().rotate(-20F * Mth.DEG_TO_RAD);
		graphics.pose().scale(1.8F - Mth
				.abs(Mth.sin((float) (System.currentTimeMillis() % 1000L) / 1000.0F * (float) Math.PI * 2.0F) * 0.1F));
		// GL11.glScalef(f4 = 1.8F -
		// MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) /
		// 1000.0F * (float)Math.PI * 2.0F) * 0.1F), f4, f4);
		graphics.centeredText(font, "Pre-beta!", 0, -8, 0xFFFFFF00);
		// drawCenteredString(this.fontRenderer, "Pre-beta!", 0, -8, 16776960);
		graphics.pose().popMatrix();
		// GL11.glPopMatrix();
		// copyright
		String string5 = "Copyright Mojang Specifications. Do not distribute.";
		graphics.text(font, string5, this.width - this.font.width(string5) - 2, this.height - 10, 0xFFFFFFFF);
		// b(this.fontRenderer, string5, this.width -
		// this.fontRenderer.getStringWidth(string5) - 2, this.height - 10, 0xFFFFFF);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}
}
