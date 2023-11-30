package packet.resp;

import lombok.Data;
import packet.Command;
import packet.Packet;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {
    private List<String> usernameList;
    private boolean success;
    private String groupID;

    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
