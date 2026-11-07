package io.github.sprainedspark89.c0._30ify.client;

import com.mojang.blaze3d.platform.InputConstants;

import io.github.sprainedspark89.c0._30ify.C030Ify;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class C030IfyClient implements ClientModInitializer {
	private static final KeyMapping.Category CATEGORY = KeyMapping.Category
			.register(Identifier.fromNamespaceAndPath(C030Ify.MOD_ID, "key.categories.c030ify"));
	public static boolean classicGui = true;
	public static String hoveredUsername = null;

	public static KeyMapping toggleC030GUI;

	@Override
	public void onInitializeClient() {
		toggleC030GUI = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.c030ify.classic_gui",
				InputConstants.Type.KEYBOARD, InputConstants.KEY_C, CATEGORY));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (toggleC030GUI.consumeClick()) {
				classicGui = !classicGui;
			}
		});

		FabricLoader.getInstance().getModContainer(C030Ify.MOD_ID).ifPresent(modContainer -> {

			ResourceManagerHelper.registerBuiltinResourcePack(
					// The first argument is the ID of your pack
					Identifier.fromNamespaceAndPath(C030Ify.MOD_ID, "c0.30-ify"), modContainer,
					Component.literal("c0.30-ify"), // Display name in menu
					ResourcePackActivationType.DEFAULT_ENABLED // NORMAL means optional. Users must enable it manually
			);
		});

	}
}