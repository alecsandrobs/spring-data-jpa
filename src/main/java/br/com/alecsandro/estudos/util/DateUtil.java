package br.com.alecsandro.estudos.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateUtil {

    private static DateTimeFormatter jsonDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter jsonDateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dataBaseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dataformat = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    public static String dateFormat(LocalDate data) {
        return dataformat.format(data);
    }

    public static String formatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime) {
        return dataBaseFormat.format(localDateTime);
    }

    public static LocalDateTime localDateTime(String data) {
        return LocalDateTime.parse(data, jsonDateFormat);
    }

    public static LocalDate localDate(String data) {
        return LocalDate.parse(data, jsonDateFormat);
    }
}
