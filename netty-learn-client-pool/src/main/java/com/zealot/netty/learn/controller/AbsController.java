/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2017.
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
 * 2017年12月18日    LongMin         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.zealot.netty.learn.bean.APIContent;
import com.zealot.netty.learn.consts.API;
import com.zealot.netty.learn.exception.APIException;


/**
 * @FileName AbsController.java
 * @Description: 控制器基类
 *
 * @Date 2017年12月18日 上午11:26:08
 * @author LongMin
 * @version 1.0
 */
public abstract class AbsController {


	/**
	 * @Title: apiSuccess
	 * @Description: api处理成功
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @author LongMin
	 * @date 2017年12月19日 下午3:24:29
	 */
	protected final void apiSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		this.apiSuccess(request, response, null);
	}

	/**
	 * @Title: apiSuccess
	 * @Description: api处理成功
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @param data
	 *            返回数据
	 * @author LongMin
	 * @date 2017年12月19日 下午3:24:29
	 */
	protected final void apiSuccess(HttpServletRequest request, HttpServletResponse response, Object data) throws IOException {
		APIContent apiContent = new APIContent();
		apiContent.setData(data);
		this.apiReturn(request, response, apiContent);
	}

	/**
	 * @Title: apiError
	 * @Description: api处理失败
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @param t
	 *            错误对象
	 * @throws IOException
	 * @author LongMin
	 * @date 2017年12月19日 下午3:26:11
	 */
	protected final void apiError(HttpServletRequest request, HttpServletResponse response, Throwable t) throws IOException {

		APIContent apiContent = new APIContent();
		if (t instanceof APIException) {
			APIException e = (APIException) t;
			apiContent.setCode(e.getCode());
			apiContent.setMsg(e.getMessage());
		} else if (t instanceof IllegalArgumentException) {
			apiContent.setCode(API.PARAM_ERROR);
			apiContent.setMsg(t.getMessage());
		} else {
			apiContent.setCode(API.FAILED);
			apiContent.setMsg("服务器错误");
		}

		this.apiReturn(request, response, apiContent);
	}

	/*
	 * api返回json
	 */
	protected final void apiReturn(HttpServletRequest request, HttpServletResponse response, APIContent apiContent) throws IOException {
		byte[] content = JSON.toJSONString(apiContent).getBytes("utf-8");
		response.setHeader("Content-Length", String.valueOf(content.length));
		response.setContentType("application/json; charset=utf-8");
		response.getOutputStream().write(content);
		response.getOutputStream().flush();
	}

}
