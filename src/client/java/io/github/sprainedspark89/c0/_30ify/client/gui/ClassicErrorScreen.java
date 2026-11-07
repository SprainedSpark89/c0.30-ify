
package io.github.sprainedspark89.c0._30ify.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClassicErrorScreen extends Screen {
    private final Component desc;
    boolean multiplayerDisconnect;

    public ClassicErrorScreen(String title, String description, boolean canEsc) {
        super(Component.literal(title));
        this.desc = Component.literal(description);
        this.multiplayerDisconnect = canEsc;
    }

    @Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
    	graphics.fillGradient(0, 0, this.width, this.height, -12574688, -11530224);
    }

    @Override
    public void extractRenderState(
        GuiGraphicsExtractor graphics,
        int mouseX,
        int mouseY,
        float partialTick
    ) {
    	graphics.centeredText(this.font, this.title, this.width / 2, 90, 0xFFFFFFFF);
    	graphics.centeredText(this.font, this.desc, this.width / 2, 110, 0xFFFFFFFF);
    	super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return this.multiplayerDisconnect;
    }
}