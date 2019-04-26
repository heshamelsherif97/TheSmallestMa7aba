package NettyHTTP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


public class NettyHTTPServer {
    public static void start(int port) {
        System.out.println("starting");
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HTTPServerInitializer());
//            b.option(ChannelOption.SO_KEEPALIVE, true);
            Channel ch = b.bind(port).sync().channel();

            System.err.println("Server is listening on http://127.0.0.1:" + port + '/');
            ch.closeFuture().sync();



        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {

        int port;
        Properties conf = new Properties();
        try {

            File file = new File("./src/main/java/CommandPattern/userStories/config.properties");
            FileInputStream confLoc = new FileInputStream(file);
            conf.load(confLoc);
            port = Integer.parseInt(conf.getProperty("nettyPort"));
            //System.out.print(port + "test");
        }
        catch(Exception e)
        {
            System.out.print("Using Default Port, 8083");
            port = 8083;

        }
        NettyHTTPServer.start(port);
    }
}