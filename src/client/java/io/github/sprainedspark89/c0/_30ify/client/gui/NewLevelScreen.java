package io.github.sprainedspark89.c0._30ify.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraft.world.level.levelgen.presets.WorldPresets;

public final class NewLevelScreen extends Screen {
	private Screen parent;

	public NewLevelScreen(Screen screen) {
		super(Component.literal("Generate new level"));
		this.parent = screen;
	}

	@Override
	public final void init() {
//		this.buttons.clear();
		Button smallLevel = this
				.addRenderableWidget(Button
						.builder(Component.literal("SURVIVAL TEST Infinite"),
								var1 -> {
									LevelSettings settings = new LevelSettings(
									        "SURVIVAL_TEST",
									        GameType.SURVIVAL,
									        LevelSettings.DifficultySettings.DEFAULT,
									        false,
									        WorldDataConfiguration.DEFAULT
									    );
									this.minecraft.createWorldOpenFlows().createFreshLevel(
											"SURVIVAL_TEST",
									        settings,
									        WorldOptions.defaultWithRandomSeed(),
									        WorldPresets::createNormalWorldDimensions,
									        this
									    );
								})
						.bounds(this.width / 2 - 100, this.height / 4, 200, 20).build());
//		this.buttons.add(new Button(0, this.width / 2 - 100, this.height / 4, "Small"));
		Button normalLevel = this
				.addRenderableWidget(Button
						.builder(Component.literal("Creative Infinite"),
								var1 -> {
									LevelSettings settings = new LevelSettings(
									        "LEVEL",
									        GameType.CREATIVE,
									        LevelSettings.DifficultySettings.DEFAULT,
									        false,
									        WorldDataConfiguration.DEFAULT
									    );
									this.minecraft.createWorldOpenFlows().createFreshLevel(
											"Level",
									        settings,
									        WorldOptions.testWorldWithRandomSeed(),
									        WorldPresets::createTestWorldDimensions,
									        this
									    );
								})
						.bounds(this.width / 2 - 100, this.height / 4 + 24, 200, 20).build());
//		this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 24, "Normal"));
		Button hugeLevel = this
				.addRenderableWidget(Button
						.builder(Component.literal("Tutorial Infinite"),
								var1 -> this.minecraft
								.createWorldOpenFlows()
								.createFreshLevel("Tutorial_Level", MinecraftServer.DEMO_SETTINGS, WorldOptions.DEMO_OPTIONS, WorldPresets::createNormalWorldDimensions, this))
						.bounds(this.width / 2 - 100, this.height / 4 + 48, 200, 20).build());
//		this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 48, "Huge"));
		Button cancel = this
				.addRenderableWidget(Button
						.builder(Component.literal("Cancel"),
								var1 -> this.minecraft.gui
										.setScreen(this.parent))
						.bounds(this.width / 2 - 100, this.height / 4 + 120, 200, 20).build());
//		this.buttons.add(new Button(3, this.width / 2 - 100, this.height / 4 + 120, "Cancel"));
	}

//	protected final void buttonClicked(Button button) {
//		if(button.id == 3) {
//			this.minecraft.setScreen(this.parent);
//		} else {
//			this.minecraft.generateLevel(button.id);
//			this.minecraft.setScreen((Screen)null);
//			this.minecraft.grabMouse();
//		}
//	}

//	public final void render(int xMouse, int yMouse) {
//		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
//		drawCenteredString(this.font, "Generate new level", this.width / 2, 40, 0xFFFFFF);
//		super.render(xMouse, yMouse);
//	}
	private void createClassicWorld(String name, GameType type) {
	    LevelSettings settings = new LevelSettings(
	        name,
	        type,
	        LevelSettings.DifficultySettings.DEFAULT,
	        type == GameType.CREATIVE,
	        WorldDataConfiguration.DEFAULT
	    );

	    this.minecraft.createWorldOpenFlows().createFreshLevel(
	        name,
	        settings,
	        WorldOptions.defaultWithRandomSeed(),
	        WorldPresets::createNormalWorldDimensions,
	        this
	    );
	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		graphics.centeredText(this.font, "Generate new level", this.width / 2, 40, 0xFFFFFFFF);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}
}