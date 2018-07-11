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
 * 2018年7月2日    Zhao Haiming         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.controller;

import io.netty.channel.Channel;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zealot.netty.learn.client.NettyPoolClientPool;

/**
 * @FileName FlumeController.java
 * @Description: 
 *
 * @Date 2018年7月2日 下午3:21:12
 * @author Zhao Haiming
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/netty")
public class nettyClientController {

    //输出普通日志
    private final static Logger logger = LoggerFactory.getLogger(nettyClientController.class);    
    
    @Resource
    private NettyPoolClientPool pool;
    
    @RequestMapping(value = "/client")
    @ResponseBody
    public String client(HttpServletRequest req,String msg){
    	
    	if(StringUtils.isEmpty(msg))
    	{
    		msg = "默认消息";
    	}
        for(int i=0;i<100;i++)
        {
	    	Channel ch = pool.getChannel(2);
	    	if(null != ch)
	    	{
	    		ch.writeAndFlush(msg);
	    		pool.release(ch);
	    	}
        }
        logger.info("发送成功");
        return "done";
      
    }
}
