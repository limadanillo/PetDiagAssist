package br.com.petdiagassist.utils;

import lombok.extern.log4j.Log4j2;

/**
 * Class Object Utils
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
public class StringUtils {
    public final static Boolean isNullOrBlank(String value) {
        return (value == null || value.isBlank());
    }

    public final static Boolean isNotNullAndBlank(String value) {
        return (value != null && !value.isBlank());
    }
}
