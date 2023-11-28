package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import packet.PacketCodeC;

/**
 * 继承了Netty提供的基于长度字段的拆包器
 * 在拆包之前添加一段逻辑：校验该报文是否合法的数据报文
 */
public class Spliter extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        // 先进行拆包
        ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);

        // 校验魔术位
        // 屏蔽非本协议的客户端
        if (byteBuf != null) {
            byteBuf.markReaderIndex();
            if (byteBuf.readInt() != PacketCodeC.MAGIC_NUMBER) {
                ctx.channel().close();
                System.out.println("协议错误，已断开连接！");
                return null;
            }
            byteBuf.resetReaderIndex();
        }

        return byteBuf;
    }
}

