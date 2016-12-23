package nio.netty.example;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
/**
 * Created by Administrator on 2015/6/16.
 * 请求参数
 */
public class Request implements Serializable {

    public static final String HEARTBEAT_EVENT = null;

    private static final AtomicLong INVOKE_ID = new AtomicLong(0);
    /**
     * 请求唯一标识
     */
    private final long    id;
    /**
     * 请求事件（心跳检测 | 普通请求）
     */
    private boolean event = false;
    /**
     * 请求数据
     */
    private Object  data;

    public Request() {
        id = newId();
    }

    public Request(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public boolean getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = true;
        this.data = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object msg) {
        data = msg;
    }

    public boolean isHeartbeat() {
        return event && HEARTBEAT_EVENT == data;
    }

    public void setHeartbeat(boolean isHeartbeat) {
        if (isHeartbeat) {
            setEvent(HEARTBEAT_EVENT);
        }
    }

    private static long newId() {
        return INVOKE_ID.getAndIncrement();
    }

    @Override
    public String toString() {
        return "Request [id=" + id + ", event=" + event
                + ", data=" + (data == this ? "this" : data) + "]";
    }

}
