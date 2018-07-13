package com.zealot.netty.learn.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zealot.netty.learn.response.handler.ResponseHandler;


public class GuavaCache {

	/**
	 * imei_serverflagId handler 指令回调缓存
	 */
	
	public static Cache<String, ResponseHandler> cmd = CacheBuilder.newBuilder()
			.expireAfterWrite(31, TimeUnit.SECONDS) // 写入30秒后过期
			.build();
	
//	public static Cache<String, Integer> testcmd = CacheBuilder.newBuilder()
//			.expireAfterWrite(31, TimeUnit.SECONDS) // 写入30秒后过期
//			.build();
//	
//	public static void main(String [] args)
//	{
//		testcmd.put("zhm1", 1);
//		testcmd.put("zhm2", 2);
//		
//		System.out.println("zhm1="+testcmd.getIfPresent("zhm1"));
//		System.out.println("zhm2="+testcmd.getIfPresent("zhm2"));
//		try {
//			Thread.sleep(33000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		System.out.println("zhm1="+testcmd.getIfPresent("zhm1"));
//		System.out.println("zhm2="+testcmd.getIfPresent("zhm2"));
//	}

}
