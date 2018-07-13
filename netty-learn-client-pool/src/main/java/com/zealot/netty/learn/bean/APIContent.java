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

package com.zealot.netty.learn.bean;

import java.io.Serializable;

import com.zealot.netty.learn.consts.API;


/**
 * @FileName APIContent.java
 * @Description: API数据返回
 *
 * @Date 2017年12月18日 上午11:29:25
 * @author LongMin
 * @version 1.0
 */
public class APIContent implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code = API.SUCCESS;
	private String msg;
	private Object data;

	public APIContent() {
	}

	public APIContent(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public APIContent(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
