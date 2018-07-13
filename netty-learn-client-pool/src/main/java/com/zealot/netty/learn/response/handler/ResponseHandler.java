package com.zealot.netty.learn.response.handler;

import java.util.Map;

public interface ResponseHandler {
    
    /**
     * 返回消息
     * @param imei 设备IMEI
     * @param response 响应消息 
     * @throws InterruptedException 
     */
    public void onMsg(String imei,Map<String,String> response) throws InterruptedException ;
}
