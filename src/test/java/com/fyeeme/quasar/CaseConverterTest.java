package com.fyeeme.quasar;

import com.fyeeme.quasar.base.util.CaseConverter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CaseConverterTest {

    @Test
    public void testConvertToUpperCase() {
        String input = "The quick brown fox jumps over the lazy dog";
        assertEquals("The Quick Brown Fox Jumps Over The Lazy Dog", CaseConverter.convertToStartCase(input));
        assertEquals("TheQuickBrownFoxJumpsOverTheLazyDog", CaseConverter.convertToCamelCase(input));
        assertEquals("The_Quick_Brown_Fox_Jumps_Over_The_Lazy_Dog", CaseConverter.convertToSnakeCase(input));
        assertEquals("the-quick-brown-fox-jumps-over-the-lazy-dog", CaseConverter.convertToKebabCase(input));
    }

    @Test
    public void testToUpperSnakeCase(){
        var str = "HttpRequestMethodNotSupportedException";
        assertEquals("HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION", CaseConverter.convertToUpperSnakeCase(str));
    }

}
