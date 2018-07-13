package com.zealot.netty.learn.common.protocol;


import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zealot.netty.learn.common.utils.Crc16;
import com.zealot.netty.learn.common.utils.GeneralUtils;
import com.zealot.netty.learn.common.utils.Globals;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.ReferenceCountUtil;

public class ProtocolCodec extends ByteToMessageCodec<Protocol> {
	private final static Logger log = LoggerFactory.getLogger(ProtocolCodec.class);    
	
	// 参数(最大帧长，帧长字段偏移，帐长字段的长度，附加长度，解析后跳过长度，超过最大帧长时快速失败)
	private PackTypeAdapter frameV1 = new PackTypeAdapter(Short.MAX_VALUE, 2, 2 , 2, 0, true);

	@Override
	protected void encode(ChannelHandlerContext ctx, Protocol pro, ByteBuf out) throws Exception { // Protocol编码到输出流

		if (null != pro) {
			// 获取计算CRC的数据
			byte[] data = pro.getData();
			int dataLength = 0;
			if (null != data && data.length > 0) {
				dataLength = data.length;
			}
			ByteBuf crcData = Unpooled.buffer(dataLength + Globals.ProtocolLength.PACKAGELENGTH + Globals.ProtocolLength.PRONO+Globals.ProtocolLength.SEQNO);// proNo+seqNo 协议号+信息序列号
			crcData.writeShort(dataLength + Globals.ProtocolLength.PRONO+Globals.ProtocolLength.SEQNO+Globals.ProtocolLength.CRC);// short
			// 写数据 协议号
			crcData.writeByte(pro.getProNo());
			if (dataLength > 0) {
				crcData.writeBytes(data);
			}
			//消息流水号
			crcData.writeShort(pro.getSeqNo());
			// 计算CRC
			short crc16 = Crc16.crc16(crcData.array());
			// 写数据
			out.writeBytes(Pack.HEADER_V1);
			out.writeBytes(crcData);
			out.writeShort(crc16);
			out.writeBytes(Globals.end);
			crcData.release();
			// 打印发送数据
			if (log.isInfoEnabled()) {
				ByteBuf printBuf = out.copy();
				byte[] printData = new byte[printBuf.readableBytes()];
				printBuf.readBytes(printData);
				/*ReferenceCountUtil.release(printBuf);
				String imei = GlobalMaps.getImeiByChannelHashCode(ctx.channel().hashCode());
				if (StringUtils.isNotBlank(imei)) {
					hexDump.info(imei + " > " + GeneralUtils.getHexString(printData));
				} else {*/
				log.info("web下发指令到网关 > " + GeneralUtils.getHexString(printData));
				//}

			}
		}
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 统计
		if (in.readableBytes() < 3) {
			// 长度不到解析长度返回
			return;
		} else {
			// 解码帧
			byte[] header = new byte[2];
			int readerIndex = in.readerIndex();
			in.getBytes(readerIndex, header);
			ByteBuf packFrame = null;
			int lengthFieldLength = 2;
			packFrame = frameV1.decodePackFrame(ctx, in);
			// 解码数据
			if (null != packFrame) {
				int packLength = packFrame.readableBytes();
				byte[] printBuf = new byte[packLength];
				if (log.isInfoEnabled()) {
					packFrame.getBytes(0, printBuf);
					//String imei = GlobalMaps.getImeiByChannelHashCode(ctx.channel().hashCode());
					String hex = GeneralUtils.getHexString(printBuf);
					// 登录包
					/*if (StringUtils.isBlank(imei) && hex.length() > 36 && hex.startsWith("78,78,11,01")) {
						imei = hex.substring(13, 36).replaceAll(",", "");
					}*/
					log.info("web端收到网关应答 < " + hex);
				}
				// int packSize=packFrame.readableBytes();
				// Protocol
				Protocol protocol = decodeProtocol(packFrame, lengthFieldLength);
				if (null != protocol) {
					out.add(protocol);
				}
			}
		}
	}

	/**
	 * 包解码
	 * 
	 * @param in
	 * @return
	 */
	private Protocol decodeProtocol(ByteBuf in, int lengthFieldLength) {
		int readerIndex = in.readerIndex();
		// 获取帧长度
		int packLength = in.getUnsignedShort(2);
		// 校验结结束位，开始位在帧解码已验证
		byte[] end = new byte[2];
		in.getBytes(in.readableBytes() - 2 , end);// 帧长+头
		if (!Arrays.equals(end, Pack.END)) {
			log.error("错误的结束符:" + GeneralUtils.getHexString(end));
			return null;
		}
		// crc验证
		byte[] crcData = new byte[packLength - 2 + lengthFieldLength];
		in.getBytes(readerIndex + 2, crcData);
		short crc = in.getShort(readerIndex + packLength + lengthFieldLength);
		short calCrc = Crc16.crc16(crcData);
		if (crc != calCrc) {
			log.error("CRC16 校验错误(读取值：计算值):" + Integer.toHexString(crc) + ":" + Integer.toHexString(calCrc));
			return null;
		}
		// 解析成protocol
		Protocol pro = new Protocol();

		pro.setHeader(in.readBytes(2).array());// 头
		in.skipBytes(lengthFieldLength);// 包长
		pro.setProNo(in.readByte());// 协议号
		pro.setData(in.readBytes(packLength - 5).array());// 数据
		pro.setSeqNo(in.readShort());// 序号
		ReferenceCountUtil.release(in);
		return pro;

	}

	public static void main(String[] args) {
		String hex = "78,78,11,01,08,68,12,01,37,19,46,65,70,01,32,02,00,23,d8,24,0d,0a,";
		System.out.println(hex.substring(13, 36).replaceAll(",", ""));
	}

}
