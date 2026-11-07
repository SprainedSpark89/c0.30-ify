package io.github.sprainedspark89.c0._30ify.client.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.swing.JFileChooser;

import net.minecraft.CrashReport;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelStorageSource.LevelCandidates;
import net.minecraft.world.level.storage.LevelSummary;

public class LoadLevelScreen extends Screen implements Runnable {
	JFileChooser fileChooser;
	boolean fileLoaded = false;
	private boolean finished = false;
protected boolean isSaveScreen = false;
	protected File levelFile;
	List<Button> levels = new ArrayList<>();
	Button load;
	private boolean loaded = false;
	protected Screen parent;
	//	private String[] levels = null;
	private String status = "";
	protected String title = "Load level";

	public LoadLevelScreen(Screen screen) {
		super(Component.literal("Load level"));
		this.parent = screen;
	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}

//	protected void setLevels(String[] levels) {
//		for(int i2 = 0; i2 < 5; ++i2) {
//			((Button)this.buttons.get(i2)).enabled = !levels[i2].equals("-");
//			((Button)this.buttons.get(i2)).msg = levels[i2];
//			((Button)this.buttons.get(i2)).visible = true;
//		}
//
//	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
		graphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);
		if(this.fileLoaded) {
			graphics.centeredText(this.font, "Selecting file..", this.width / 2, this.height / 2 - 4, 0xFFFFFFFF);
//			drawCenteredString(this.font, "Selecting file..", this.width / 2, this.height / 2 - 4, 0xFFFFFF);

			try {
				Thread.sleep(20L);
			} catch (InterruptedException interruptedException3) {
				interruptedException3.printStackTrace();
			}
		} else {
			if(!this.loaded) {
				graphics.centeredText(this.font, this.status, this.width / 2, this.height / 2 - 4, 0xFFFFFFFF);
//				drawCenteredString(this.font, this.status, this.width / 2, this.height / 2 - 4, 0xFFFFFF);
			}

			super.extractRenderState(graphics, mouseX, mouseY, a);
		}
	}

	@Override
	public void init() {
//		(new Thread(this)).start();
		List<LevelSummary> world;
		try {
			world = this.loadLevels().get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			world = List.of();
		}

		List<LevelSummary> worlds = new ArrayList<>(world);
		worlds.sort(Comparator.comparingLong(LevelSummary::getLastPlayed).reversed());

		for(int i1 = 0; i1 < 5; ++i1) {
			int i = i1;
//			this.buttons.add(new Button(i1, this.width / 2 - 100, this.height / 6 + i1 * 24, "---"));
			String worldName = worlds.size() - 1 >= i ? worlds.get(i).getLevelName() : "---";
			Button level = this
					.addRenderableWidget(Button
							.builder(Component.literal(worldName.contentEquals("---") ? "-" : worldName),
									var1 -> {
										loadLevel(worlds, i, var1);
										})
							.bounds(this.width / 2 - 100, this.height / 6 + i * 24, 200, 20).build());
			if(worldName.contentEquals("---")) {
				level.active = false;
			}
			levels.add(level);
		}
		load = this
				.addRenderableWidget(Button
						.builder(Component.literal("Load file.."),
								var1 -> this.minecraft.gui
										.setScreen(new SelectWorldScreen(this)))
						.bounds(this.width / 2 - 100, this.height / 6 + 120 + 12, 200, 20).build());
//		this.buttons.add(new Button(5, this.width / 2 - 100, this.height / 6 + 120 + 12, "Load file..."));
		Button cancel = this
				.addRenderableWidget(Button
						.builder(Component.literal("Cancel"),
								var1 -> {//if(this.finished || this.loaded) {
										this.minecraft.gui.setScreen(this.parent);
									})
						.bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
//		this.buttons.add(new Button(6, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
	}

public void loadLevel(List<LevelSummary> worlds, int i, Button button) {
	if(worlds.get(i) != null && worlds.get(i).getLevelId() != null) {
		this.minecraft.createWorldOpenFlows().openWorld(worlds.get(i).getLevelId(), null);
	}
}

//	protected void loadLevel(File file) {
//		File file2 = file;
//		Minecraft file1 = this.minecraft;
//		boolean z10000;
//		Level level5;
//		if((level5 = this.minecraft.levelIo.load(file2)) == null) {
//			z10000 = false;
//		} else {
//			file1.loadLegacy(level5);
//			z10000 = true;
//		}
//
//		this.minecraft.setScreen(this.parent);
//	}

//	protected void loadLevel(int id) {
//		this.minecraft.loadLevel(this.minecraft.getUser().getName(), id);
//		this.minecraft.setScreen((Screen)null);
//		this.minecraft.grabMouse();
//	}

	//	protected final void buttonClicked(Button button) {
	//		if(!this.fileLoaded) {
	//			if(button.enabled) {
	//				if(this.loaded && button.id < 5) {
	//					this.loadLevel(button.id);
	//				}
	//
	//				if(this.finished || this.loaded && button.id == 5) {
	//					this.fileLoaded = true;
	////					LoadLevelScreenThread loadLevelScreenThread2;
	////					(loadLevelScreenThread2 = new LoadLevelScreenThread(this)).setDaemon(true);
	////					SwingUtilities.invokeLater(loadLevelScreenThread2);
	//				}
	//
	//				if(this.finished || this.loaded && button.id == 6) {
	//					this.minecraft.gui.setScreen(this.parent);
	//				}
	//
	//			}
	//		}
	//	}
	
		private CompletableFuture<List<LevelSummary>> loadLevels() {
			LevelCandidates levelCandidates;
			try {
				levelCandidates = this.minecraft.getLevelSource().findLevelCandidates();
			} catch (LevelStorageException e) {
	//			LOGGER.error("Couldn't load level list", e);
	//			this.handleLevelLoadFailure(e.getMessageComponent());
				return CompletableFuture.failedFuture(e);
			}
	
			return this.minecraft.getLevelSource().loadLevelSummaries(levelCandidates).exceptionally(throwable -> {
				this.minecraft.delayCrash(CrashReport.forThrowable(throwable, "Couldn't load level list"));
				return List.of();
			});
		}

	@Override
	public final void removed() {
		super.removed();
		if(this.fileChooser != null) {
			this.fileChooser.cancelSelection();
		}

	}

	@Override
	public void run() {
		try {
			if(this.fileLoaded) {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException interruptedException2) {
					interruptedException2.printStackTrace();
				}
			}

			this.status = "Getting level list..";
			URL uRL1 = new URL("http://" + "minecraft.net" + "/listmaps.jsp?user=" + this.minecraft.getUser().getName());
			BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(uRL1.openConnection().getInputStream()));
//			this.levels = bufferedReader4.readLine().split(";");
//			if(this.levels.length >= 5) {
////				this.setLevels(this.levels);
//				this.loaded = true;
//				return;
//			}

//			this.status = this.levels[0];
			this.finished = true;
		} catch (Exception exception3) {
			exception3.printStackTrace();
			this.status = "Failed to load levels";
			this.finished = true;
		}

	}

	@Override
	public final void tick() {
		super.tick();
		if(this.levelFile != null) {
//			this.loadLevel(this.levelFile);
			this.levelFile = null;
		}

	}
}