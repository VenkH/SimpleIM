package packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static packet.Command.LOGIN_REQUEST;


@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}