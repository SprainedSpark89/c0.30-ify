package io.github.sprainedspark89.c0._30ify.client.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;

public class ClassicOptions {
	private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("c0_30ify.properties");
	public static final OptionInstance<Boolean> SHOW_FPS = OptionInstance.createBoolean("options.c030ify.showFps",
			false);

	static {
		Properties properties = new Properties();

		if (Files.exists(CONFIG_FILE)) {
			try (var reader = Files.newBufferedReader(CONFIG_FILE)) {
				properties.load(reader);
				SHOW_FPS.set(Boolean.parseBoolean(properties.getProperty("showFps", "false")));
			} catch (IOException e) {
				System.err.println("Failed to load c0_30ify settings: " + e.getMessage());
			}
		}
	}

	private static String addPluses(String name, int renderDistance, long current) {
		long next = current << 2;
		long pluses = renderDistance - current / 16;

		StringBuilder builder = new StringBuilder(name);

		for (long i = 0; i < pluses; i++) {
			builder.append('+');
		}

		return builder.toString();
	}

	private static OptionInstance<Double>[] getAllSoundOptionsExceptMasterAndMusic(Options options) {
		return Arrays.stream(SoundSource.values()).filter(s -> s != SoundSource.MASTER && s != SoundSource.MUSIC)
				.map(options::getSoundSourceOptionInstance).toArray(OptionInstance[]::new);
	}

	public static OptionInstance[] getMainOptionsMenu(Options options) {
		return new OptionInstance[] { options.getSoundSourceOptionInstance(SoundSource.MUSIC),
				options.getSoundSourceOptionInstance(SoundSource.BLOCKS), options.invertMouseY(), SHOW_FPS, // no
				// show
				// fps,
				// might
				// replace
				// later
				options.renderDistance(), options.bobView(), options.enableVsync(), // no 3d anaglyph, not
				// reimplementing it
				options.framerateLimit() };
	}

	public final static String getMessage(OptionInstance option, Options options) { // need to add things, change
																					// things, or remove things...

		if (option == options.getSoundSourceOptionInstance(SoundSource.MUSIC)) {
			return "Music: " + ((double) option.get() != 0 ? "ON" : "OFF");
		} else if (option == options.getSoundSourceOptionInstance(SoundSource.BLOCKS)) {
			return "Sound: " + ((double) option.get() != 0 ? "ON" : "OFF");
		} else if (option == options.invertMouseY()) {
			return "Invert mouse: " + ((boolean) option.get() ? "ON" : "OFF");
		} else if (option == SHOW_FPS) { // placeholder
			return "Show FPS: " + ((boolean) option.get() ? "ON" : "OFF");
		} else if (option == options.renderDistance()) {
			int renderDistance = (int) option.get();
			String renderString = "FUCKING BLIND";
			if (renderDistance >= 8388608 / 16) {
				renderString = "INSANE";
				long l = 8388608;

				while (renderDistance >= (l << 2) / 16) {
					l <<= 2;

					if (renderString.endsWith("ER")) {
						renderString = renderString.substring(0, renderString.length() - 2) + "EST";
					} else {
						renderString += "ER";
					}
				}

				renderString = renderString.replace("EER", "ER");

				renderString = addPluses(renderString, renderDistance, l);

			} else if (renderDistance >= 2097152 / 16) {
				renderString = addPluses("JESUS CHRIST", renderDistance, 2097152);
			} else if (renderDistance >= 524288 / 16) {
				renderString = addPluses("CHRIST", renderDistance, 524288);
			} else if (renderDistance >= 131072 / 16) {
				renderString = addPluses("LUDICHRIST", renderDistance, 131072);
			} else if (renderDistance >= 32768 / 16) {
				renderString = addPluses("LUDICROUS", renderDistance, 32768);
			} else if (renderDistance >= 8192 / 16) {
				renderString = addPluses("ABSURD", renderDistance, 8192);
			} else if (renderDistance >= 2048 / 16) {
				renderString = addPluses("EXTREME", renderDistance, 2048);
			} else if (renderDistance >= 512 / 16) {
				renderString = addPluses("FAR", renderDistance, 512);
			} else if (renderDistance >= 128 / 16) {
				renderString = addPluses("NORMAL", renderDistance, 128);
			} else if (renderDistance >= 32 / 16) {
				renderString = addPluses("SHORT", renderDistance, 32);
			} else if (renderDistance >= 8 / 16) {
				renderString = addPluses("TINY", renderDistance, 8);
			} else if (renderDistance >= 2 / 16) {
				renderString = addPluses("MINUSCULE", renderDistance, 2);
			}

			return "Render distance: " + renderString;
		} else if (option == options.bobView()) {
			return "View bobbing: " + ((boolean) option.get() ? "ON" : "OFF");
		} else if (option == options.enableVsync()) {
			return "Sync framerate: " + ((boolean) option.get() ? "ON" : "OFF");
		} else if (option == options.framerateLimit()) {
			return "Limit framerate: " + ((int) option.get() < 260 ? "ON" : "OFF");
		}

		return "";
	}

	public static void handleOptionChange(OptionInstance<?> option, Options options) {
		if (option == options.getSoundSourceOptionInstance(SoundSource.BLOCKS)) {
			OptionInstance<Double>[] sounds = getAllSoundOptionsExceptMasterAndMusic(options);
			if ((double) option.get() != 0) {
				for (OptionInstance<Double> o : sounds) {
					o.set(0D);
				}
			} else {
				for (OptionInstance<Double> o : sounds) {
					o.set(1D);
				}
			}
		} else if (option == options.getSoundSourceOptionInstance(SoundSource.MUSIC)) {
			OptionInstance<Double> o = (OptionInstance<Double>) option;
			if ((double) option.get() != 0) {
				o.set(0D);
			} else {
				o.set(1D);
			}
		} else {
			if (option.get() instanceof Integer) {
				OptionInstance<Integer> o = (OptionInstance<Integer>) option;
				OptionInstance.ValueSet<Integer> values = o.values();

				if (values instanceof OptionInstance.IntRangeBase range) {
					Integer next = range.next(o.get()).orElse(range.minInclusive());
					o.set(next);
				}
			} else if (option.get() instanceof Boolean) {
				OptionInstance<Boolean> o = (OptionInstance<Boolean>) option;
				o.set(!o.get());
			} else if (option.values() instanceof OptionInstance.Enum<?> e) {
				List<?> list = e.values();
				int index = list.indexOf(option.get());
				index = (index + 1) % list.size();

				((OptionInstance<Object>) option).set(list.get(index));
			}
		}
	}

	public static void saveSettings() {
		Properties properties = new Properties();
		properties.setProperty("showFps", Boolean.toString(SHOW_FPS.get()));

		try {
			Files.createDirectories(CONFIG_FILE.getParent());

			try (var writer = Files.newBufferedWriter(CONFIG_FILE)) {
				properties.store(writer, "c0_30ify settings");
			}
		} catch (IOException e) {
			System.err.println("Failed to save c0_30ify settings: " + e.getMessage());
		}
	}
}
