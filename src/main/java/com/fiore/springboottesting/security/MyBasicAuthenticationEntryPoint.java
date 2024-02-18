//package com.fiore.springboottesting.security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.SneakyThrows;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import java.io.PrintWriter;
//
////@Component
//public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
//
//    @Override
//    @SneakyThrows
//    public void commence(
//            HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
//            {
//        response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        PrintWriter writer = response.getWriter();
//        writer.println("###HTTP Status 401 - " + authEx.getMessage());
//    }
//
//    @Override
//    @SneakyThrows
//    public void afterPropertiesSet(){
//        setRealmName("REAME");
//        super.afterPropertiesSet();
//    }
//}
