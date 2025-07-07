package com.example.reactor.enricher;

import lombok.Setter;

@Setter
public class React<T>{

    public T response;
    public String message;

    public static <T> React<T> response(T data) {
        React<T> react = new React<>();
        react.response = data;
        return react;
    }
}
