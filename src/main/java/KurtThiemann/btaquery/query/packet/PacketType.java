package KurtThiemann.btaquery.query.packet;

public enum PacketType {
    HANDSHAKE((byte) 9),
    STAT((byte) 0);

    private final byte id;

    PacketType(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public static PacketType fromId(int id) throws QueryProtocolException {
        for (PacketType type : PacketType.values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        throw new QueryProtocolException("Unknown packet type: " + id);
    }
}
