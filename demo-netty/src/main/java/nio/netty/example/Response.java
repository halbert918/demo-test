package nio.netty.example;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/16.
 */
public class Response implements Serializable {
    
    public static final String HEARTBEAT_EVENT = null;

    /**
     * ok.
     */
    public static final byte OK                = 20;

    /**
     * clien side timeout.
     */
    public static final byte CLIENT_TIMEOUT    = 30;

    /**
     * server side timeout.
     */
    public static final byte SERVER_TIMEOUT    = 31;

    /**
     * request format error.
     */
    public static final byte BAD_REQUEST       = 40;

    /**
     * response format error.
     */
    public static final byte BAD_RESPONSE      = 50;

    /**
     * service not found.
     */
    public static final byte SERVICE_NOT_FOUND = 60;

    /**
     * service error.
     */
    public static final byte SERVICE_ERROR     = 70;

    /**
     * internal server error.
     */
    public static final byte SERVER_ERROR      = 80;

    /**
     * internal server error.
     */
    public static final byte CLIENT_ERROR      = 90;

    private long             id               = 0;

    private byte             status           = OK;

    private boolean          event         = false;

    private String           errorMsg;

    private Object           result;

    public Response(){
    }

    public Response(long id){
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
    
    public boolean getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = true;
        result = event;
    }

    public boolean isHeartbeat() {
        return event && HEARTBEAT_EVENT == result;
    }

    @Deprecated
    public void setHeartbeat(boolean isHeartbeat) {
        if (isHeartbeat) {
            setEvent(HEARTBEAT_EVENT);
        }
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object msg) {
        result = msg;
    }

    public String getErrorMessage() {
        return errorMsg;
    }

    public void setErrorMessage(String msg) {
        errorMsg = msg;
    }

    @Override
    public String toString() {
        return "Response [id=" + id + ", status=" + status + ", event=" + event
               + ", error=" + errorMsg + ", result=" + (result == this ? "this" : result) + "]";
    }
}