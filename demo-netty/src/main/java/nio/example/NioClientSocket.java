package nio.example;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by admin on 2016/12/19.
 */
public class NioClientSocket {

    private final int HOST = 6666;

    private Selector selector;

    public NioClientSocket() {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(HOST));
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public void doConnect() {
        int i = 0;
        try {
            while (true) {
                selector.select();
                Iterator iterator = selector.selectedKeys().iterator();
                if(iterator.hasNext()) {
                    SelectionKey key = (SelectionKey) iterator.next();
                    iterator.remove();
                    if(key.isConnectable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        if(socketChannel.isConnectionPending()){
                            //需调用该方法完成连接
                            socketChannel.finishConnect();
                        }
                        socketChannel.configureBlocking(false);
                        //在这里可以给服务端发送信息哦
                        socketChannel.write(ByteBuffer.wrap(new String("clientSocket客户端请求连接...").getBytes()));
                        //在和服务端连接成功之后，为了可以接收到服务端的信息，需要给通道设置读的权限。
                        socketChannel.register(selector, SelectionKey.OP_READ );
                    } else if(key.isReadable()) {
                        SocketChannel sChannel = (SocketChannel) key.channel();
                        //创建读缓冲区
                        ByteBuffer buffer = ByteBuffer.allocate(64);
                        int bufferRead = sChannel.read(buffer); //将channel中数据读取到buffer缓冲
                        if(-1 != bufferRead) {
                            String msg = new String(buffer.array()).trim();
                            System.out.println("client：" + msg);
                            if("bye".equals(msg)) {
                                sChannel.close();
                                selector.close();
                            }
                        }

                        if (i == 10) {
                            sChannel.write(ByteBuffer.wrap("bye".getBytes()));
                            Thread.sleep(1000);
                        }
                        i++;
                        sChannel.write(ByteBuffer.wrap(String.valueOf(i).getBytes()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        NioClientSocket clientSocket = new NioClientSocket();
        clientSocket.doConnect();
    }


}
