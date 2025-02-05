package com.github.jaeukkang12.lib.command.handler;

import java.util.HashMap;
import java.util.Map;

public class CommandInstanceHandler {
    private static Map<Class<?>, Object> instances = new HashMap<>();

    protected static <T> T getCommandInstance(Class<T> commandClass) {
        if (!instances.containsKey(commandClass)) {
            try {
                instances.put(commandClass, commandClass.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return (T) instances.get(commandClass);
    }
}
