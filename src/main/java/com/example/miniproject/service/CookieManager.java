package com.example.miniproject.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieManager {

    public void setCookie(HttpServletResponse response, String value){
        Cookie cookie = new Cookie("생성하려는 쿠키 id", value);
        cookie.setMaxAge(0); //쿠키 유효기간 : 브라우저 종료 전까지
        cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정 (절대경로여야함)
        response.addCookie(cookie);
    }

    public String getCookie(HttpServletRequest request, String findId){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie c : cookies){
                String name = c.getName(); //쿠키이름
                String value = c.getValue(); //쿠키값

                if(name.equals(findId)){
                    return value;
                }
            }
        }
        return null;
    }


}
