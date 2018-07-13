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

package com.zealot.netty.learn.exception;

/**
 * @FileName APIException.java
 * @Description:
 *
 * @Date 2017年12月18日 上午11:35:26
 * @author LongMin
 * @version 1.0
 */
public class APIException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final int code;

	public APIException(int code) {
		this.code = code;
	}

	public APIException(int code, String msg) {
		super(msg);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
