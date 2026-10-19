package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.github.sprainedspark89.c0._30ify.client.C030IfyClient;
import net.minecraft.client.gui.components.ChatComponent;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {


@Redirect(
    method = "extractRenderState(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IILnet/minecraft/client/gui/components/ChatComponent$DisplayMode;)V",
    at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V"
    )
)
private void c030ify$toggleChatBackground(
        ChatComponent.ChatGraphicsAccess graphics,
        int x1, int y1, int x2, int y2, int color
) {
    if (!C030IfyClient.classicGui) {
        graphics.fill(x1, y1, x2, y2, color);
    }
}


}
