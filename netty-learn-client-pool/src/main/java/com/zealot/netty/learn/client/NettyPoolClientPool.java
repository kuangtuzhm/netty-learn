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

package com.zealot.netty.learn.client;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zealot.netty.learn.pool.handler.NettyChannelPoolHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.FixedChannelPool;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;

/**
 * @FileName NettyPoolClient.java
 * @Description:
 * 
 * @Date 2018年7月11日 下午2:55:47
 * @author Zhao Haiming
 * @version 1.0
 */
@Component
public class NettyPoolClientPool {

	@Value("${netty.server.ip}")
	private String serverIp;

	@Value("${netty.server.port}")
	private Integer port;
	
	@Value("${netty.server.channelcount}")
	private Integer channelcount;

	final EventLoopGroup group = new NioEventLoopGroup();
	final Bootstrap strap = new Bootstrap();

	SimpleChannelPool pool;

	public void build() throws Exception {

		InetSocketAddress addr1 = new InetSocketAddress(serverIp, port);

		strap.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true);

		pool = new FixedChannelPool(strap.remoteAddress(addr1),
				new NettyChannelPoolHandler(), channelcount);

	}
	
	//申请连接，没有申请到(或者网络断开)，返回null
	public Channel getChannel(int seconds) { 
	try {
	Future<Channel> fch = pool.acquire();
	Channel ch = fch.get(seconds, TimeUnit.SECONDS);
	return ch;
	} catch (Exception e) {
	e.printStackTrace();
	}
	return null;
	}
	
	//释放连接
	public void release(Channel channel) { 
	try {
	if (channel != null) {
		pool.release(channel);
	}
	} catch (Exception e) {
	e.printStackTrace();
	}
	}

}
