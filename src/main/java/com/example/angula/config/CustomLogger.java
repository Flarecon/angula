package com.example.angula.config;

import org.springframework.boot.logging.structured.StructuredLogFormatter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class CustomLogger implements StructuredLogFormatter<ILoggingEvent>  {

    @Override
    public String format(ILoggingEvent event) {
        return
        "Level: " + event.getLevel() + "\n" +
        "Message: " + event.getMessage() + "\n\n";
    }

}
