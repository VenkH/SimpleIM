package client.console;

import io.netty.channel.Channel;
import packet.req.LoginRequestPacket;

import java.util.Scanner;

import static util.IDUtil.randomUserId;

public class LoginConsoleCommand implements ConsoleCommand{
    @Override
    public void exec(Scanner scanner, Channel channel) {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        // 随机生成用户id
        loginRequestPacket.setUserId(randomUserId());
        System.out.print("输入用户名登录: ");
        String username = scanner.nextLine();
        loginRequestPacket.setUsername(username);

        // 密码使用默认的
        loginRequestPacket.setPassword("pwd");

        // 发送登录数据包
        channel.writeAndFlush(loginRequestPacket);
        waitForLoginResponse();
    }

    private static void waitForLoginResponse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }


}
