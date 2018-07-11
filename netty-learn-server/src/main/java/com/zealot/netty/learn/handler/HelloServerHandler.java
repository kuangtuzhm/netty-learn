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
 * 2018年7月10日    Zhao Haiming         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.handler;

import java.net.InetAddress;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @FileName ServerChannelHandlerAdapter.java
 * @Description: 通道适配器 多线程共享
 *
 * @Date 2018年7月10日 下午6:21:39
 * @author Zhao Haiming
 * @version 1.0
 */
@Component
@Sharable
public class HelloServerHandler extends SimpleChannelInboundHandler<String> {
  	/**
     * 日志处理
     */
    private Logger logger = LoggerFactory.getLogger(HelloServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
    	

         // 收到消息直接打印输出
    	logger.info(ctx.channel().remoteAddress() + " Say : " + msg);
           
           // 返回客户端消息 - 我已经接收到了你的消息
            ctx.writeAndFlush("Received your message !\n");
    		
    }

         /*
          * 
          * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
          * 
          * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
          * */
         @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
             
        	 logger.info("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
             
             ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
             
             super.channelActive(ctx);
         }

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
				throws Exception {
			ctx.close();
			logger.error("handler error:", cause);
		}
}
