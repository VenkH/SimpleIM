package client.handler02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.Packet;
import packet.req.LoginRequestPacket;
import packet.PacketCodeC;
import packet.resp.LoginResponsePacket;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 客户端登录
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LoginRequestPacket loginRequest = new LoginRequestPacket();
        loginRequest.setUserId(randomUserId());
        loginRequest.setUsername("vent");
        loginRequest.setPassword("pwd");

        ByteBuf encode = PacketCodeC.INSTANCE.encode(loginRequest);

        System.out.println(new Date() + "：客户端登录");
        ctx.channel().writeAndFlush(encode);
    }

    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet decode = PacketCodeC.INSTANCE.decode(byteBuf);
        if (decode instanceof LoginResponsePacket) {
            LoginResponsePacket responsePacket = (LoginResponsePacket) decode;
            System.out.println(new Date() + ": 客户端读到数据 -> " + responsePacket);
        }
    }
}
