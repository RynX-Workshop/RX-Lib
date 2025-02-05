package com.github.jaeukkang12.lib.utils;

public final class PreCondition {
    public static void nonNull(Object object, String message) {
        if (object == null) throw new NullPointerException(message);
    }
}
