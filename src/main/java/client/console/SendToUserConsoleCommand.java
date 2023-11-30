package client.console;

import io.netty.channel.Channel;
import packet.req.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.print("向用户：");
        String userId = scanner.next();
        System.out.print("发送消息：");
        String message = scanner.next();

        MessageRequestPacket messageRequestPacket = new MessageRequestPacket(userId, message);
        channel.writeAndFlush(messageRequestPacket);
    }
}
