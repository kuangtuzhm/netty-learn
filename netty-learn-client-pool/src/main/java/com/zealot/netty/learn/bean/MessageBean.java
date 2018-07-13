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
 * 2018年7月13日    Zhao Haiming         Create the class
 * http://www.jimilab.com/
*/

package com.zealot.netty.learn.bean;

/**
 * @FileName MessageBean.java
 * @Description: 
 *
 * @Date 2018年7月13日 上午11:08:10
 * @author Zhao Haiming
 * @version 1.0
 */
public class MessageBean {

	private String uuid;
	
	private String msg;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
