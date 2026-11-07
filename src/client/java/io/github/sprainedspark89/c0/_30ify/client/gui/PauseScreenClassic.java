package io.github.sprainedspark89.c0._30ify.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;

public final class PauseScreenClassic extends Screen {
	public PauseScreenClassic(Component title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
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
//		if(this.minecraft.user != null) {
//			if(button.id == 2) {
//				this.minecraft.setScreen(new SaveLevelScreen(this));
//			}
//
//			if(button.id == 3) {
//				this.minecraft.setScreen(new LoadLevelScreen(this));
//			}
//		}
//
//		if(button.id == 4) {
//			this.minecraft.setScreen((Screen)null);
//			this.minecraft.grabMouse();
//		}
//
//	}
	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		graphics.centeredText(this.font, "Game menu", this.width / 2, 40, 0xFFFFFFFF);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}

	@Override
	public final void init() {
//		this.buttons.clear();
//		this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Options..."));
		Button options = this
				.addRenderableWidget(Button
						.builder(Component.literal("Options..."),
								var1 -> this.minecraft.gui
										.setScreen(new OptionsScreen(this, this.minecraft.options)))
						.bounds(this.width / 2 - 100, this.height / 4, 200, 20).build());
//		this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Generate new level..."));
		Button newLevel = this.addRenderableWidget(Button
				.builder(Component.literal("Generate new level..."),
						var1 -> this.minecraft.gui.setScreen(new NewLevelScreen(this)
							))
				.bounds(this.width / 2 - 100, this.height / 4 + 24, 200, 20).build());
//		this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Save level.."));
		Button saveLevel = this.addRenderableWidget(Button
				.builder(Component.literal("Save level.."),
						var1 -> this.minecraft.gui.setScreen(new SaveLevelScreen(this)))
				.bounds(this.width / 2 - 100, this.height / 4 + 48, 200, 20).build());
//		this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 72, "Load level.."));
		Button loadLevel = this.addRenderableWidget(Button
				.builder(Component.literal("Load level.."),
						var1 -> this.minecraft.gui.setScreen(new LoadLevelScreen(this)))
				.bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build());
//		this.buttons.add(new Button(4, this.width / 2 - 100, this.height / 4 + 120, "Back to game"));
		Button resume = this.addRenderableWidget(Button
				.builder(Component.literal("Back to game"),
						var1 -> {this.minecraft.gui.setScreen(null);
						this.minecraft.mouseHandler.grabMouse();})
				.bounds(this.width / 2 - 100, this.height / 4 + 120, 200, 20).build());
		if(this.minecraft.getUser() == null) {
			saveLevel.active = false;
			loadLevel.active = false;
		}

		if(this.minecraft.getCurrentServer() != null) {
			newLevel.active = false;
			saveLevel.active = false;
			loadLevel.active = false;
		}

	}
}