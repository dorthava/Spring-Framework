package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrinterWithDateTimeImpl implements Printer {
    private final Renderer renderer;
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private String localDateTime;

    public PrinterWithDateTimeImpl(Renderer renderer) {
         this.renderer = renderer;
    }

    public void setLocalDateTime(String localDateTime) {
        this.localDateTime = LocalDateTime.parse(localDateTime, dateTimeFormatter).format(dateTimeFormatter);
    }

    @Override
    public void print(String text) {
        renderer.render(localDateTime + " " + text);
    }
}
