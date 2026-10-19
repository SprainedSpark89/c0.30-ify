package io.github.sprainedspark89.c0._30ify.client.gui;


import java.util.List;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;

public final class SaveLevelScreen extends LoadLevelScreen {
	public SaveLevelScreen(Screen screen1) {
		super(screen1);
		this.title = "Save level";
		this.isSaveScreen = true;
	}

	public final void init() {
		super.init();
		load.setMessage(Component.literal("Save file..."));
		this.setLevels();
	}

	protected final void setLevels() {
		for(Button level : levels) {
			level.setMessage(level.getMessage());
//			((Button)this.buttons.get(i2)).msg = level;
			level.visible = true;
//			((Button)this.buttons.get(i2)).visible = true;
			level.active = !this.minecraft.isDemo();
//			((Button)this.buttons.get(i2)).enabled = this.minecraft.user.hasPaid;
		}

	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		super.extractRenderState(graphics, mouseX, mouseY, a);
		if(this.minecraft.isDemo()) {
			graphics.fillGradient(this.width / 2 - 80, 72, this.width / 2 + 80, 120, -536870912, -536870912);
			graphics.centeredText(this.font, "Premium only!", this.width / 2, 80, 0xFF000000 + 16748688);
			graphics.centeredText(this.font, "Purchase the game to be able", this.width / 2, 96, 0xFF000000 + 14712960);
			graphics.centeredText(this.font, "to save your levels online.", this.width / 2, 104, 0xFF000000 + 14712960);
		}
	}

//	protected final void loadLevel(File file) {
//		if(!file.getName().endsWith(".mine")) {
//			file = new File(file.getParentFile(), file.getName() + ".mine");
//		}
//
//		File file2 = file;
//		Minecraft file1 = this.minecraft;
//		this.minecraft.levelIo.save(file1.level, file2);
//		this.minecraft.setScreen(this.parent);
//	}

	@Override
	public void loadLevel(List<LevelSummary> worlds, int i, Button button) {
		this.minecraft.gui.setScreen(new NameLevelScreen(this, button.getMessage().getString(), worlds, i));
	}
}