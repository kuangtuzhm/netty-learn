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
 * 2018年7月16日    Zhao Haiming         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.service;

import io.netty.channel.Channel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zealot.netty.learn.bean.APIContent;
import com.zealot.netty.learn.bean.MessageBean;
import com.zealot.netty.learn.cache.GuavaCache;
import com.zealot.netty.learn.client.NettyPoolClientPool;
import com.zealot.netty.learn.consts.RouteGlobal;
import com.zealot.netty.learn.response.handler.ResponseHandler;
import com.zealot.netty.learn.response.handler.SyncTask;

/**
 * @FileName SendService.java
 * @Description: 
 *
 * @Date 2018年7月16日 上午8:51:24
 * @author Zhao Haiming
 * @version 1.0
 */
@Service
public class SendService {
	
	//输出普通日志
    private final static Logger logger = LoggerFactory.getLogger(SendService.class); 
	
	@Resource
    private NettyPoolClientPool pool;
	
	private static final ReentrantLock lock = new ReentrantLock();

	public APIContent sendMsg(String uuid, String json)
	{
		APIContent apiContent = new APIContent();
		
		Channel ch = pool.getChannel(3);
    	if(null != ch)
    	{
    		try {
    			logger.info("发送指令消息："+json);
	    		ch.writeAndFlush(json);
	    		pool.release(ch);
	    		final SyncTask task = new SyncTask();
	    		task.setComplete(lock.newCondition());
				lock.lockInterruptibly();
				logger.info(Thread.currentThread().getName()+" 开始");
	    		GuavaCache.cmd.put(uuid, new ResponseHandler() {
					@Override
					public void onMsg(MessageBean bean) throws InterruptedException {

						lock.lockInterruptibly();
						try {
							//Thread.sleep(5000);
							task.setResult(bean);
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
    		finally{
    			logger.info(Thread.currentThread().getName()+" 结束");
    			lock.unlock();
    			
    		}
    		
    	}
		
		return apiContent;
	}
}
