package packet.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import packet.Packet;

import static packet.Command.LOGIN_REQUEST;


@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}