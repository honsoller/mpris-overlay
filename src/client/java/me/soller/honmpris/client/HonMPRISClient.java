package me.soller.honmpris.client;

import me.soller.honmpris.HonMPRIS;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

public class HonMPRISClient implements ClientModInitializer {

	private static final MPRISService mpris = new MPRISService();

	@Override
	public void onInitializeClient() {

		Config.load();

		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				HonMPRIS.id("player"),
				(context, tickCounter) -> {

					Song song = mpris.getSong();

					if (song != null) {
						HudRenderer.render(context, song);
					}
				}
		);
	}
}