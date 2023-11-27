package server.handler03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.PacketCodeC;
import packet.req.LoginRequestPacket;
import packet.resp.LoginResponsePacket;

import java.util.Date;

/**
 * SimpleChannelInboundHandler 与 ChannelInboundHandlerAdapter类似
 * 区别在于 SimpleChannelInboundHandler 传入一个泛型，是指定要处理的消息类型
 * 消息类型的转换在父类中都帮我们处理了，因此我们只关注与消息的处理逻辑
 */
public class LoginServerHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(new Date() + "：客户端建立连接");
        System.out.println(new Date() + "：【用户】 " + loginRequestPacket.getUsername() + " 登录成功");

        LoginResponsePacket loginResponse = new LoginResponsePacket();
        loginResponse.setSuccess(true);
        loginResponse.setMessage("登录成功");
        loginResponse.setUsername(loginRequestPacket.getUsername());

        // PacketEncoder 会自动编码
        channelHandlerContext.channel().writeAndFlush(loginResponse);
    }
}
