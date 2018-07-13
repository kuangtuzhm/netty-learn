package com.zealot.netty.learn.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class WebInstructionProtocol  extends Common{
	
 
	public WebInstructionProtocol(byte[] content){
		this.content = content;
	} 
	//指令长度
	private  byte len;  
	//服务器标志位
	private int id;
 
	//指令内容
	private byte[] content; 
	
	
	//语言
	private byte[] language; 
	
	
	//内容编码
	private byte  ecode; 
	
	
	public byte[] getLanguage() {
		return language;
	}



	public void setLanguage(byte[] language) {
		this.language = language;
	}



	public byte getEcode() {
		return ecode;
	}



	public void setEcode(byte ecode) {
		this.ecode = ecode;
	}



	public void setLen(byte len) {
		this.len = len;
	}



	public byte[] build() {     
		
		ByteBuf buffer= Unpooled.buffer(5+content.length);
		buffer.writeByte(4+content.length);
		buffer.writeInt(id);    
		buffer.writeBytes(content);  
		byte[] data= buffer.array();
		buffer.release();	
		return data;
	}
	
	public byte[] buildWithEnglish() {     
		
		ByteBuf buffer= Unpooled.buffer(5+content.length);
		buffer.writeShort(4+content.length);
		buffer.writeInt(id);    
		buffer.writeBytes(content);  
		byte[] data= buffer.array();
		buffer.release();	
		return data;
	}
	
	public byte[] buildWithLanguage() {             
		ByteBuf buffer=  Unpooled.buffer(7+content.length);  
		buffer.writeByte(4+content.length);
		buffer.writeInt(id);   
		buffer.writeBytes(content); 
		buffer.writeBytes(language);  
		byte[] data=buffer.array();
		buffer.release();
		return data;
		 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

 
	public int getLen() {
		return len;
	}

}
