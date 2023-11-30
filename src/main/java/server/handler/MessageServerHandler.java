package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.req.MessageRequestPacket;
import packet.resp.MessageResponsePacket;
import session.Session;
import util.SessionUtil;

import java.util.Date;

public class MessageServerHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket messageRequestPacket) throws Exception {
        // 拿到发送者会话信息
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        // 构造响应
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        // 拿到接收者会话Channel
        Channel channel = SessionUtil.getChannel(messageRequestPacket.getToUserId());

        // 发送消息
        if (channel != null && SessionUtil.hasLogin(channel)) {
            channel.writeAndFlush(messageResponsePacket);
        } else {
            System.out.println(new Date() + "用户【" + messageRequestPacket.getToUserId() + "】已下线");
        }

        System.out.println(new Date() + ": 收到用户【" + session.getUserId() + "】消息 转发至用户【" + messageRequestPacket.getToUserId() + "】");
    }
}
