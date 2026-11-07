//package io.github.sprainedspark89.c0._30ify.client.gui;
//
//import org.lwjgl.glfw.GLFW;
//
//import net.minecraft.client.gui.GuiGraphicsExtractor;
//import net.minecraft.client.gui.screens.Screen;
//import net.minecraft.client.input.CharacterEvent;
//import net.minecraft.client.input.KeyEvent;
//import net.minecraft.client.input.MouseButtonEvent;
//import net.minecraft.network.chat.Component;
//
//public final class ClassicChatScreen extends Screen {
//	public ClassicChatScreen(Component title) {
//		super(title);
//		// TODO Auto-generated constructor stub
//	}
//
//	private String typedMsg = "";
//	private int counter = 0;
//
//	// public final void init() {
//	// Keyboard.enableRepeatEvents(true);
//	// }
//	//
//	// public final void removed() {
//	// Keyboard.enableRepeatEvents(false);
//	// }
//	@Override
//	public final void tick() {
//		++this.counter;
//	}
//
//	@Override
//	public boolean keyPressed(KeyEvent event) {
//		switch (event.key()) {
//			case GLFW.GLFW_KEY_BACKSPACE -> {
//				if (this.typedMsg.length() > 0) {
//					this.typedMsg = this.typedMsg.substring(0, this.typedMsg.length() - 1);
//				}
//			}
//
//			case GLFW.GLFW_KEY_ENTER -> {
//				String eventKey1 = this.typedMsg.trim();
//
//				if ((eventKey1 = eventKey1.trim()).length() > 0) {
//					this.minecraft.player.connection.sendChat(eventKey1);
//				}
//
//				this.minecraft.setScreenAndShow((Screen) null);
//			}
//
//			case GLFW.GLFW_KEY_ESCAPE -> {
//				this.minecraft.setScreenAndShow((Screen) null);
//			}
//
//			default -> {
//				return super.keyPressed(event);
//			}
//		}
//
//		return true;
//	}
//
//	@Override
//	public boolean charTyped(CharacterEvent event) {
//		String eventCharacter = event.codepointAsString();
//
//		if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\\\"#%/()=+?[]{}<>@|$;"
//				.indexOf(eventCharacter) >= 0
//				&& this.typedMsg.length() < 64 - (this.minecraft.getUser().getName().length() + 2)) {
//			this.typedMsg = this.typedMsg + eventCharacter;
//		}
//
//		return true;
//	}
//
//	@Override
//	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
//			final float a) {
//		graphics.fill(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
//		graphics.text(this.font, "> " + this.typedMsg + (this.counter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12,
//				14737632 + 0xFF000000);
//		super.extractRenderState(graphics, mouseX, mouseY, a);
//	}
//
//	@Override
//	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
//		//		if (event.button() == 0 && this.minecraft.gui.hoveredUsername != null) {
//		//			if (!this.typedMsg.isEmpty() && !this.typedMsg.endsWith(" ")) {
//		//				this.typedMsg += " ";
//		//			}
//		//			this.typedMsg += this.minecraft.gui.hoveredUsername;
//		//			int maxLength = 64 - (this.minecraft.getUser().getName().length() + 2);
//		//			if (this.typedMsg.length() > maxLength) {
//		//				this.typedMsg = this.typedMsg.substring(0, maxLength);
//		//			}
//		//			return true;
//		//		}
//		return super.mouseClicked(event, doubleClick);
//	}
//
//	@Override
//	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//	}
//
//	@Override
//	public boolean isPauseScreen() {
//		return false;
//	}
//}