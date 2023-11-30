package packet.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import packet.Packet;

import static packet.Command.MESSAGE_RESPONSE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}

