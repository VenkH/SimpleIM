package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.resp.LoginResponsePacket;
import session.Session;
import util.SessionUtil;

import java.util.Date;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) {
        if (loginResponsePacket.isSuccess()) {
            Session session = new Session(loginResponsePacket.getUserId(), loginResponsePacket.getUsername());
            SessionUtil.bindSession(session, ctx.channel());
            System.out.println(new Date() + ": 用户【" + loginResponsePacket.getUserId() + "】登录成功");
        } else {
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getMessage());
        }
    }
}