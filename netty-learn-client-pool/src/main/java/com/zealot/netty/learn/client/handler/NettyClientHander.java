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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @FileName NettyClientHander.java
 * @Description: 
 *
 * @Date 2018年7月11日 下午2:24:07
 * @author Zhao Haiming
 * @version 1.0
 */
public class NettyClientHander extends ChannelInboundHandlerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(NettyClientHander.class);

	static AtomicInteger count = new AtomicInteger(1);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	logger.info(count.getAndIncrement() + ":" + msg);
    }
}
