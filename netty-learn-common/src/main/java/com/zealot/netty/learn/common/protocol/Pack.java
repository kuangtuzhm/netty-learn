package com.zealot.netty.learn.common.protocol;

public class Pack {

    /**
     * 固定头子协议长 1byte
     */
    public static final byte[] HEADER_V1=new byte[]{0x78,0x78};
   
    /**
     * 结束符
     */
     public static final byte[] END=new byte[]{0x0D,0x0A};
    /**
     * CRC长度 
     */
    public static final int CRC_LENGTH = 2;
     /**
      * 会话ID
      */
     private String sessionId;
     /**
      * 头
      */
     private byte[] header;
    /**
     * 子协议
     */
    private byte[] protocol;
    

    public byte[] getHeader() {
        return header;
    }
    public void setHeader(byte[] header) {
        this.header = header;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public byte[] getProtocol() {
        return protocol;
    }
    public void setProtocol(byte[] protocol) {
        this.protocol = protocol;
    }
    


    
    
}
