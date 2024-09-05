package KurtThiemann.btaquery.query.packet;

public class ClientBoundHandshakePacket extends ClientBoundPacket {

    private final int challengeToken;

    public ClientBoundHandshakePacket(int sessionId, int challengeToken) {
        super(PacketType.HANDSHAKE, sessionId);
        this.challengeToken = challengeToken;
    }

    @Override
    protected byte[] serializePayload() {
        return (this.challengeToken + "\0").getBytes();
    }

    public int getChallengeToken() {
        return challengeToken;
    }
}
