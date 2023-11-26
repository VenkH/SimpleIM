package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.LoginRequestPacket;
import packet.Packet;
import packet.PacketCodeC;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        // 判断是否是登录请求数据包
        if (packet instanceof LoginRequestPacket) {
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            // 登录校验....

            System.out.println(new Date() + "：客户端建立连接");
            System.out.println(new Date() + "：【用户】 " + loginRequestPacket.getUsername() + " 登录成功");

            byte[] bytes = ("你好" + loginRequestPacket.getUsername() + "欢迎登录").getBytes(StandardCharsets.UTF_8);
            ByteBuf byteBuf1 = getByteBuf(ctx, bytes);
            ctx.channel().writeAndFlush(byteBuf1);
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, byte[] bytes) {
        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
