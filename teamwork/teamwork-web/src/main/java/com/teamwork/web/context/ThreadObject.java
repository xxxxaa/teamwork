package com.teamwork.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ThreadObject {
    
    public final HttpServletRequest request;
    public final HttpServletResponse response;
    public String ip;
    /** 其他附加数据 */
    final Map<String, Object> data = new HashMap<>();
    public Parameter parameter;

    public ThreadObject(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.parameter = null;
        if (request instanceof HttpServletRequest) {
            this.parameter = new Parameter(request);
        }
    }
    
}