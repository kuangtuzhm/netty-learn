package com.zealot.netty.learn.consts;

public class RouteGlobal {
    /**
     * 网关
     */
    public static final class RedisKey{
    	public static final String IMEI_GATE="TRACKER_ROUTE_IMEI_GATE";
    }
    
    public static  class InnerField{
	    /**
	      *服务器标志ID
	      */
	    public static final String cmdType="_type";
		/**
		 *服务器标志ID
		 */
		public static final String serverFlagId="_serverFlagId";
		/**
		 *指令内容
		 */
		public static final String cmdContent="_content";
		/**
		 *协议号
		 */
		public static final String proNo="_proNo";
		
		/**
		 *uuid
		 */
		public static final String uuid = "_uuid";
		
		/**
		 * OBD结果码
		 */
		public static final String obdResultCode="_obdResultCode";
		
		/**
		 * 路由客户端时间
		 */
		public static final String sendTime="_route_client_time";
		/**
		 * 路由服务器时间
		 */
		public static final String routeTime="_route_server_time";
	 
		/**
		 * 指令响应代码
		 */
		public static final String CmdResponseCode="_code";
		/**
		 * 指令响应消息
		 */
		public static final String CmdResponseMsg="_msg";
	
	
    }
    /**
   
    /**
     * 回复代码
     * @author chengxuwei
     *
     */
    public static class ResponseCode{

		/**
		 * 指令成功发送，并成功响应，可能是设备忙，设备异常等
		 */
		public static final int DEVICE_OK= 100;
		
		/**
		 * 参数无效
		 */
		public static final int INVALID_PARAMS= 200;
		
		/**
		 * 设备没在线(网关)
		 */
		public static final int DEVICE_OFF_LINE= 300;
		/**
		 * 设备没有注册到路由表（Redis中没有找到IMEI和网关ID映射）
		 */
		public static final int DEVICE_OFF_ROUTE= 301;
		
		/**
		 *前一条指令己发送，还没有返回设备忙
		 */
		public static final int DEVICE_BUSY= 302;
		/**
		 * 网络错误(连接断开，等)
		 */
		public static final int NET_ERROR= 400;
		
		/**
		 * 代码执行异常
		 */
		public static final int SERVICE_ERROR= 500;
		/**
		 * 请求超时
		 */
		public static final int TIME_OUT= 600;
    }
}
