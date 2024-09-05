package KurtThiemann.btaquery.query.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public abstract class ClientBoundPacket {
    private final PacketType type;

    private final int sessionId;

    public ClientBoundPacket(PacketType type, int sessionId) {
        this.type = type;
        this.sessionId = sessionId;
    }

    public PacketType getType() {
        return type;
    }

    public int getSessionId() {
        return sessionId;
    }

    protected abstract byte[] serializePayload();

    protected byte[] encodeString(String s) {
        return (s.replace("\0", "") + "\0").getBytes(StandardCharsets.UTF_8);
    }

    public byte[] serialize() {
        byte[] payload = serializePayload();
        ByteBuffer buffer = ByteBuffer.allocate(5 + payload.length);
        buffer.order(ByteOrder.BIG_ENDIAN);

        buffer.put(this.getType().getId());
        buffer.putInt(this.getSessionId());

        buffer.put(payload);

        return buffer.array();
    }
}
