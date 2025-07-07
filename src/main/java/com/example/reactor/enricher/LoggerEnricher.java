package com.example.reactor.enricher;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.example.reactor.controller.ReactRestController;

@Service
@ControllerAdvice(basePackageClasses = ReactRestController.class)
public class LoggerEnricher implements ResponseBodyAdvice<React> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println(returnType + " just Crossed By -------------------!>");
        return React.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public React beforeBodyWrite(React body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {
        body.setMessage("thanks for crossing by class " + body.response.getClass().getSimpleName());
        return body;
    }
}