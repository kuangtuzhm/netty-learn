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

package com.zealot.netty.learn.pool.handler;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zealot.netty.learn.client.handler.NettyClientHander;
import com.zealot.netty.learn.common.protocol.ProtocolCodec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @FileName NettyChannelPoolHandler.java
 * @Description: 
 *
 * @Date 2018年7月11日 下午2:22:03
 * @author Zhao Haiming
 * @version 1.0
 */
@Component
public class NettyChannelPoolHandler implements ChannelPoolHandler {
	
	private Logger logger = LoggerFactory.getLogger(NettyChannelPoolHandler.class);
	
	@Resource
	private NettyClientHander nettyClientHander;

	@Override
	public void channelReleased(Channel ch) throws Exception {
		logger.info("channelReleased. Channel ID: " + ch.id());
	}

	@Override
	public void channelAcquired(Channel ch) throws Exception {
		
		logger.info("channelAcquired. Channel ID: " + ch.id());
	}

	@Override
	public void channelCreated(Channel ch) throws Exception {
		
		ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
		logger.info("channelCreated. Channel ID: " + ch.id());
        SocketChannel channel = (SocketChannel) ch;
        channel.config().setKeepAlive(true);
        channel.config().setTcpNoDelay(true);
        channel.pipeline()
                .addLast(new DelimiterBasedFrameDecoder(1024, delimiter))
                .addLast(new ProtocolCodec()).addLast(nettyClientHander);
	}

}
