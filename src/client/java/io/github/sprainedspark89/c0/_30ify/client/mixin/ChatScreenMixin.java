package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.blaze3d.platform.InputConstants;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen { // Direct painful integration

	@Shadow
	protected EditBox input;

	public boolean tabDown = false;

	@Unique
	private PlayerTabOverlay tabList;

	protected ChatScreenMixin(Component title) {
		super(title);
		// TODO Auto-generated constructor stub

	}

	@Override
	public boolean charTyped(CharacterEvent event) {
		if (!C030IfyClient.classicGui) {
			return super.charTyped(event);
		}
		String eventCharacter = event.codepointAsString();

		if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\\\"#%/()=+?[]{}<>@|$;"
				.indexOf(eventCharacter) >= 0
				&& this.input.getValue().length() < 64 - (this.minecraft.getUser().getName().length() + 2)) {
			this.input.setValue(this.input.getValue() + eventCharacter);
		}

		return true;
	}

	@Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY,
			final float a, CallbackInfo i) {
		if (C030IfyClient.classicGui) {
			graphics.fill(2, this.height - 14, this.width - 2, this.height - 2,
					this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE));
			this.minecraft.gui.hud.getChat().extractRenderState(graphics, this.font,
					this.minecraft.gui.hud.getGuiTicks(), mouseX, mouseY, ChatComponent.DisplayMode.FOREGROUND, false);
			super.extractRenderState(graphics, mouseX, mouseY, a);
			this.extractTabList(graphics);
			i.cancel();
		}
	}

	private void extractTabList(final GuiGraphicsExtractor graphics) {
		Scoreboard scoreboard = this.minecraft.level.getScoreboard();
		Objective displayObjective = scoreboard.getDisplayObjective(DisplaySlot.LIST);
		if (!tabDown
			|| this.minecraft.isLocalServer() && this.minecraft.player.connection.getListedOnlinePlayers().size() <= 1 && displayObjective == null) {
			this.tabList.setVisible(false);
		} else {
			this.tabList.setVisible(true);
			graphics.nextStratum();
			this.tabList.extractRenderState(graphics, graphics.guiWidth(), scoreboard, displayObjective);
		}
	}

	@Inject(method = "init", at = @At("TAIL"), cancellable = true)
	public void init(CallbackInfo i) {
		this.tabList = new PlayerTabOverlay(minecraft, this.minecraft.gui.hud);
	}

	@Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
	public void keyPressed(final KeyEvent event, CallbackInfoReturnable<Boolean> i) {
		if (C030IfyClient.classicGui) {
			switch (event.key()) {
			case InputConstants.KEY_BACKSPACE -> {
				if (this.input.getValue().length() > 0) {
					this.input.setValue(this.input.getValue().substring(0, this.input.getValue().length() - 1));
				}
			}

			case InputConstants.KEY_RETURN -> {
				String eventKey1 = this.input.getValue().trim();

				if ((eventKey1 = eventKey1.trim()).length() > 0) {
					if (eventKey1.startsWith("/")) {
						this.minecraft.player.connection.sendCommand(eventKey1.substring(1));
					} else {
						this.minecraft.player.connection.sendChat(eventKey1);
					}
					this.input.setValue("");
				}

				this.minecraft.setScreenAndShow((Screen) null);
			}

			case InputConstants.KEY_ESCAPE -> {
				this.minecraft.setScreenAndShow((Screen) null);
			}

			default -> {
				// return super.keyPressed(event);
				i.setReturnValue(super.keyPressed(event));
			}
			}

			// return true;
			i.setReturnValue(true);
			this.tabDown = this.minecraft.options.keyPlayerList.matches(event);
			i.cancel();
		}
	}

	// Patches for the rebellion \/

	@Override
	public boolean keyReleased(final KeyEvent event) {
		this.tabDown = this.minecraft.options.keyPlayerList.matches(event) ? false : this.tabDown;
		return super.keyReleased(event);
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
	public void mouseClicked(final MouseButtonEvent event, final boolean doubleClick,
			CallbackInfoReturnable<Boolean> i) {
		if (C030IfyClient.classicGui) {
			if (event.button() == 0 && C030IfyClient.hoveredUsername != null && tabDown) {
				if (!this.input.getValue().isEmpty() && !this.input.getValue().endsWith(" ")) {
					this.input.setValue(this.input.getValue() + " ");
				}
				this.input.setValue(this.input.getValue() + C030IfyClient.hoveredUsername);
				int maxLength = 64 - (this.minecraft.getUser().getName().length() + 2);
				if (this.input.getValue().length() > maxLength) {
					this.input.setValue(this.input.getValue().substring(0, maxLength));
				}
			}
			i.setReturnValue(super.mouseClicked(event, doubleClick));
			i.cancel();
		}
	}
}
