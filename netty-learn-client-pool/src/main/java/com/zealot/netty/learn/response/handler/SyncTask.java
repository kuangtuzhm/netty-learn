package com.zealot.netty.learn.response.handler;

import java.util.concurrent.locks.Condition;

import com.zealot.netty.learn.bean.MessageBean;

public class SyncTask {
    
    private MessageBean result;
    
    private Condition complete;

	public MessageBean getResult() {
		return result;
	}
	public void setResult(MessageBean result) {
		this.result = result;
	}
	public Condition getComplete() {
        return complete;
    }
    public void setComplete(Condition complete) {
        this.complete = complete;
    }
}
