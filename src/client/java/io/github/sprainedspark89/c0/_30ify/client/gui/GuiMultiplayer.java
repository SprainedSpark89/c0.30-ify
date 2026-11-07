package io.github.sprainedspark89.c0._30ify.client.gui;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.security.GeneralSecurityException;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class GuiMultiplayer extends Screen {
	public static byte[] convertCodeCiphered(String string0) {
		byte[] b1 = new byte[string0.length() / 2];

		for(int i2 = 0; i2 < b1.length; ++i2) {
			b1[i2] = (byte)("0123456789ABCDEF".indexOf(string0.charAt(i2 * 2 + 1)) + "0123456789ABCDEF".indexOf(string0.charAt(i2 * 2 + 0)) * 16);
		}

		return b1;
	}
	Button connect;
	private boolean inviteCodeMode;
	byte[] ipCode = convertCodeCiphered("2E411B61822594868E923036250D06BD939447A5E3759F60D4F99C751EA01B79"); // the password isn't known, but one of the inputs is likely: OK:94.254.71.120:53100
	private String ipServer = "";
	private String ipText = "";
	private Screen parentScreen;
	private ServerList servers;

	private int updateCounter = 0;

	public GuiMultiplayer(Screen guiScreen1, boolean cipher) {
		super(Component.literal("Play Multi player"));
		this.parentScreen = guiScreen1;
		this.inviteCodeMode = cipher; // custom stuff
	}

	private Cipher a(int i1, String string2) throws GeneralSecurityException {
		Random random3 = new Random(16L);
		byte[] b4 = new byte[8];
		random3.nextBytes(b4);
		PBEParameterSpec pBEParameterSpec5 = new PBEParameterSpec(b4, 5);
		SecretKey secretKey6 = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(new PBEKeySpec(string2.toCharArray()));
		Cipher cipher7 = Cipher.getInstance("PBEWithMD5AndDES");
		cipher7.init(i1, secretKey6, pBEParameterSpec5);
		return cipher7;
	}

//	protected void actionPerformed(Button guiButton1) {
//		if(guiButton1.enabled) {
//			if(guiButton1.id == 1) {
//
//			} else if(guiButton1.id == 0) {
//
//			}
//
//		}
//	}

	@Override
	public boolean charTyped(CharacterEvent event) {
	    String c = event.codepointAsString();
	    int max = inviteCodeMode ? 16 : 255;
	    if ("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 ,.:-_\'*!\"#%/()=+?[]{}<>".indexOf(c) >= 0 && this.ipText.length() < max) {
	    	this.ipText += c;
	    }

	    connect.active = updateConnectionTarget();
	    return true;
	}

	private boolean cipherCode(String string1) {
		this.ipServer = "";

		try {
			Cipher cipher2 = this.a(2, string1);
			DataInputStream dataInputStream3 = new DataInputStream(new CipherInputStream(new ByteArrayInputStream(this.ipCode), cipher2));

			boolean z4;
			try {
				this.ipServer = dataInputStream3.readUTF();
				z4 = true;
			} finally {
				dataInputStream3.close();
			}

			return z4;
		} catch (Exception exception9) {
			return false;
		}
	}

	@Override
	public void extractBackground(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
		graphics.fillGradient(0, 0, this.width, this.height, 1610941696, -1607454624);
	}

//	protected void keyTyped(char c1, int i2) {
//		if(i2 == 14 && this.ipText.length() > 0) {
//			this.ipText = this.ipText.substring(0, this.ipText.length() - 1);
//		}
//
//		if(" !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb".indexOf(c1) >= 0 && this.ipText.length() < 16) {
//			this.ipText = this.ipText + c1;
//		}
//
//		connect.active = updateConnectionTarget();
//	}

	@Override
	public void extractRenderState(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
//		this.drawDefaultBackground();
		if(!inviteCodeMode) {
			graphics.centeredText(this.font, "External level", this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF + 0xFF000000);
			graphics.text(this.font, "Enter the address of the level", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880 + 0xFF000000);
			graphics.text(this.font, "you'd like to connect to.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 9, 10526880 + 0xFF000000);
//			graphics.text(this.font, "Public (and buggy) testing is coming in a few weeks or so.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 18, 10526880 + 0xFF000000);
//			graphics.text(this.font, "If you\'ve received an invite code, enter the code below:", this.width / 2 - 140, this.height / 4 - 60 + 60 + 45, 10526880 + 0xFF000000);
		} else {
			graphics.centeredText(this.font, "Level code", this.width / 2, this.height / 4 - 60 + 20, 0xFFFFFF + 0xFF000000);
			graphics.text(this.font, "Enter the level code below.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 0, 10526880 + 0xFF000000);
			graphics.text(this.font, "If the code is valid, you'll be", this.width / 2 - 140, this.height / 4 - 60 + 60 + 9, 10526880 + 0xFF000000);
			graphics.text(this.font, "connected to the corresponding level.", this.width / 2 - 140, this.height / 4 - 60 + 60 + 18, 10526880 + 0xFF000000);
//			graphics.text(this.font, "If you\'ve received an invite code, enter the code below:", this.width / 2 - 140, this.height / 4 - 60 + 60 + 45, 10526880 + 0xFF000000);
		}
		int i4 = this.width / 2 - 100;
		int i5 = this.height / 4 - 10 + 50 + 18;
		short s6 = 200;
		byte b7 = 20;
		graphics.fill(i4 - 1, i5 - 1, i4 + s6 + 1, i5 + b7 + 1, -6250336);
		graphics.fill(i4, i5, i4 + s6, i5 + b7, 0xFF000000);
		String display;
		if(this.inviteCodeMode) {
			display = "";

			for(char c : this.ipText.toCharArray()) {
				display += "#";
			}
		} else {
			display = this.ipText;
		}
		graphics.text(this.font, display + (this.updateCounter / 6 % 2 == 0 ? "_" : ""), i4 + 4, i5 + (b7 - 8) / 2, 14737632 + 0xFF000000);
		super.extractRenderState(graphics, mouseX, mouseY, a);
	}

	@Override
	public final void init() {
		this.minecraft.onTextInputFocusChange(this, true);
		this.servers = new ServerList(this.minecraft);
		this.servers.load();
//		this.controlList.clear();
		connect = this
				.addRenderableWidget(Button
						.builder(Component.literal("Connect"),
								var1 -> {
									System.out.println(this.ipServer);
									String[] string2 = this.ipServer.split(":");
									if(string2[0].equals("OK")) {
										ServerData serverData = this.servers.get(string2[1] + ":" + string2[2]);
										ServerData editingServer = new ServerData(I18n.get("selectServer.defaultName"), "", ServerData.Type.OTHER);
										editingServer.ip = string2[1] + ":" + string2[2];
										ServerData join;
										if (serverData == null) {
											this.servers.add(editingServer, true);
											this.servers.save();
											join=(editingServer);
										} else {
											join=(serverData);
										}
//										this.mc.displayGuiScreen(new GuiConnecting(this.mc, string2[1], Integer.parseInt(string2[2])));
										ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(string2[1] + ":" + string2[2]), join, false, null);
									}
								}
									)
						.bounds(this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20).build());
//		this.controlList.add(new Button(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Connect"));
		Button cancel = this
				.addRenderableWidget(Button
						.builder(Component.literal("Cancel"),
								var1 ->
						this.minecraft.gui.setScreen(this.parentScreen))
						.bounds(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20).build());
//		this.controlList.add(new Button(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
		connect.active = false;
//		((Button)this.controlList.get(0)).enabled = false;
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
	    switch (event.key()) {
	        case InputConstants.KEY_BACKSPACE -> {
	            if (!ipText.isEmpty()) {
	            	this.ipText = this.ipText.substring(0, this.ipText.length() - 1);
	            }
	        }

//	        case GLFW.GLFW_KEY_ENTER -> {
//	            if (save.active) {
//	                save.onPress(event);
//	            }
//	        }

	        default -> {
	            return super.keyPressed(event);
	        }
	    }

	    connect.active = updateConnectionTarget();
	    return true;
	}

	@Override
	public void tick() {
		++this.updateCounter;
	}

	private boolean updateConnectionTarget() {
	    if (inviteCodeMode) {
	        return cipherCode(ipText.trim());
	    }

	    ipServer = ipText.trim();

	    return !ipServer.isBlank();
	}

	@Override
	public void removed() {
	    this.minecraft.onTextInputFocusChange(this, false);
	    super.removed();
	}
}
