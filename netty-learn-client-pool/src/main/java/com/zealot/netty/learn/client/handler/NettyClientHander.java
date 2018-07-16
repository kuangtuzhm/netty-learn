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
 * 2018年7月11日    Zhao Haiming         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.client.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zealot.netty.learn.bean.MessageBean;
import com.zealot.netty.learn.cache.GuavaCache;
import com.zealot.netty.learn.response.handler.ResponseHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @FileName NettyClientHander.java
 * @Description: 
 *
 * @Date 2018年7月11日 下午2:24:07
 * @author Zhao Haiming
 * @version 1.0
 */

public class NettyClientHander extends SimpleChannelInboundHandler<String> {
	
	private Logger logger = LoggerFactory.getLogger(NettyClientHander.class);

	static AtomicInteger count = new AtomicInteger(1);

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {

		logger.info("Server say : " + msg);
		if(StringUtils.isNotEmpty(msg))
		{
			MessageBean bean = JSON.parseObject(msg, MessageBean.class);
			if(StringUtils.isNotEmpty(bean.getUuid()))
			{
				ResponseHandler handler = GuavaCache.cmd.getIfPresent(bean.getUuid());
				
				if(null != handler)
				{
					Thread.sleep(5000);
					handler.onMsg(bean);
					logger.info("调用NettyClientHander完成");
				}
				
			}
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Client active ");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("Client close ");
		super.channelInactive(ctx);
	}


   
}
