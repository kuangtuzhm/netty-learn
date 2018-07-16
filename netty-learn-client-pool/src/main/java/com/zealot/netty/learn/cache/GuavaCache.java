package com.zealot.netty.learn.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zealot.netty.learn.bean.MessageBean;
import com.zealot.netty.learn.response.handler.ResponseHandler;


public class GuavaCache {

	/**
	 * imei_serverflagId handler 指令回调缓存
	 */
	
	public static Cache<String, ResponseHandler> cmd = CacheBuilder.newBuilder()
			.expireAfterWrite(31, TimeUnit.SECONDS) // 写入30秒后过期
			.build();
	
	public static Cache<String, ResponseHandler> testcmd = CacheBuilder.newBuilder()
			.expireAfterWrite(31, TimeUnit.SECONDS) // 写入30秒后过期
			.build();
	
	public static void main(String [] args)
	{
		testcmd.put("zhm1", new ResponseHandler() {
			@Override
			public void onMsg(MessageBean bean) throws InterruptedException {

					System.out.println(1);
	
			}
		});
		
		System.out.println("zhm1="+testcmd.getIfPresent("zhm1"));
		
		try {
			Thread.sleep(33000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("zhm1="+testcmd.getIfPresent("zhm1"));
		System.out.println("zhm2="+testcmd.getIfPresent("zhm2"));
	}

}
