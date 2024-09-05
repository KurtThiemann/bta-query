package KurtThiemann.btaquery.query.packet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ServerBoundStatPacket extends ServerBoundPacket {
    private final boolean full;

    private final int challenge;

    public ServerBoundStatPacket(byte[] buffer, int length) throws QueryProtocolException {
        super(buffer, length);

        byte[] payload = this.getPayload();

         /*for (int i = 0; i < payload.length; i++) {
             System.out.println("payload[" + i + "] = " + payload[i]);
         }*/

        ByteBuffer bb = ByteBuffer.wrap(payload);
        bb.order(ByteOrder.BIG_ENDIAN);
        this.challenge = bb.getInt();
        this.full = payload.length == 8;
    }

    public boolean isFull() {
        return full;
    }

    public int getChallenge() {
        return challenge;
    }
}
