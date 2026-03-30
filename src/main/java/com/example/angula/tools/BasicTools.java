package com.example.angula.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class BasicTools {

    @Tool(description = "get current datetime")
    public ZonedDateTime getCurrentDatetime() {
        return ZonedDateTime.now();
    }
}
