package server.handler02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.req.LoginRequestPacket;
import packet.Packet;
import packet.PacketCodeC;
import packet.resp.LoginResponsePacket;

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

            LoginResponsePacket loginResponse = new LoginResponsePacket();
            loginResponse.setSuccess(true);
            loginResponse.setMessage("登录成功");
            loginResponse.setUsername(loginRequestPacket.getUsername());

            ByteBuf buf = PacketCodeC.INSTANCE.encode(loginResponse);
            ctx.channel().writeAndFlush(buf);
        }
    }

}
