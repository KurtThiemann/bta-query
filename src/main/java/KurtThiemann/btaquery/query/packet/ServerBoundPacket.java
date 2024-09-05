package KurtThiemann.btaquery.query.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ServerBoundPacket {
    private final byte[] buffer;

    private final PacketType type;

    private final int sessionId;

    private final byte[] payload;

    public static ServerBoundPacket fromBuffer(byte[] buffer, int length) throws QueryProtocolException {
        switch (PacketType.fromId(buffer[2])) {
            case HANDSHAKE:
                return new ServerBoundHandshakePacket(buffer, length);
            case STAT:
                return new ServerBoundStatPacket(buffer, length);
            default:
                throw new QueryProtocolException("Invalid packet type");
        }
    }

    public ServerBoundPacket(byte[] buffer, int length) throws QueryProtocolException {
        this.buffer = buffer;

        ByteBuffer bb = ByteBuffer.wrap(buffer);
        bb.order(ByteOrder.BIG_ENDIAN);

        short magic = bb.getShort();
        if (magic != (short) 0xFEFD) {
            throw new QueryProtocolException("Invalid magic");
        }

        this.type = PacketType.fromId(bb.get());
        this.sessionId = bb.getInt();

        this.payload = new byte[length - 7];
        bb.get(this.payload);
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public PacketType getType() {
        return type;
    }

    public int getSessionId() {
        return sessionId;
    }

    public byte[] getPayload() {
        return payload;
    }
}
