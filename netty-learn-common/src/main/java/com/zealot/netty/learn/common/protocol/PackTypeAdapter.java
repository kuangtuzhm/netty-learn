package com.zealot.netty.learn.common.protocol;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class PackTypeAdapter extends LengthFieldBasedFrameDecoder {

    public PackTypeAdapter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }
    /**
     * 帧解码
     * @param ctx
     * @param in
     * @return
     * @throws Exception
     */
    public ByteBuf decodePackFrame(ChannelHandlerContext ctx, ByteBuf in) throws Exception{
        return (ByteBuf) decode(ctx, in);
    }
}
