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

package com.zealot.netty.learn.server;

import com.zealot.netty.learn.common.protocol.ProtocolCodec;
import com.zealot.netty.learn.handler.HelloServerHandler;
import com.zealot.netty.learn.handler.ProtocalHandler;
import com.zealot.netty.learn.handler.WebInstructionProtocolDecode;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @FileName HelloServerInitializer.java
 * @Description:
 * 
 * @Date 2018年7月11日 上午9:40:42
 * @author Zhao Haiming
 * @version 1.0
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();

		// 以("\n")为结尾分割的 解码器
//		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,
//				Delimiters.lineDelimiter()));

		// 字符串解码 和 编码
		//pipeline.addLast("decoder", new StringDecoder());
		//pipeline.addLast("encoder", new StringEncoder());

		// 自己的逻辑Handler
		//pipeline.addLast("handler", new HelloServerHandler());
		
		pipeline.addLast(new ProtocolCodec());
		pipeline.addLast(new WebInstructionProtocolDecode());
		pipeline.addLast(new ProtocalHandler());
	}

}
