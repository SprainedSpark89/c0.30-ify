package io.github.sprainedspark89.c0._30ify.client.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;
import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;

@Mixin(PlayerTabOverlay.class)
public abstract class PlayerTabOverlayMixin {

	@Shadow
	private Minecraft minecraft;

	@Shadow
	private List<PlayerInfo> getPlayerInfos() {
		throw new AssertionError();
	}

	@Shadow
	public abstract Component getNameForDisplay(PlayerInfo info);

	@Inject(method = "extractRenderState", at = @At("HEAD"), cancellable = true)
	private void c030ify$renderClassicPlayerList(GuiGraphicsExtractor graphics, int screenWidth, Scoreboard scoreboard,
			Objective displayObjective, CallbackInfo ci) {
		if (!C030IfyClient.classicGui) {
			return;
		}

		ci.cancel();

		List<PlayerInfo> players = this.getPlayerInfos();
		int centerX = screenWidth / 2;
		int centerY = graphics.guiHeight() / 2;

		int mouseX = (int) this.minecraft.mouseHandler.getScaledXPos(this.minecraft.getWindow());
		int mouseY = (int) this.minecraft.mouseHandler.getScaledYPos(this.minecraft.getWindow());

		// Classic translucent panel: x = centerX +/- 128
		// y = centerY - 80 through centerY + 68.
		graphics.fillGradient(
			    centerX - 128, centerY - 80,
			    centerX + 128, centerY + 68,
			    0xB3000000, // top: black, 70% opacity
			    0xCC333333  // bottom: dark gray, 80% opacity
			);

		// Classic heading.
		Component heading = Component.literal("Connected players:");
		graphics.text(this.minecraft.font, heading, centerX - this.minecraft.font.width(heading) / 2, centerY - 76,
				0xFFFFFFFF);

		// Two columns, 120 pixels each, eight pixels per row.
		boolean hasHovered = false;
		for (int i = 0; i < players.size(); i++) {
			String username = players.get(i).getProfile().name();

			int x = centerX - 120 + (i % 2) * 120;
			int y = centerY - 64 + (i / 2) * 8;

			boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + 120 && mouseY < y + 8;
			hasHovered = !hasHovered ? hovered : hasHovered;
			graphics.text(this.minecraft.font, username, x + (hovered ? 2 : 0), y, hovered ? 0xFFFFFFFF : 0xFFEEEEEE);
			if(hovered) {
				C030IfyClient.hoveredUsername = username;
			}
		}
		if(!hasHovered) {
			C030IfyClient.hoveredUsername = null;
		}
	}

}
