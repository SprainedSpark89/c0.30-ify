package io.github.sprainedspark89.c0._30ify.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.ActiveTextCollector;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.TextAlignment;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;

@Mixin(AbstractButton.class)
public abstract class AbstractButtonInactiveMixin extends AbstractWidget.WithInactiveMessage {

	public AbstractButtonInactiveMixin(int x, int y, int width, int height, Component message) {
		super(x, y, width, height, message);
		// TODO Auto-generated constructor stub
	}
	
	@Overwrite
	protected void extractDefaultLabel(final ActiveTextCollector output) { // This is a feature, not a bug fix, totally
		AbstractButton button = (AbstractButton) (Object) this;
        Component message = button.getMessage();

        MutableComponent styledMessage = message.copy()
            .setStyle(message.getStyle().withColor(
                button.isHoveredOrFocused() && button.active && button.visible
                    ? TextColor.YELLOW
                    : TextColor.WHITE
            ));
		output.accept(
			    TextAlignment.CENTER,
			    this.getX() + (this.getWidth() / 2),
			    this.getY() + ((this.getHeight() - 8) / 2),
			    styledMessage
			);
	}
	
	

	
}
