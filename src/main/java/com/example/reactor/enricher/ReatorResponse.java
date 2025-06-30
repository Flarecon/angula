package com.example.reactor.enricher;


public class ReatorResponse<T>{

    public T data;



    public static <T> ReatorResponse<T> of(T data) {
        ReatorResponse<T> response = new ReatorResponse<>();
        response.data = data;
        return response;
    }
}
