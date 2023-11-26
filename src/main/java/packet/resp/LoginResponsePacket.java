package packet.resp;

import lombok.Data;
import packet.Packet;

import static packet.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    /**
     * 登录成功标识
     */
    private boolean success;

    /**
     * 其他响应信息
     */
    private String message;

    /**
     * 用户名
     */
    private String username;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
