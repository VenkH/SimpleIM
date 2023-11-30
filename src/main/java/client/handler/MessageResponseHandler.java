package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.resp.MessageResponsePacket;

import java.util.Date;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) {
        System.out.println(new Date() + ": 收到用户【" + messageResponsePacket.getFromUserId() + "】的消息-> " + messageResponsePacket.getMessage());
    }
}