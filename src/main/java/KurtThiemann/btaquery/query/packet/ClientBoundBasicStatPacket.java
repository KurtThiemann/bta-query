package KurtThiemann.btaquery.query.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ClientBoundBasicStatPacket extends ClientBoundPacket {

    private final String motd;
    private final String gameType;
    private final String map;
    private final int playersOnline;
    private final int maxPlayers;
    private final int port;
    private final String host;

    public ClientBoundBasicStatPacket(int sessionId, String motd, String gameType, String map, int playersOnline, int maxPlayers, int port, String host) {
        super(PacketType.STAT, sessionId);
        this.motd = motd;
        this.gameType = gameType;
        this.map = map;
        this.playersOnline = playersOnline;
        this.maxPlayers = maxPlayers;
        this.port = port;
        this.host = host;
    }

    @Override
    protected byte[] serializePayload() {
        byte[] motd = this.encodeString(this.motd);
        byte[] gameType = this.encodeString(this.gameType);
        byte[] map = this.encodeString(this.map);
        byte[] playersOnline = this.encodeString(Integer.toString(this.playersOnline));
        byte[] maxPlayers = this.encodeString(Integer.toString(this.maxPlayers));
        byte[] host = this.encodeString(this.host);

        ByteBuffer buffer = ByteBuffer.allocate(motd.length + gameType.length + map.length + playersOnline.length + maxPlayers.length + 2 + host.length);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        buffer.put(motd);
        buffer.put(gameType);
        buffer.put(map);
        buffer.put(playersOnline);
        buffer.put(maxPlayers);
        buffer.putShort((short) this.port);
        buffer.put(host);

        return buffer.array();
    }
}
