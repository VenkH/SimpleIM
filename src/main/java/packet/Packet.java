package packet;

import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private final Byte version = 1;

    /**
     * 返回数据包的指令
     * @return 指令
     */
     public abstract Byte getCommand();
 }

