package com.example.reactor.middleware;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
Filters are used to apply common logic to all the requests, they are part of the servlet specification, and are
invoked before the servlet is called, and after the servlet is finished. They are used to restrict access to resources
or to perform some common logic that applies to all the requests.

Interceptors are used to apply common logic to all the handlers, they are part of the Spring MVC framework, and are
invoked before and after the handler is called. They are used to add common functionality to all the handlers, such
as logging, authentication, etc.

The main difference between filters and interceptors is that filters are general, and apply to all the resources, while
interceptors are specific to the Spring MVC handlers, and only apply to the handlers that are handled by the Spring MVC
framework.

Client -> Filter -> DispatcherServlet -> Interceptor -> Controllers
*/

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable Exception ex) throws Exception {
        System.out.println(request.getRequestURI() + " " + response.getStatus());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println(request.getRequestURI() + " " + response.getStatus());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        System.out.println(uri + " " + response.getStatus());
        
        // return uri.startsWith("/reactor") ? true : false;
        return true;
    }
}
