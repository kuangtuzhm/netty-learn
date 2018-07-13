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
 * 2017年12月20日    LongMin         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * @FileName API.java
 * @Description: api
 *
 * @Date 2017年12月20日 下午9:00:14
 * @author LongMin
 * @version 1.0
 */
public class API {

	public static final int SUCCESS = 0; // 成功
	public static final int FAILED = -1; // 失败
	public static final int TIME_OUT = 225; // 请求超时
	public static final int INVALID_PARAMS = 226; // 指令参数错误
	public static final int DEVICE_OFF_LINE = 228; // 设备不在线
	public static final int NET_ERROR = 229; // 网路错误
	public static final int DEVICE_BUSY = 252; // 设备忙
	public static final int DEVICE_OK = 255; // 设备成功
	public static final int SERVICE_ERROR = 404; // 未知错误
	public static final int NOT_AUTHORIZED = 503; // 登录过期
	public static final int NOT_ENOUGH_VHOST = 1000; // 虚拟主机不足
	public static final int DEV_ACCOUNT_NOT_EXIST = 1002; // 账号不存在
	public static final int DEV_PASSWORD_NOT_MATCH = 1003; // 账号、密码不匹配
	public static final int DEV_ACCOUNT_IS_EXIST = 1004; // 账号已存在
	public static final int PARAM_ERROR = 11001; // 参数错误
	public static final int DEV_KEY_NOT_EXIST = 12001; // 开发者KEY无效
	public static final int UUID_NOT_MATCH = 12002; // 开发者,设备不匹配
	public static final int UUID_NOT_EXIST = 12003; // 设备UUID无效
	public static final int DEV_SECRET_NOT_MATCH = 12004; // 开发者SECRET不匹配
	public static final int NOT_SUPPORT_M3U8 = 1005; // 不支持M3U8格式
	public static final int EXPIRED_TOKEN = 1006; // 令牌过期
	public static final int INVALID_TOKEN = 1007; // 令牌无效
	public static final int PARTIAL_IMPORT_FAILURE = 1008; // 部分导入失败

	public static final Map<Integer, String> MSG = new HashMap<>();
	static {
		MSG.put(API.PARTIAL_IMPORT_FAILURE, "部分导入失败");
		MSG.put(API.INVALID_TOKEN, "令牌校验错误");
		MSG.put(API.EXPIRED_TOKEN, "令牌过期");
		MSG.put(API.NOT_SUPPORT_M3U8, "不支持M3U8格式");
		MSG.put(API.NOT_AUTHORIZED, "登录过期");
		MSG.put(API.DEV_ACCOUNT_IS_EXIST, "账号已存在");
		MSG.put(API.DEV_PASSWORD_NOT_MATCH, "账号、密码不匹配");
		MSG.put(API.DEV_ACCOUNT_NOT_EXIST, "账号不存在");
		MSG.put(API.NOT_ENOUGH_VHOST, "虚拟主机不足");
		MSG.put(API.FAILED, "系统错误");
		MSG.put(API.DEV_KEY_NOT_EXIST, "开发者KEY无效");
		MSG.put(API.DEV_SECRET_NOT_MATCH, "开发者SECRET不匹配");
		MSG.put(API.UUID_NOT_MATCH, "开发者,设备不匹配");
		MSG.put(API.UUID_NOT_EXIST, "设备UUID无效");
		MSG.put(API.DEVICE_BUSY, "设备忙");
		MSG.put(API.DEVICE_OFF_LINE, "设备不在线");
		MSG.put(API.INVALID_PARAMS, "指令参数错误");
		MSG.put(API.NET_ERROR, "网路错误");
		MSG.put(API.TIME_OUT, "请求超时");
		MSG.put(API.SERVICE_ERROR, "未知错误");
	}
}
