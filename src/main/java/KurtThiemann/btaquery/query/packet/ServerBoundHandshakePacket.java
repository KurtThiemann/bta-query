package KurtThiemann.btaquery.query.packet;

public class ServerBoundHandshakePacket extends ServerBoundPacket {
    public ServerBoundHandshakePacket(byte[] buffer, int length) throws QueryProtocolException {
        super(buffer, length);
    }
}
