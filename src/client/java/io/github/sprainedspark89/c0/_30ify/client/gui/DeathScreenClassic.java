package io.github.sprainedspark89.c0._30ify.client.gui;

//import org.lwjgl.opengl.GL11; // why can this still be imported?

//import com.mojang.realmsclient.RealmsMainScreen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class DeathScreenClassic extends Screen {
	public DeathScreenClassic(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
	}

//	protected final void buttonClicked(Button button) {
//		if(button.id == 0) {
//			this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
//		}
//
//		if(button.id == 1) {
//			this.minecraft.setScreen(new NewLevelScreen(this));
//		}
//
//		if(this.minecraft.user != null && button.id == 2) {
//			this.minecraft.setScreen(new LoadLevelScreen(this));
//		}
//
//	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
		graphics.pose().pushMatrix();
		graphics.pose().scale(2F);
		graphics.centeredText(this.font, "Game over!", this.width / 2 / 2, 30, 0xFFFFFFFF);
		graphics.pose().popMatrix();
		graphics.centeredText(this.font, "Score: §e" + this.minecraft.player.getScore(), this.width / 2, 100, 0xFFFFFFFF);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}

	@Override
	public final void init() {
//		this.buttons.clear();
		Button gen = this
				.addRenderableWidget(Button
						.builder(Component.literal("Generate new level..."),
								var1 ->
									this.minecraft.gui
									.setScreen(new NewLevelScreen(this)
									))
						.bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build());
//		this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 72, "Generate new level..."));
		Button load = this
				.addRenderableWidget(Button
						.builder(Component.literal("Load level.."),
								var1 ->
									this.minecraft.player.respawn()
									)
						.bounds(this.width / 2 - 100, this.height / 4 + 96, 200, 20).build());
//		this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 96, "Load level.."));
		if(this.minecraft.getUser() == null) {
			load.active = false;
		}

	}
}