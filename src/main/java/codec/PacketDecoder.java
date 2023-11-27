package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import packet.PacketCodeC;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 收到消息时，对byteBuf解码
        // -- Netty 4.1.6 Final 版本ByteBuf默认是用堆外内存，需手动释放
        // 解码完成后会自动将ByteBuf的内存释放，避免造成内存泄露
        list.add(PacketCodeC.INSTANCE.decode(byteBuf));
    }
}
