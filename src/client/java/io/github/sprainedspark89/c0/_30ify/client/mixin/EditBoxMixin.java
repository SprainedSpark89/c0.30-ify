package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;

@Mixin(EditBox.class)
public abstract class EditBoxMixin {
	@Shadow
	private int textX;

	@Unique
	private boolean c030ify$isChatInput() {
		return C030IfyClient.classicGui && Minecraft.getInstance().gui.screen() instanceof ChatScreen;
	}

	@Inject(method = "extractWidgetRenderState", at = @At("HEAD"))
	private void c030ify$renderPrompt(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick,
			CallbackInfo ci) {
		if (!this.c030ify$isChatInput()) {
			return;
		}
		this.textX += 9;

		graphics.text(Minecraft.getInstance().font, ">", this.textX - 9, ((EditBox) (Object) this).getY(), 0xFFFFFFFF);
	}
	
	@Inject(method = "extractWidgetRenderState", at = @At("TAIL"))
	private void c030ify$renderPromptT(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick,
			CallbackInfo ci) {
		if (!this.c030ify$isChatInput()) {
			return;
		}
		this.textX -= 9;
	}

}
