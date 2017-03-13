package com.teamwork.web.context;

import com.teamwork.account.info.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManager.class);
    private static final String LOGIN_USER_SESSION_KEY = "LOGIN_USER";

    public static HttpServletRequest getRequest() {
        return ThreadContext.request();
    }

    public static HttpServletResponse getResponse() {
        return ThreadContext.response();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static boolean isLogind() {
        return null != getLoginUser();
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    public static UserInfo getLoginUser() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(1);
//        userInfo.setName("测试");
//        userInfo.setUserType(UserType.ADMIN);
//        userInfo.setUserName("xxx");
//     return userInfo;
        return (UserInfo) getSession().getAttribute(LOGIN_USER_SESSION_KEY);
    }

    /**
     * 设置登录上下文
     *
     * @param userInfo
     */
    public static void setLoginContext(UserInfo userInfo) {
        getSession().setAttribute(LOGIN_USER_SESSION_KEY, userInfo);
    }

    /**
     * 清理登录上下文
     */
    public static void clearLoginContext() {
        getSession().removeAttribute(LOGIN_USER_SESSION_KEY);
    }

}