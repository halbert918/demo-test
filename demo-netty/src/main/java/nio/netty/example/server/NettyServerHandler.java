package nio.netty.example.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import nio.netty.example.Request;
import nio.netty.example.Response;
import nio.netty.example.support.HeartbeatTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2015/6/16.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private Map<Channel, Long> channelMap = new ConcurrentHashMap<>();

    private HeartbeatTask handler;

    public NettyServerHandler() {
        super(false);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(IdleStateEvent.class.isAssignableFrom(evt.getClass())){
            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println("channel触发最大空闲事件:" + event.state());
            logger.info("channel触发最大空闲事件:{}", event.state());
            if(event.state() == IdleState.ALL_IDLE) {
                ctx.channel().close();
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server:" + msg.toString());
//        channelReadTest(ctx, msg);
        if(msg instanceof Request) {
            Request request = (Request) msg;
            Response response = new Response(request.getId());
            //处理心跳事件
            if(request.isHeartbeat()) {
                response.setEvent(null);
                response.setStatus(Response.OK);
                ChannelFuture future = ctx.writeAndFlush(response);
            } else { //回写正常请求数据
                response.setStatus(Response.OK);
                response.setResult(request.getData());
            }
            ChannelFuture future = ctx.writeAndFlush(response);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    logger.info("回写客户端完成，心跳检测:{}", response.isHeartbeat());
                    assert future == future;
                }
            });
            return;
        }
//        super.channelRead(ctx, msg);
    }

    /**
     * 连接创建时触发
     * 不需先接收消息，直接可以发送消息
     * @param ctx
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
//        final ByteBuf time = ctx.alloc().buffer(4);
//        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//        Channel channel = ctx.channel();
//        System.out.println("=========>" + channel.isWritable() + "    " + channel.isActive() + "  " + channel.isOpen());
//        final ChannelFuture f = channel.writeAndFlush(time);
////        final ChannelFuture f = ctx.writeAndFlush(time);
//        //添加监听，消息发送完成后调用
//        f.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) {
//                assert f == future;
//                ctx.close();
//            }
//        });
    }

    /**
     * 断开连接时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ctx.channel().closeFuture().sync();
        ctx.channel().close();
//        list.remove(ctx.channel());
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    //////////////////////////////////////////////////////////////////////////////////

    protected void channelReadTest(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.print("接收到数据:");
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }
            System.out.println();
            // 回写客户端
            final ByteBuf time = ctx.alloc().buffer(4);
            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
            final ChannelFuture f = ctx.writeAndFlush(time);
            //添加监听，消息发送完成后调用
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    assert f == future;
                }
            });
        } finally {
            //write时自动释放
//            ReferenceCountUtil.release(msg);
        }
    }

}
