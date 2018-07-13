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
import java.util.Map;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zealot.netty.learn.common.protocol.Protocol;
import com.zealot.netty.learn.common.utils.Globals;

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
public class ProtocalHandler extends SimpleChannelInboundHandler<WebInstructionProtocol> {
  	/**
     * 日志处理
     */
    private Logger logger = LoggerFactory.getLogger(ProtocalHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebInstructionProtocol cmd) throws Exception {
    	

    	String json = new String(cmd.getContent());
    	logger.info("Instruction Request info: " + json);
		Map<String, String> map = JSON.parseObject(json, new TypeReference<Map<String, String>>() {
		});
		String uuid = map.get(Globals.instruction.uuid);
    	
    	Protocol cmdReturn = new Protocol();
		cmdReturn.setHeader(Globals.ProtocolKey.start);
		cmdReturn.setProNo(Globals.ProtocolNO.CMDRESPONSE);
		cmdReturn.setSeqNo(cmd.getSeqNo());
		map.put(Globals.instruction.CmdResponseCode, Globals.instruction.DEVICE_OFF_ROUTE);
		map.put(Globals.instruction.CmdResponseMsg, "成功应答");
		String responseJson = JSON.toJSONString(map);
		cmdReturn.setData(responseJson.getBytes());
		logger.info("应答web指令，成功应答 -> " + JSON.toJSONString(cmdReturn));
		ctx.writeAndFlush(cmdReturn); 		
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
