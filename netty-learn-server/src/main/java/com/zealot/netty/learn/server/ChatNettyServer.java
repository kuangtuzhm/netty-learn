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

package com.zealot.netty.learn.server;

/**
 * @FileName NettyServerListener.java
 * @Description: 
 *
 * @Date 2018年7月10日 下午6:18:18
 * @author Zhao Haiming
 * @version 1.0
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zealot.netty.learn.config.NettyConfig;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
* 服务启动监听器
*
* @author 叶云轩
*/
@Component
public class ChatNettyServer {
 /**
  * NettyServerListener 日志输出器
  *
  * @author 叶云轩 create by 2017/10/31 18:05
  */
 private static final Logger LOGGER = LoggerFactory.getLogger(ChatNettyServer.class);
 /**
  * 创建bootstrap
  */
 ServerBootstrap serverBootstrap = new ServerBootstrap();
 /**
  * BOSS
  */
 EventLoopGroup boss = new NioEventLoopGroup();
 /**
  * Worker
  */
 EventLoopGroup work = new NioEventLoopGroup();

 /**
  * NETT服务器配置类
  */
 @Resource
 private NettyConfig nettyConfig;

 /**
  * 关闭服务器方法
  */
 public void close() {
     LOGGER.info("关闭服务器....");
     //优雅退出
     boss.shutdownGracefully();
     work.shutdownGracefully();
 }

 /**
  * 开启及服务线程
  */
 public void start() throws InterruptedException{
     // 从配置文件中(application.yml)获取服务端监听端口号
     int port = nettyConfig.getPort();
     serverBootstrap.group(boss, work);
     serverBootstrap.channel(NioServerSocketChannel.class);
         try {
             //设置事件处理
        	 serverBootstrap.childHandler(new HelloServerInitializer());
             LOGGER.info("netty服务器在[{}]端口启动监听", port);
             ChannelFuture f = serverBootstrap.bind(port).sync();
             f.channel().closeFuture().syncUninterruptibly();
         } finally {
             LOGGER.info("[出现异常] 释放资源");
             boss.shutdownGracefully();
             work.shutdownGracefully();
         }
 }
}