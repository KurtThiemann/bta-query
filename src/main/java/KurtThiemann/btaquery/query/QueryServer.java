package KurtThiemann.btaquery.query;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.net.PropertyManager;
import net.minecraft.server.MinecraftServer;

import java.net.InetAddress;
import java.net.SocketException;

public class QueryServer extends AbstractQueryServer {
	private final MinecraftServer server;
	private final PropertyManager properties;

	public QueryServer(MinecraftServer server, int port, InetAddress address) throws SocketException {
		super(port, address);
		this.server = server;
		this.properties = server.propertyManager;
	}

	@Override
	protected int getMinecraftServerPort() {
		return this.properties.getIntProperty("server-port", 25565);
	}

	@Override
	protected String getMinecraftServerIp() {
		String address = this.properties.getStringProperty("server-ip", "");
		if (address.isEmpty()) {
			address = "0.0.0.0";
		}
		return address;
	}

	@Override
	protected String getMotd() {
		return this.server.motd;
	}

	@Override
	protected int getPlayersOnline() {
		return this.server.playerList.playerEntities.size();
	}

	@Override
	protected int getMaxPlayers() {
		return this.server.maxPlayers;
	}

	@Override
	protected String[] getPlayerList() {
		return this.server.playerList.playerEntities.stream().map(player -> player.username).toArray(String[]::new);
	}

	@Override
	protected String getPluginInfo() {
		String[] mods = FabricLoader.getInstance().getAllMods().stream().map(mod -> mod.getMetadata().getId() + " " + mod.getMetadata().getVersion().getFriendlyString()).toArray(String[]::new);
		return "Better than Adventure Babric: " + String.join("; ", mods);
	}

	@Override
	protected String getGameId() {
		return "MINECRAFT";
	}

	@Override
	protected String getGameVersion() {
		return this.server.getMinecraftVersion();
	}
}
