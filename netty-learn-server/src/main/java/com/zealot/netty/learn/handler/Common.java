package com.zealot.netty.learn.handler;

 

public  class Common {
    /**
     * 设置会话ID
     */
   // private String sessionId;
    
    /**
     * 序号
     */
    private short seqNo;
    
    
	private byte[] uuid ;
	
	

	public byte[] getUuid() {
		return uuid;
	}

	public void setUuid(byte[] uuid) {
		this.uuid = uuid;
	}

	public short getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(short seqNo) {
		this.seqNo = seqNo;
	}

//	public String getSessionId() {
//		return sessionId;
//	}
//
//	public void setSessionId(String sessionId) {
//		this.sessionId = sessionId;
//	}

    
}
