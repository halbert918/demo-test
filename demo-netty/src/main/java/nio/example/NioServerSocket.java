package nio.example;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by admin on 2016/12/19.
 */
public class NioServerSocket {

    private final int HOST = 6666;

    private Selector selector;

    public NioServerSocket() {
        try {
            //创建Channel通道
            ServerSocketChannel ssChannel = ServerSocketChannel.open();
            ssChannel.socket().bind(new InetSocketAddress(HOST));
            ssChannel.configureBlocking(false);   //设置非阻塞模式
            //开启Channel管理器
            selector = Selector.open();
            //注册接收客户端请求事件
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAccept() {
        try {
            while (true) {
                selector.select();
                Iterator iterator = selector.selectedKeys().iterator();
                while ((iterator.hasNext())) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    //移除已监听到的事件
                    iterator.remove();
                    if(key.isAcceptable()) {
                        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
                        SocketChannel sChannel = ssChannel.accept();
                        sChannel.configureBlocking(false);
                        sChannel.write(ByteBuffer.wrap(new String("服务端已收到连接请求...").getBytes()));
                        //注册客户端请求读事件
                        sChannel.register(selector, SelectionKey.OP_READ);
                    } else if(key.isReadable()) {
                        SocketChannel sChannel = (SocketChannel) key.channel();
                        //创建读缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(64);
                        int bufferRead = sChannel.read(buffer); //将channel中数据读取到buffer缓冲
                        if(-1 != bufferRead) {
                            String msg = new String(buffer.array()).trim();
                            System.out.println("server：" + msg);
                            if ("bye".equals(msg)) {
                                sChannel.write(ByteBuffer.wrap(msg.getBytes()));
                                Thread.sleep(1000);
                                sChannel.close();
//                                selector.close();
                            } else {
                                sChannel.write(ByteBuffer.wrap(("发送数据到客户端" + msg).getBytes()));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public static void main(String... args) {
        NioServerSocket serverSocket = new NioServerSocket();
        serverSocket.doAccept();
    }

}
