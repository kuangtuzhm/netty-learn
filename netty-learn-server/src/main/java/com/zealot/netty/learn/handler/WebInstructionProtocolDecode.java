package com.zealot.netty.learn.handler;

import java.util.List;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zealot.netty.learn.common.protocol.Protocol;
import com.zealot.netty.learn.common.utils.Globals;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

public class WebInstructionProtocolDecode extends MessageToMessageCodec<Protocol, WebInstructionProtocol> {
	
	private Logger logger = LoggerFactory.getLogger(HelloServerHandler.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, Protocol pro, List<Object> out) throws Exception {
		if (pro.getProNo() != Globals.ProtocolNO.CMDREQUEST) {
			out.add(pro);
			return;
		}
		ByteBuf proData = Unpooled.wrappedBuffer(pro.getData());
		logger.debug("ByteBuf 读之前，接受WEB发过来的【控制指令包】  包大小：" + proData.readableBytes() + " Bytes");
		//减去流水号
		WebInstructionProtocol cmd = new WebInstructionProtocol(
				proData.readBytes(proData.readableBytes()).array());// WEB系统 发送
		proData.release();
		out.add(cmd);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, WebInstructionProtocol msg, List<Object> out) throws Exception {
		// TODO Auto-generated method stub

	}

}
 