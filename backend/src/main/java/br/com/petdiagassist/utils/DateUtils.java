package br.com.petdiagassist.utils;

import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


/**
 * Class Date Utils
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
public final class DateUtils {
    public static final ZoneId AMERICA_SP = ZoneId.of("America/Sao_Paulo");
    public static final DateTimeFormatter DATE_TIME_FORMATTER_BR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    public static final DateTimeFormatter DATE_FORMATTER_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String parseDateToString(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(localDateTime.atZone(AMERICA_SP).withZoneSameInstant(ZoneOffset.UTC));
    }

    public static String parseDateToString(LocalDate localDate, DateTimeFormatter dateTimeFormatter) {
        return dateTimeFormatter.format(localDate);
    }

    public static LocalDate parseStringLocalDate(String date) {
        return LocalDate.parse(date, DATE_FORMATTER_BR);
    }
}
