package com.zealot.netty.learn.utils;


public class Globals {
	
	//错误代码  请求成功/失败
	
	public static final byte SUCC=0x01;       //成功
	
	public static final byte CRC_FAIELD=0x02; //CRC校验失败 ,CRC错误
	
	public static final byte MSG_LEN_ERR=0x03; //包长度与实际长度不符
	
	public static final byte MSG_CONTENT_ERR=0x10; //数据包错误,例包头不为 79 79
	
	public static final byte NO_LONGIN=(byte) 0xFF; //未登录发送请求 
	
	public static final byte FAIELD=(byte) 0x0F; //未登录发送请求 
	
    
    public static class  RedisKey{
        /**
         * 离线指令
         */
        public static final String OFFLINE_CMD="TRACKER_OFFLINE_CMD";
        /**
         * 离线指令记数
         */
        public static final String OFFLINE_CMD_COUNT="TRACKER_OFFLINE_CMD_COUNT";
    }
    /*
	 * 默认起始位
	 */
	public static final byte[] start={0x79 , 0x79};
	
	/*
	 * 固定终止位
	 */
	public static final byte[] end={0x0D,0x0A};
	
	public static final class protocolNO{
		/**
		 * 指令下发
		 */
		public static final byte CMD_REQUEST = (byte) 0x83 ;
		
		/**
		 * 指令应答
		 */
		public static final byte CMD_RESPONSE = 0x03 ;
	}
	public static final class ProtocolLength{
		//header 长度
		public static final byte HEADER = 2;
		
		//包长度
		public static final byte PACKAGELENGTH = 2 ;  
		
		//协议号长度
		public static final byte PRONO = 1;
		
		//流水号长度
		public static final byte SEQNO = 2;
		
		//CRC长度
		public static final byte CRC = 2;
		
		//end 长度
		public static final byte END = 2;
		
		
	} 
	   
	
}
