package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import packet.req.CreateGroupRequestPacket;
import packet.resp.CreateGroupResponsePacket;
import util.SessionUtil;

import java.util.ArrayList;
import java.util.List;
import static util.IDUtil.randomGroupId;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        List<String> userIds = createGroupRequestPacket.getUserIds();
        ArrayList<String> userNameList = new ArrayList<>();

        // 新建channel分组
        DefaultChannelGroup channels = new DefaultChannelGroup(channelHandlerContext.executor());

        // 将在线的用户添加到channel分组
        channels.add(channelHandlerContext.channel());
        userNameList.add(SessionUtil.getSession(channelHandlerContext.channel()).getUserName());
        for (String id: userIds) {
            Channel channel = SessionUtil.getChannel(id);
            if (channel != null && SessionUtil.hasLogin(channel)) {
                channels.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        // 封装响应消息
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setUsernameList(userNameList);
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupID(randomGroupId());

        // 群发消息
        channels.writeAndFlush(createGroupResponsePacket);

        System.out.println("群聊创建成功，群聊id：" + createGroupResponsePacket.getGroupID());
        System.out.println("群聊中有：" + userNameList);
    }
}
