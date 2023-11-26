package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SimpleClient {
    private static final int MAX_RETRY = 5;
    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                // 1.指定线程模型
                .group(workerGroup)
                // 2.指定 IO 类型为 NIO
                .channel(NioSocketChannel.class)
                // 3.IO 处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });
        // 4.建立连接
        connect(bootstrap, "localhost", 9000, MAX_RETRY);
    }

    /**
     * 建立连接，连接失败再次尝试
     * @param bootstrap 启动引导类
     * @param host 主机号
     * @param port 端口号
     */
    private static void connect(Bootstrap bootstrap, String host, int port) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else {
                System.err.println("连接失败，开始重连");
                connect(bootstrap, host, port);
            }
        });
    }


    /**
     * 建立连接，连接失败再次尝试，限制重连次数
     * 重连间隔时间按照2的幂次方增长
     * @param bootstrap 启动引导类
     * @param host 主机号
     * @param port 端口号
     * @param retry 重连次数
     */
    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {

        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功!");
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");

                bootstrap
                        // 拿到bootstrap配置参数的抽象
                         .config()
                        // 拿到EventLoopGroup，也就是WorkerGroup
                         .group()
                        // 调用定时任务
                         .schedule(
                                () -> connect(bootstrap, host, port, retry - 1)
                                , delay
                                , TimeUnit.SECONDS);
            }
        });
    }


}
