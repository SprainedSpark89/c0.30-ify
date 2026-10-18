package io.github.sprainedspark89.c0._30ify.client.gui;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

public final class ControlsScreenClassic extends Screen {
	private Screen parent;
	private String title = "Controls";
	private Options options;
	private Button selectedButton = null;
	private KeyMapping selectedKey = null;

	public ControlsScreenClassic(Screen screen, Options options) {
		super(Component.literal("Controls"));
		this.parent = screen;
		this.options = options;
	}

	public final void init() {
//		System.out.println(width + " x " + height);
		for(int i1 = 0; i1 < this.options.keyMappings.length; ++i1) {
			this.makeButton(i1);
		}

//		this.buttons.add(new Button(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
		Button done = this
				.addRenderableWidget(Button
						.builder(Component.literal("Done"),
								var1 -> this.minecraft.gui
										.setScreen(this.parent))
						.bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
	}
	
	public void makeButton(int i1) {
		int x = this.width / 2 - 155 + i1 % 2 * 160, y = this.height / 6 + 24 * (i1 >> 1);
//		String key = this.options.keyMappings[i1].getName();
//		String translated = Component.translatable(key).getString();
//
//		System.out.println(key + " -> '" + translated + "'");
		Button optionButton = this
				.addRenderableWidget(Button
						.builder(Component.literal(Component.translatable(this.options.keyMappings[i1].getName()).getString() + ": " + this.options.keyMappings[i1].getTranslatedKeyMessage().getString()),
								var1 -> {
									if (this.selectedButton != null) {
									    this.selectedButton.setMessage(Component.literal(
									        Component.translatable(this.selectedKey.getName()).getString()
									        + ": "
									        + this.selectedKey.getTranslatedKeyMessage().getString()
									    ));
									}

									this.selectedButton = var1;
									this.selectedKey = this.options.keyMappings[i1];
									var1.setMessage(Component.literal("> "
									    + Component.translatable(this.selectedKey.getName()).getString()
									    + " <"));
									
								})
						.bounds(x, y, 150, 20).build());
//		
//		System.out.println("Button Width:" + optionButton.getWidth());
//		System.out.println("Button Height:" + optionButton.getHeight());
	}

//	protected final void buttonClicked(Button button) {
//		for(int i2 = 0; i2 < this.options.keys.length; ++i2) {
//			((Button)this.buttons.get(i2)).msg = this.options.getKeyMessage(i2);
//		}
//
//		if(button.id == 200) {
//			this.minecraft.setScreen(this.parent);
//		} else {
//			this.selectedKey = button.id;
//			button.msg = "> " + this.options.getKeyMessage(button.id) + " <";
//		}
//	}

	@Override
	public boolean keyPressed(final KeyEvent event) {
		if(this.selectedButton != null) {
			this.selectedKey.setKey(InputConstants.getKey(event));
			this.selectedButton.setMessage(Component.literal(Component.translatable(this.selectedKey.getName()).getString() + ": " + selectedKey.getTranslatedKeyMessage().getString()));
			this.selectedButton = null;
			return true;
		} else {
			return super.keyPressed(event);
		}
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		graphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}
	
	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}
}