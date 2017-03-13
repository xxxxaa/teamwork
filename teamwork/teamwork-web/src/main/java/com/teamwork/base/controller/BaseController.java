package com.teamwork.base.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author yan
 * @Description:
 * @date 2016/10/17
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String REDIRECT = "redirect:";
    private static final String URL_ROOT = "/";

    private static final String PC_FOLDER = "pc/";
    private static final String MOBILE_FOLDER = "mobile/";

    protected String redirectTo(String toUrl) {
        if (toUrl == null) {
            toUrl = URL_ROOT;
        }
        StringBuffer sb = new StringBuffer();
        return sb.append(REDIRECT).append(toUrl).toString();
    }

    protected String redirectTo404() {
        return redirectTo("/error/not_found.htm");
    }

    protected String redirectTo500() {
        return redirectTo("/error/errorPage.htm");
    }

    protected String pcPageView(String viewPath) {
        return PC_FOLDER + viewPath;
    }

    protected String mobilePageView(String viewPath) {
        return MOBILE_FOLDER + viewPath;
    }

    protected String encodeParam(String param, String defaultValue){
        if(StringUtils.isNotBlank(param) && param.trim().length() <= 1){
            param = null;
        }

        if(StringUtils.isBlank(param)){
            param = defaultValue;
        }else{
            try {
                param = URLEncoder.encode(param, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isBlank(param)){
            param = defaultValue;
        }
        return param;
    }

    /**
     * 解码参数
     * @param param
     * @return
     */
    protected String decodeParam(String param, String defaultValue){
        if(StringUtils.isNotBlank(param) && param.trim().length() <= 1){
            param = null;
        }

        if(StringUtils.isBlank(param)){
            param = defaultValue;
        }else{
            try {
                param = URLDecoder.decode(param, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(StringUtils.isBlank(param)){
            param = defaultValue;
        }
        return param;
    }
}
