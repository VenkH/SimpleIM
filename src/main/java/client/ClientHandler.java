package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.LoginRequestPacket;
import packet.PacketCodeC;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端登录
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LoginRequestPacket loginRequest = new LoginRequestPacket();
        loginRequest.setUserId((int) System.currentTimeMillis());
        loginRequest.setUsername("vent");
        loginRequest.setPassword("pwd");

        ByteBuf encode = PacketCodeC.INSTANCE.encode(loginRequest);

        System.out.println(new Date() + "：客户端登录");
        ctx.channel().writeAndFlush(encode);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(StandardCharsets.UTF_8));
    }
}
