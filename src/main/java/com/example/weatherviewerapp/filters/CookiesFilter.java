package com.example.weatherviewerapp.filters;

import com.example.weatherviewerapp.utils.CookieValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//@WebFilter("/main.html")
//public class CookiesFilter extends HttpFilter {
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//        System.out.println("фильтр работает mainhtml");
//        Cookie cookie = CookieValidator.CheckCookie(req);
//
//        if (cookie==null){
//            HttpServletResponse httpResp = (HttpServletResponse) res;
//            httpResp.sendRedirect("sign-in");
//            super.doFilter(req, res, chain);
//        }
////        else {
////
////            //метод на поиск куки в бд с сессиями и проверки его на время
////              //или переход на старницу авторизации, или вход на главную +
////                  // обновление времени в бд
////            super.doFilter(req, res, chain);
////        }
//        HttpServletResponse httpResp = (HttpServletResponse) res;
//        httpResp.sendRedirect("sign-on");
//        super.doFilter(req, res, chain);
//    }
//}
