package com.zealot.netty.learn.common.protocol;

/**
 * 
 * @author chengxuwei
 *
 */
public class Protocol {

    /**
     * 协议号
     */
    private byte proNo;
    /**
     * 序号
     */
    private short seqNo;
    /**
     * 数据
     */
    private byte[] data;
    /**
     * 头
     */
    private byte[] header;
    

    public byte[] getHeader() {
        return header;
    }
    public void setHeader(byte[] header) {
        this.header = header;
    }


    public byte getProNo() {
        return proNo;
    }


    public void setProNo(byte proNo) {
        this.proNo = proNo;
    }


    public short getSeqNo() {
        return seqNo;
    }


    public void setSeqNo(short seqNo) {
        this.seqNo = seqNo;
    }


    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }

}
