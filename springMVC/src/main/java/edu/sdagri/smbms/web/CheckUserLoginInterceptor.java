package edu.sdagri.smbms.web;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Package: edu.sdagri.smbms.web
 * File: CheckUserLoginInterceptor.java
 * Author: Yang Song
 * Date: 05/01/2025
 * Time: 16:35
 * Description: TODO: change me
 */

public class CheckUserLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if(loginUser!=null){
            return true;
        }
        response.sendRedirect(request.getContextPath()+"/"+"tologin");
      return false;

    }
}
