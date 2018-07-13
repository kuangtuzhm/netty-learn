
package com.zealot.netty.learn.common.utils;

import io.netty.util.AttributeKey;

public class Globals {
	
	public static final AttributeKey<String> APP_VERIFY_CODE = AttributeKey.valueOf("APP_VERIFY_CODE");
	
	//错误代码  请求成功/失败
	public static final byte SUCC =  0x01;       //成功
	
	public static final byte CRC_FAIELD = 0x02; //CRC校验失败 ,CRC错误
	
	public static final byte MSG_LEN_ERR = 0x03; //包长度与实际长度不符
	
	public static final byte MSG_CONTENT_ERR = 0x10; //数据包错误,例包头不为 79 79
	
	public static final byte NO_LONGIN = (byte) 0xFF; //未登录发送请求 
	
	public static final byte FAIELD=(byte) 0x0F; //未登录发送请求 
	
    
	/**
	 * 协议通用相关
	 */
	public static class  ProtocolKey{
		/*
		 * 默认起始位
		 */
		public static final byte[] start={0x78 , 0x78};
		
		/*
		 * 固定终止位
		 */
		public static final byte[] end = {0x0D,0x0A};
		
	}
	public static final class ProtocolNO{
	    //登陆
		public static final byte LOGIN = 0x01;
		
		//登陆应答
		public static final byte LOGINRESPONSE = (byte)0x81;
		
		//心跳
		public static final byte HB    = 0x02;
		
		public static final byte HBRESPONSE    = (byte)0x82;
		
		//指令下发
		public static final byte CMDREQUEST  = (byte) 0x83;
		
		//指令应答
		public static final byte CMDRESPONSE = 0x03;
		
		//终端设备主动上报业务数据
		public static final byte DATAREQUEST  = 0x07;
		
		//终端透传业务数据包
		public static final byte DATARESPONSE = (byte) 0x87;
		
		//app登陆
		public static final byte APPLOGINEQUEST  = 0x04;
		
		//登陆应答
		public static final byte APPLOGINRESPONSE = (byte) 0x84;
		
		
		
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
    public static class  RedisKey{
        /**
         * 离线指令
         */
        public static final String OFFLINE_CMD="TRACKER_OFFLINE_CMD";
        /**
         * 离线指令记数
         */
        public static final String OFFLINE_CMD_COUNT="TRACKER_OFFLINE_CMD_COUNT";
        /**
         * 心跳数据
         */
    	public static final String TRACKER_MIDEA_HB = "TRACKER_MIDEA_HB"; 
    	/**
    	 * 终端网关通道
    	 */
    	public static final String TRACKER_MIDEA_IMEI_GATE = "TRACKER_MIDEA_IMEI_GATE";
    	
    	/**
    	 * uuid ->appKey的关系
    	 */
    	public static final String SRS_UUID_APPKEY = "SRS_UUID_APPKEY";
    	


    }
    
    public static final String HB_ACC="HB_ACC";
	
	
	
	 /**
     * 会话Key
     * @author
     *
     */
    public static interface SessionKey{
        /**
         * uuid 
         */
        public static final String UUID = "UUID";
        
        /**
         * appKey
         */
        public static final String APPKEY = "APPKEY";
        
       
   
    }
    /**
	 * 字符串指令编码类型
	 * @author xiehongtao
	 *
	public static final class instructionEncodeType{
		
		public static final byte ASCII = 0x01;//0x01 ASCⅡ编码
		
		public static final byte UTF16 = 0x02;//0x02 UTF16-BE编码
		
	}*/
	/**
	 * 字符串指令编码类型
	 * @author xiehongtao
	 *
	 */
	public static final class instruction{
		
		public static final byte ASCII = 0x01;//0x01 ASCⅡ编码
		
		public static final byte UTF16 = 0x02;//0x02 UTF16-BE编码
		/**
		 *指令内容
		 */
		public static final String cmdContent = "_content";
		
		/**
		 *IMEI
		 */
		public static final String imei="_imei";
		
		/**
		 *IMEI
		 */
		public static final String uuid="_uuid";
		
		/**
		 *服务器标志ID
		 */
		public static final String serverFlagId="_serverFlagId";
		
		/**
		 *协议号
		 */
		public static final String proNo="_proNo";
		
		/**
		 * 指令响应代码
		 */
		public static final String CmdResponseCode="_code";
		/**
		 * 指令响应消息
		 */
		public static final String CmdResponseMsg="_msg";
		
		/**
		 * 设备没在线(网关)
		 */
		public static final String DEVICE_OFF_LINE= "300";
		/**
		 * 设备没有注册到路由表（Redis中没有找到IMEI和网关ID映射）
		 */
		public static final String DEVICE_OFF_ROUTE= "301";
		
		
	}
	/**
	 * 应答结果
	 */
	public static final class ProtocolResponse{
		/**
		 * 成功
		 */
		public static final  byte  success = 0x00;
		/**
		 * UUID不存在
		 */
		public static final  byte  uuidNotExist = 0x01;
		
		/**
		 * APPkey不存在
		 */
		public static final  byte  appkeyNotExist = 0x02;
		
		/**
		 * UUID跟appkey不匹配
		 */
		public static final  byte  appkeyNotEqualUuid = 0x03;
		
		
	}
	
	 /*
		 * 默认起始位
		 */
		public static final byte[] start={0x79 , 0x79};
		
		/*
		 * 固定终止位
		 */
		public static final byte[] end={0x0D,0x0A};
}
