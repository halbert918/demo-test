package nio.netty.example.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import nio.netty.example.Request;
import nio.netty.example.Response;
import nio.netty.example.server.NettyServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by Administrator on 2015/6/16.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    public NettyClientHandler() {
        super(false);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //channelReadTest(ctx, msg);
        if(msg instanceof Response) {
            Response response = (Response) msg;
            if (response.isHeartbeat()) {
                System.out.println("client:心跳检测");
                logger.info("收到服务端心跳数据:{}", response.getId());
            } else {
                System.out.println("client:" + msg.toString());
                logger.info("收到服务端返回处理数据:{}", response.getResult());
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        byte[] req = "hello server".getBytes();
//        ByteBuf firstMessage = Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
//        ctx.writeAndFlush(firstMessage);// 将请求消息发送给服务端
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        //TODO reconnect
        ctx.fireChannelInactive();
        System.out.println("channelInactive:" + ctx.channel().isActive());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


    ///////////////////////////////////////////////////////////////////////////////

    protected void channelReadTest(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
        } finally {
            m.release();
//            ctx.close();
        }
    }

}
