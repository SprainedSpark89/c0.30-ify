
package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.renderpearl.api.pipeline.RenderPipeline;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import io.github.sprainedspark89.c0._30ify.client.util.ClassicChunkUpdates;
import io.github.sprainedspark89.c0._30ify.client.util.ClassicOptions;
import net.minecraft.SharedConstants;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.contextualbar.ContextualBar;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(Hud.class)
public abstract class HudMixin {

	@Shadow
	@Final
	private static Identifier HOTBAR_OFFHAND_LEFT_SPRITE;
	@Shadow
	@Final
	private static Identifier HOTBAR_OFFHAND_RIGHT_SPRITE;

	@Inject(method = "extractSelectedItemName", at = @At("HEAD"), cancellable = true)
	private void c030ify$hideSelectedItemName(CallbackInfo ci) {
		if (C030IfyClient.classicGui) {
			ci.cancel();
		}
	}

	@ModifyConstant(method = "extractPlayerHealth", constant = @Constant(intValue = 39))
	private int c030ify$moveHealthDown(int value) {
		return C030IfyClient.classicGui ? 32 : value;
	}

	@ModifyConstant(method = "extractItemHotbar", constant = @Constant(intValue = 23))
	private int c030ify$classicSelectionHeight(int value) {
		return C030IfyClient.classicGui ? 22 : value;
	}

	@Redirect(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/contextualbar/ContextualBar;extractBackground(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"))
	private void c030ify$hideContextualBackground(ContextualBar bar, GuiGraphicsExtractor graphics,
			DeltaTracker deltaTracker) {
		if (!C030IfyClient.classicGui) {
			bar.extractBackground(graphics, deltaTracker);
		}
	}

	@Redirect(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/contextualbar/ContextualBar;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/DeltaTracker;)V"))
	private void c030ify$hideContextualBar(ContextualBar bar, GuiGraphicsExtractor graphics,
			DeltaTracker deltaTracker) {
		if (!C030IfyClient.classicGui) {
			bar.extractRenderState(graphics, deltaTracker);
		}
	}

	@Redirect(
		    method = "extractPlayerHealth",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/gui/Hud;getVehicleMaxHearts(Lnet/minecraft/world/entity/LivingEntity;)I"
		    )
		)
		private int c030ify$showHunger(Hud instance, LivingEntity entity) {
		    if (C030IfyClient.classicGui) {
		        return 0;
		    }

		    return ((HudAccessor) instance).c030ify$getVehicleMaxHearts(entity);
		}

	@Inject(method = "extractVehicleHealth", at = @At("HEAD"), cancellable = true)
	private void extractVehicleHealth(final GuiGraphicsExtractor graphics, CallbackInfo ci) {
		if (C030IfyClient.classicGui) {
			ci.cancel();
		}
		}

	@Redirect(method = "extractHotbarAndDecorations", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/contextualbar/ContextualBar;extractExperienceLevel(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/client/gui/Font;I)V"), require = 1)
	private static void c030ify$extractExperienceLevel(GuiGraphicsExtractor graphics, Font font, int experienceLevel) {
		if (!C030IfyClient.classicGui) {
			ContextualBarAccessor.c030ify$extractExperienceLevel(graphics, font, experienceLevel);
		}
	}

	@Inject(method = "extractRenderState", at = @At("TAIL"))
	private void c030ify$renderClassicDebugText(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker,
			CallbackInfo ci) {
		if (!C030IfyClient.classicGui || ((Hud)(Object)this).isHidden()) {
			return;
		}

		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;

		graphics.text(font, SharedConstants.getCurrentVersion().id(), 2, 2, 0xFFFFFFFF);

		if (ClassicOptions.SHOW_FPS.get()) {
			graphics.text(font, minecraft.getFps() + " fps, " + ClassicChunkUpdates.chunkUpdates + " chunk updates", 2,
					12, 0xFFFFFFFF);
		}

		if (minecraft.gameMode.canHurtPlayer()) {
			String string24 = "Score: §e" + minecraft.player.getScore();
			graphics.text(font, string24, graphics.guiWidth() - font.width(string24) - 2, 2, 0xFFFFFFFF);
			string24 = "Progress: §e" + (int) (minecraft.player.experienceProgress * 100) + "%";
			graphics.text(font, string24, graphics.guiWidth() - font.width(string24) - 2, 12, 0xFFFFFFFF);
			string24 = "Level: §e" + minecraft.player.experienceLevel;
			graphics.text(font, string24, graphics.guiWidth() - font.width(string24) - 2, 22, 0xFFFFFFFF);
			if (minecraft.player.jumpableVehicle() != null) {
				string24 = "Jump: §e" + (int) (minecraft.player.getJumpRidingScale() * 100) + "%";
				graphics.text(font, string24, graphics.guiWidth() - font.width(string24) - 2, 32, 0xFFFFFFFF);
			}
			// if(!minecraft.player.getOffhandItem().isEmpty()) {
			// graphics.text(font, itemName(minecraft.player.getOffhandItem())+ ": " +
			// minecraft.player.getOffhandItem().count(), graphics.guiWidth() / 2 + 8,
			// graphics.guiHeight() - 33, 0xFFFFFFFF);
			// }
		}
	}

	public String itemName(ItemStack i) {
		return i.getDisplayName().getString().replace("[", "").replace("]", "").split(" ")[0]
				+ (i.count() > 1 ? "s" : "");
	}

	@Redirect(method = "extractPlayerHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Hud;extractAirBubbles(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;III)V"))
	private void c030ify$positionAirBubbles(Hud hud, GuiGraphicsExtractor graphics, Player player, int vehicleHearts,
			int yLineAir, int xRight) {
		if (C030IfyClient.classicGui) {
			int xLeft = graphics.guiWidth() / 2 - 91;
			((HudAccessor) hud).c030ify$extractAirBubbles(graphics, player, vehicleHearts, yLineAir, xLeft);
		} else {
			((HudAccessor) hud).c030ify$extractAirBubbles(graphics, player, vehicleHearts, yLineAir, xRight);
		}
	}

	@ModifyConstant(method = "extractAirBubbles", constant = @Constant(intValue = 8))
	private int c030ify$reverseAirBubbleDirection(int value) {
		return C030IfyClient.classicGui ? -8 : value;
	}

	@ModifyConstant(method = "extractAirBubbles", constant = @Constant(intValue = 9, ordinal = 0))
	private int c030ify$reverseAirBubbleOffset(int value) {
		return C030IfyClient.classicGui ? 0 : value;
	}

	@Redirect(method = "extractItemHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/renderpearl/api/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"))
	private void c030ify$hideOffhandDecoration(GuiGraphicsExtractor graphics, RenderPipeline pipeline,
			Identifier sprite, int x, int y, int width, int height) {
		if (C030IfyClient.classicGui
				&& (sprite.equals(HOTBAR_OFFHAND_LEFT_SPRITE) || sprite.equals(HOTBAR_OFFHAND_RIGHT_SPRITE))) {
			Minecraft minecraft = Minecraft.getInstance();
			Font font = minecraft.font;
			if (!minecraft.player.getOffhandItem().isEmpty()) {
				graphics.text(font,
						itemName(minecraft.player.getOffhandItem()) + ": " + minecraft.player.getOffhandItem().count(),
						x - (sprite.equals(HOTBAR_OFFHAND_RIGHT_SPRITE) ? 0 : width), y - height, 0xFFFFFFFF);
			}

			return;
		}

		graphics.blitSprite(pipeline, sprite, x, y, width, height);
	}

	@Inject(method = "extractSlot", at = @At("HEAD"), cancellable = true)
	private void extractSlot(final GuiGraphicsExtractor graphics, final int x, final int y,
			final DeltaTracker deltaTracker, final Player player, final ItemStack itemStack, final int seed,
			CallbackInfo ci) {

		if (C030IfyClient.classicGui && player.getOffhandItem() == itemStack) {
			ci.cancel();
		}
	}

	@ModifyArg(
		    method = "extractPlayerHealth",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/gui/Hud;extractArmor(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIII)V"
		    ),
		    index = 4
		)
		private int c030ify$adjustArmorRowHeight(int healthRowHeight) {
		    return C030IfyClient.classicGui ? 32 : healthRowHeight;
		}

		@ModifyArg(
		    method = "extractPlayerHealth",
		    at = @At(
		        value = "INVOKE",
		        target = "Lnet/minecraft/client/gui/Hud;extractArmor(Lnet/minecraft/client/gui/GuiGraphicsExtractor;Lnet/minecraft/world/entity/player/Player;IIII)V"
		    ),
		    index = 5
		)
		private int c030ify$moveArmorToRight(int xLeft) {
		    if (!C030IfyClient.classicGui) {
				return xLeft;
			}
		    return (Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2 + 91) - 9;
		}

	@ModifyConstant(method = "extractArmor", constant = @Constant(intValue = 8))
	private static int c030ify$reverseArmorDirection(int value) {
		return C030IfyClient.classicGui ? -8 : value;
	}
}