package com.zealot.netty.learn.response.handler;

import com.zealot.netty.learn.bean.MessageBean;

public interface ResponseHandler {
    
    /**
     * 返回消息
     * @param imei 设备IMEI
     * @param response 响应消息 
     * @throws InterruptedException 
     */
    public void onMsg(MessageBean bean) throws InterruptedException ;
}
