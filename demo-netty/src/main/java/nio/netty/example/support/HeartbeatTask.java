package nio.netty.example.support;

import io.netty.channel.*;
import nio.netty.example.Request;
/**
 * Created by Administrator on 2015/6/16.
 */
public class HeartbeatTask implements Runnable {

    private Channel channel;

    private final int DEFAULT_HEART_BEAT = 1000;

    private int             heartbeat = DEFAULT_HEART_BEAT;

    public HeartbeatTask(Channel channel) {
        this.channel = channel;
    }

    public HeartbeatTask(Channel channel, int heartbeat) {
        this.channel = channel;
        this.heartbeat = heartbeat;
    }

    public void run() {
        try {
            Request request = new Request();
            request.setEvent(null);
            channel.writeAndFlush(request);
        } catch ( Throwable t ) {
            t.printStackTrace();
        }
    }

}
