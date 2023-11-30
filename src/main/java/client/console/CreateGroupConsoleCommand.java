package client.console;

import io.netty.channel.Channel;
import packet.req.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    private static final String USERID_SPLIT = ",";
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("【创建群聊】输入用户id列表：");
        String ids = scanner.next();
        String[] split = ids.split(USERID_SPLIT);
        List<String> idList = Arrays.asList(split);
        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket(idList);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
