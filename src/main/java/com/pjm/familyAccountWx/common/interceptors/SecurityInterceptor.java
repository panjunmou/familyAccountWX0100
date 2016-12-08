package com.pjm.familyAccountWx.common.interceptors;

import com.pjm.familyAccountWx.common.constants.GlobalConstant;
import com.pjm.familyAccountWx.vo.LoginUserInfo;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限拦截器
 */
public class SecurityInterceptor implements HandlerInterceptor {

    /**
     * 完成页面的render后调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception {
    }

    /**
     * 在调用controller具体方法后拦截
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在调用controller具体方法前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        LoginUserInfo loginUserInfo = (LoginUserInfo) request.getSession().getAttribute(GlobalConstant.LOGIN_USER_INFO);

        System.out.println("url = " + url);
        if (url.indexOf("/login") > -1) {
            return true;
        } else {
            if (loginUserInfo == null) {
                response.sendRedirect("/index.jsp");
                return false;
            }
        }
        return true;
    }
}
