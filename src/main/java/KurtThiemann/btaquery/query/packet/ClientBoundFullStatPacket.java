package KurtThiemann.btaquery.query.packet;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ClientBoundFullStatPacket extends ClientBoundPacket {

    private final String hostName;
    private final String gameType;
    private final String gameId;
    private final String version;
    private final String plugins;
    private final String map;
    private final int numPlayers;
    private final int maxPlayers;
    private final int port;
    private final String hostIp;
    private final String[] players;

    public ClientBoundFullStatPacket(int sessionId, String hostName, String gameType, String gameId, String version, String plugins, String map, int numPlayers, int maxPlayers, int port, String hostIp, String[] players) {
        super(PacketType.STAT, sessionId);
        this.hostName = hostName;
        this.gameType = gameType;
        this.gameId = gameId;
        this.version = version;
        this.plugins = plugins;
        this.map = map;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
        this.port = port;
        this.hostIp = hostIp;
        this.players = players;
    }

    private void putKV(String key, String value, List<byte[]> target) {
        target.add(this.encodeString(key));
        target.add(this.encodeString(value));
    }

    @Override
    protected byte[] serializePayload() {
        List<byte[]> kv = new ArrayList<>();
        this.putKV("hostname", this.hostName, kv);
        this.putKV("gametype", this.gameType, kv);
        this.putKV("game_id", this.gameId, kv);
        this.putKV("version", this.version, kv);
        this.putKV("plugins", this.plugins, kv);
        this.putKV("map", this.map, kv);
        this.putKV("numplayers", String.valueOf(this.numPlayers), kv);
        this.putKV("maxplayers", String.valueOf(this.maxPlayers), kv);
        this.putKV("hostport", String.valueOf(this.port), kv);
        this.putKV("hostip", this.hostIp, kv);

        List<byte[]> players = new ArrayList<>();
        for (String player : this.players) {
            players.add(this.encodeString(player));
        }

        int length = 11 + kv.stream().mapToInt(b -> b.length).sum() + 11 + players.stream().mapToInt(b -> b.length).sum() + 1;
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.put(new byte[]{0x73, 0x70, 0x6C, 0x69, 0x74, 0x6E, 0x75, 0x6D, 0x00, (byte) 0x80, 0x00});

        for (byte[] b : kv) {
            buffer.put(b);
        }

        buffer.put((byte) 0);

        buffer.put(new byte[]{0x01, 0x70, 0x6C, 0x61, 0x79, 0x65, 0x72, 0x5F, 0x00, 0x00});

        for (byte[] b : players) {
            buffer.put(b);
        }
        buffer.put((byte) 0);

        return buffer.array();
    }
}
