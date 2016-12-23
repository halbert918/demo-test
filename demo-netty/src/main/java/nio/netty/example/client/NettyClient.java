package nio.netty.example.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import nio.netty.example.support.HeartbeatTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2015/6/16.
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 6666;

    private volatile Channel channel;

    private Bootstrap bootstrap;

    public static void main(String... args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                            pipeline.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 6666).sync();
            if(future.isSuccess()) {
                //启动心跳检测线程
                ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
                service.scheduleAtFixedRate(new HeartbeatTask(future.channel()), 1, 3, TimeUnit.SECONDS);
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        System.out.println("channel operation complete");
                    }
                });
//                final Channel channel = future.channel();
//                Runnable worker = new Runnable() {
//                    @Override
//                    public void run() {
////                        String currentName = Thread.currentThread().getName();
////                        System.out.println(currentName);
////                        byte[] req = (currentName + ": hello server. ").getBytes();
////                        ByteBuf firstMessage = Unpooled.buffer(req.length);
////                        firstMessage.writeBytes(req);
//                        Request request = new Request();
//                        request.setEvent(null);
//                        channel.writeAndFlush(request);// 将请求消息发送给服务端
//                    }
//                };
//                Executor executor = Executors.newFixedThreadPool(2);
//                executor.execute(worker);
//                Thread.sleep(1000);
//                executor.execute(worker);
//                Thread.sleep(1000);
//                executor.execute(worker);
            }

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
