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
import com.google.common.collect.Maps;
import com.zealot.netty.learn.bean.APIContent;
import com.zealot.netty.learn.bean.MessageBean;
import com.zealot.netty.learn.cache.GuavaCache;
import com.zealot.netty.learn.client.NettyPoolClientPool;
import com.zealot.netty.learn.common.protocol.Pack;
import com.zealot.netty.learn.common.protocol.Protocol;
import com.zealot.netty.learn.consts.RouteGlobal;
import com.zealot.netty.learn.response.handler.ResponseHandler;
import com.zealot.netty.learn.response.handler.SyncTask;
import com.zealot.netty.learn.utils.Globals;

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
    private NettyPoolClientPool pool;
    
    private static final ReentrantLock lock = new ReentrantLock();
    
    @RequestMapping(value = "/client")
    @ResponseBody
    public APIContent client(HttpServletRequest request, HttpServletResponse response, String msg) {
    	APIContent apiContent = new APIContent();
    	
    	MessageBean bean = new MessageBean();
    	
    	final UUID uuid = UUID.randomUUID();
    	if(StringUtils.isEmpty(msg))
    	{
    		msg = "默认消息";
    	}
    	bean.setMsg(msg);
    	bean.setUuid(uuid.toString());
    	String json = JSON.toJSONString(bean);
    	
    	Protocol cmdPro = new Protocol();
		cmdPro.setHeader(Pack.HEADER_V1);
		cmdPro.setProNo(Globals.protocolNO.CMD_REQUEST);
		short seqNo = 1;  //先给一个默认值组成一个完整消息，流水号由服务端控制
		cmdPro.setSeqNo(seqNo);
		cmdPro.setData(json.getBytes());
        for(int i=0;i<1;i++)
        {
	    	Channel ch = pool.getChannel(3);
	    	if(null != ch)
	    	{
	    		try {
		    		ch.writeAndFlush(cmdPro);
		    		pool.release(ch);
		    		final SyncTask task = new SyncTask();
					task.setComplete(lock.newCondition());
					lock.lockInterruptibly();
					logger.info(Thread.currentThread().getName()+" 开始");
		    		GuavaCache.cmd.put(uuid.toString(), new ResponseHandler() {
						@Override
						public void onMsg(String uuid , Map<String, String> response) throws InterruptedException {
	
							lock.lockInterruptibly();
							try {
								task.setResult(response);
								logger.info(Thread.currentThread().getName()+" 回调完成开始释放锁");
								task.getComplete().signal();
								task.setComplete(null);
							} finally {
								lock.unlock();
							}
						}
					});
		    		
		    		if (null != task.getComplete() && !task.getComplete().await(10, TimeUnit.SECONDS))
		    		{
		    			apiContent.setCode(RouteGlobal.ResponseCode.TIME_OUT);
		    			apiContent.setMsg("连接路由超时");
		    		}
		    		else
		    		{	
		    			apiContent.setData(task.getResult());
		    		}
				} catch (InterruptedException e) {
					apiContent.setCode(RouteGlobal.ResponseCode.SERVICE_ERROR);
	    			apiContent.setMsg("线程等待出错");
					return apiContent;
				}
	    		
	    		
	    	}
        }
        logger.info("发送成功");
        return apiContent;
    }
}
