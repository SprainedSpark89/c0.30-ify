package io.github.sprainedspark89.c0._30ify.client.gui;

import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;

//import com.mojang.blaze3d.platform.InputConstants;

//import io.github.sprainedspark89.c0._30ify.client.util.KeyInputMapper;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelSummary;

public final class NameLevelScreen extends Screen {
	private Screen parent;
	private String title = "Enter level name:";
	private int id;
	private String name;
	private int counter = 0;
	List<LevelSummary> worlds;
	Button save;

	public NameLevelScreen(Screen screen, String name, List<LevelSummary> worlds, int id) {
		super(Component.literal("Enter level name:"));
		this.parent = screen;
		this.id = id;
		this.name = name;
		this.worlds = worlds;
		if(this.name.equals("-")) {
			this.name = "";
		}

	}

	@Override
	public final void init() {
//		this.buttons.clear();
//		Keyboard.enableRepeatEvents(true);
		save = this
				.addRenderableWidget(Button
						.builder(Component.literal("Save"),
								var1 -> {
									if(this.name.trim().length() > 1) {
//										this.minecraft.gui.setScreen(null);
//										this.minecraft.mouseHandler.grabMouse();
										this.minecraft.disconnectFromWorld(ClientLevel.DEFAULT_QUIT_MESSAGE);
									}
								})
						.bounds(this.width / 2 - 100, this.height / 4 + 120, 200, 20).build());
//		this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4 + 120, "Save"));
		Button cancel = this
				.addRenderableWidget(Button
						.builder(Component.literal("Cancel"),
								var1 -> {
//									if(this.name.trim().length() > 1) {
										this.minecraft.gui.setScreen(this.parent);
//										this.minecraft.mouseHandler.grabMouse();
//									}
								})
						.bounds(this.width / 2 - 100, this.height / 4 + 144, 200, 20).build());
//		this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 144, "Cancel"));
		save.active = this.name.trim().length() > 1;
	}

//	public final void removed() {
//		Keyboard.enableRepeatEvents(false);
//	}

	@Override
	public void tick() {
		++this.counter;
	}

//	protected final void buttonClicked(Button button) {
//		if(button.enabled) {
////			if(button.id == 0 && this.name.trim().length() > 1) {
////				Minecraft minecraft10000 = this.minecraft;
////				int i10001 = this.id;
////				String string4 = this.name.trim();
////				int i3 = i10001;
////				Minecraft minecraft2 = minecraft10000;
////				minecraft10000.levelIo.save(minecraft2.level, minecraft2.host, minecraft2.user.name, minecraft2.user.sessionid, string4, i3);
////				this.minecraft.setScreen((Screen)null);
////				this.minecraft.grabMouse();
////			}
//
////			if(button.id == 1) {
////				this.minecraft.setScreen(this.parent);
////			}
//
//		}
//	}
	@Override
	public boolean keyPressed(KeyEvent event) {
	    switch (event.key()) {
	        case InputConstants.KEY_BACKSPACE -> {
	            if (!name.isEmpty()) {
	                name = name.substring(0, name.length() - 1);
	            }
	        }

	        case InputConstants.KEY_RETURN -> {
	            if (save.active) {
	                save.onPress(event);
	            }
	        }

	        default -> {
	            return super.keyPressed(event);
	        }
	    }

	    save.active = name.trim().length() > 1;
	    return true;
	}
	
	@Override
	public boolean charTyped(CharacterEvent event) {
	    String c = event.codepointAsString();

	    if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\"#%/()=+?[]{}<>".indexOf(c) >= 0 && name.length() < 64) {
	        name += c;
	    }

	    save.active = name.trim().length() > 1;
	    return true;
	}

//	@Override
//	protected void keyPressed(char chr, int key) {
//	    if (key == GLFW.GLFW_KEY_BACKSPACE && !this.name.isEmpty()) {
//	        this.name = this.name.substring(0, this.name.length() - 1);
//	    } else if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\"#%/()=+?[]{}<>".indexOf(chr) >= 0 && this.name.length() < 64) {
//	        this.name += chr;
//	    }
//
//	    save.active = this.name.trim().length() > 1;
//	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		graphics.centeredText(this.font, this.title, this.width / 2, 40, 0xFFFFFFFF);
		int i3 = this.width / 2 - 100;
		int i4 = this.height / 2 - 10;
		graphics.fill(i3 - 1, i4 - 1, i3 + 200 + 1, i4 + 20 + 1, -6250336);
		graphics.fill(i3, i4, i3 + 200, i4 + 20, 0xFF000000);
		graphics.text(this.font, this.name + (this.counter / 6 % 2 == 0 ? "_" : ""), i3 + 4, i4 + 6, 0xFF000000 + 14737632);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}
}