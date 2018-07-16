/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording, 
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 * 
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2018年7月2日    Zhao Haiming         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.channel.Channel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.zealot.netty.learn.bean.APIContent;
import com.zealot.netty.learn.bean.MessageBean;
import com.zealot.netty.learn.cache.GuavaCache;
import com.zealot.netty.learn.client.NettyPoolClientPool;
import com.zealot.netty.learn.consts.RouteGlobal;
import com.zealot.netty.learn.response.handler.ResponseHandler;
import com.zealot.netty.learn.response.handler.SyncTask;
import com.zealot.netty.learn.service.SendService;

/**
 * @FileName FlumeController.java
 * @Description: 
 *
 * @Date 2018年7月2日 下午3:21:12
 * @author Zhao Haiming
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/netty")
public class nettyClientController extends AbsController{

    //输出普通日志
    private final static Logger logger = LoggerFactory.getLogger(nettyClientController.class);    
    
    @Resource
    private SendService sendService;
    
    @RequestMapping(value = "/client")
    @ResponseBody
    public APIContent client(HttpServletRequest request, HttpServletResponse response, String msg) {
    	
    	final APIContent apiContent = new APIContent();

    	ExecutorService exec = Executors.newFixedThreadPool(10);
    	for(int i =0;i<10;i++)
    	{
    		exec.execute(new Runnable (){

				@Override
				public void run() {
					final UUID uuidObj = UUID.randomUUID();
	    	    	String uuid = uuidObj.toString();
	    	    	String msg = "默认消息";
	    			final MessageBean bean = new MessageBean();
	    			bean.setMsg(msg);
	    	    	bean.setUuid(uuid);
	    	    	String json = JSON.toJSONString(bean);
	    	    	sendService.sendMsg(uuid, json);
				}
    			
    		});
    		
    	}
        logger.info("发送成功");
        
        return apiContent;
    }
}
