package com.fyeeme.quasar.base.util;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Yves Sorge <yves.sorge@toolpage.org>
 * @link https://github.com/toolpage/java-case-converter
 * The text conversion class helps changing the cases of an existing text. Get
 * more information and online tools for this implementation on: <a href=
 * "https://en.toolpage.org/cat/text-conversion">https://en.toolpage.org/cat/text-conversion</a>
 */
public class CaseConverter {

    /**
     * Converts a text into start case / title case.
     *
     * @param value
     * @return converted string
     */
    public static String convertToStartCase(String value) {
        StringBuilder returnValue = new StringBuilder();
        value = value.toLowerCase();
        boolean makeNextUppercase = true;
        for (char c : value.toCharArray()) {
            if (Character.isSpaceChar(c) || Character.isWhitespace(c) || "()[]{}\\/".indexOf(c) != -1) {
                makeNextUppercase = true;
            } else if (makeNextUppercase) {
                c = Character.toTitleCase(c);
                makeNextUppercase = false;
            }

            returnValue.append(c);
        }
        return returnValue.toString();
    }

    /**
     * Converts a text into camel case.
     * Example: "camel case" into "CamelCase".
     *
     * @param value
     * @return converted string
     */
    public static String convertToCamelCase(String value) {
        String throwAwayChars = "()[]{}=?!.:,-_+\\\"#~/";
        value = value.replaceAll("[" + Pattern.quote(throwAwayChars) + "]", " ");
        value = CaseConverter.convertToStartCase(value);
        return value.replaceAll("\\s+", "");
    }

    /**
     * Converts a text into snake case.
     * Example: "snake case" into "Snake_Case".
     *
     * @param value
     * @return converted string
     */
    public static String convertToSnakeCase(String value) {
        String throwAwayChars = "()[]{}=?!.:,-_+\\\"#~/";
        value = value.replaceAll("[" + Pattern.quote(throwAwayChars) + "]", " ");
        value = CaseConverter.convertToStartCase(value);
        return value.trim().replaceAll("\\s+", "_");
    }

    /**
     * Converts a text into SNAKE_CASE.
     * Example: "snake case" into "SNAKE_CASE".
     *
     * @param value
     * @return converted string
     */
    public static String convertToUpperSnakeCase(String value) {
        String[] words = value.split("(?=\\p{Upper})");
        return Arrays.stream(words).map(String::toUpperCase).collect(Collectors.joining("_"));
    }

    /**
     * Converts a text into kebab case.
     * Example: "Kebab Case" into "kebab-case".
     *
     * @param value
     * @return converted string
     */
    public static String convertToKebabCase(String value) {
        String throwAwayChars = "()[]{}=?!.:,-_+\\\"#~/";
        value = value.replaceAll("[" + Pattern.quote(throwAwayChars) + "]", " ");
        value = value.toLowerCase();
        return value.trim().replaceAll("\\s+", "-");
    }

}