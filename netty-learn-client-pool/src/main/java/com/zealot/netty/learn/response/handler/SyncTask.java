package com.zealot.netty.learn.response.handler;

import java.util.Map;
import java.util.concurrent.locks.Condition;

public class SyncTask {
    
    private Map<String,String> result;
    private Condition complete;
    
    public Map<String, String> getResult() {
        return result;
    }
    public void setResult(Map<String, String> result) {
        this.result = result;
    }
    public Condition getComplete() {
        return complete;
    }
    public void setComplete(Condition complete) {
        this.complete = complete;
    }
}
