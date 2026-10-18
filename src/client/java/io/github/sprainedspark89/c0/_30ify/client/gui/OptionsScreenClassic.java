package io.github.sprainedspark89.c0._30ify.client.gui;

import com.mojang.blaze3d.platform.InputConstants;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.util.ClassicOptions;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.OptionInstance.ValueSet;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

public final class OptionsScreenClassic extends Screen {
	private Screen parent;
	private String title = "Options";
	private Options options;
	private OptionInstance[] menuOptions;

	public OptionsScreenClassic(Screen screen, Options options) {
		super(Component.literal("Options"));
		this.parent = screen;
		this.options = options;
		this.menuOptions = ClassicOptions.getMainOptionsMenu(options);
	}

	@Override
	public boolean keyPressed(final KeyEvent event) {
		if (C030IfyClient.toggleC030GUI.matches(event)) {
			C030IfyClient.classicGui = !C030IfyClient.classicGui;
			this.minecraft.gui.setScreen(new OptionsScreen(parent, options, false));
		}
		return super.keyPressed(event);
	}

	@Override
	public final void init() {
		for (int i1 = 0; i1 < this.menuOptions.length; ++i1) {
			// this.menuOptions[i1].createButton(options, this.width / 2 - 155 + i1 % 2 *
			// 160, this.height / 6 + 24 * (i1 >> 1), 150);
			this.makeButton(i1, this.menuOptions[i1]);
			// this.buttons.add(new SmallButton(i1, this.width / 2 - 155 + i1 % 2 * 160,
			// this.height / 6 + 24 * (i1 >> 1), this.options.getMessage(i1)));
		}

		// this.buttons.add(new Button(100, this.width / 2 - 100, this.height / 6 + 120
		// + 12, "Controls..."));
		Button controls = this.addRenderableWidget(Button
				.builder(Component.literal("Controls..."),
						var1 -> this.minecraft.gui.setScreen(new KeyBindsScreen(this, this.minecraft.options)))
				.bounds(this.width / 2 - 100, this.height / 6 + 120 + 12, 200, 20).build());
		// this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 6 + 168,
		// "Done"));
		Button done = this.addRenderableWidget(
				Button.builder(Component.literal("Done"), var1 -> this.minecraft.gui.setScreen(this.parent))
				.bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
	}

	public void makeButton(int i1, OptionInstance option) {
		int x = this.width / 2 - 155 + i1 % 2 * 160, y = this.height / 6 + 24 * (i1 >> 1);
		Button optionButton = this
				.addRenderableWidget(Button.builder(Component.literal(getDisplayString(option)), var1 -> {
					ClassicOptions.handleOptionChange(option, options);

					if (option == ClassicOptions.SHOW_FPS) {
						ClassicOptions.saveSettings();
					}

					var1.setMessage(Component.literal(getDisplayString(option)));
				}).bounds(x, y, 150, 20).build());
	}

	public String getDisplayString(OptionInstance option) {
		return ClassicOptions.getMessage(option, options);
	}

	// protected final void buttonClicked(Button button) {
	// if(button.enabled) {
	// if(button.id < 100) {
	// this.options.setOption(button.id, 1);
	// button.msg = this.options.getMessage(button.id);
	// }
	//
	// if(button.id == 100) {
	// this.minecraft.setScreen(new ControlsScreen(this, this.options));
	// }
	//
	// if(button.id == 200) {
	// this.minecraft.setScreen(this.parent);
	// }
	//
	// }
	// }

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
			final float a) {
		// fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		graphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
			final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}
}