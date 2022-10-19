package com.igorsrc.migration.plugin.exception;

public class PropertyValidationException extends RuntimeException {
    public PropertyValidationException(String property, String value) {
        super(String.format("Illegal property value found: prop=%s, value=%s", property, value));
    }
}