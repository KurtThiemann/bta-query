package KurtThiemann.btaquery;

import KurtThiemann.btaquery.query.QueryServer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.net.PropertyManager;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class BtaQuery implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "btaquery";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private QueryServer queryServer;

    @Override
    public void onInitialize() {
        LOGGER.info("BtaQuery initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		if (FabricLoader.getInstance().getEnvironmentType() != EnvType.SERVER) {
			LOGGER.error("BtaQuery is a server-side mod and should not be installed on the client.");
			return;
		}

		MinecraftServer server = MinecraftServer.getInstance();
		PropertyManager props = server.propertyManager;

		boolean queryEnabled = props.getBooleanProperty("enable-query", false);
		if (!queryEnabled) {
			LOGGER.info("Query server is disabled.");
			return;
		}

		int queryPort = props.getIntProperty("query.port", 25565);
		String queryAddressString = props.getStringProperty("server-ip", "");

		InetAddress inetAddress = null;
		if (!queryAddressString.isEmpty()) {
			try {
				inetAddress = InetAddress.getByName(queryAddressString);
			} catch (UnknownHostException ignore) {
			}
		}

		try {
			this.queryServer = new QueryServer(server, queryPort, inetAddress);
		} catch (SocketException e) {
			LOGGER.error("Failed to start query server", e);
			return;
		}

		LOGGER.info("Query server started on {}:{}", queryAddressString, queryPort);
	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}
}
