package com.github.jaeukkang12.lib.command.handler;

import com.github.jaeukkang12.lib.command.annotation.Command;
import com.github.jaeukkang12.lib.command.annotation.SubCommand;
import com.github.jaeukkang12.lib.utils.StringUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import oshi.util.tuples.Pair;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.github.jaeukkang12.lib.command.handler.CommandInstanceHandler.getCommandInstance;

public class CommandHandler implements CommandExecutor {

    private final JavaPlugin plugin;

    private final Map<Class<?>, String> labels = new HashMap<>();
    private final Map<Class<?>, Map<String, Pair<Method, String>>> commandMap = new HashMap<>();

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register(Class<?> commandClass) {
        Command commandAnnotation = commandClass.getAnnotation(Command.class);
        if (commandAnnotation == null) {
            return;
        }

        String name = commandAnnotation.name();
        String[] aliases = commandAnnotation.aliases();

        plugin.getCommand(name).setExecutor(this);
        Arrays.stream(aliases).forEach(it -> plugin.getCommand(it).setExecutor(this));

        labels.put(commandClass, name);
        registerSubCommand(commandClass);
    }

    private void registerSubCommand(Class<?> parentClass) {
        for(Method method : parentClass.getDeclaredMethods()) {
            SubCommand subCommandAnnotation = method.getAnnotation(SubCommand.class);
            if (subCommandAnnotation == null) {
                continue;
            }

            String name = subCommandAnnotation.name();
            String permission = subCommandAnnotation.permission();
            commandMap.computeIfAbsent(parentClass, k -> new HashMap<>()).put(name, new Pair<>(method, permission));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        String parentCommand = command.getName();
        Class<?> commandClass = null;

        for (Class<?> clazz : labels.keySet()) {
            if (labels.get(clazz).equals(parentCommand)) {
                commandClass = clazz;
                break;
            }
        }

        String argument = (args.length == 0) ? "" : args[0];

        Method method;
        try {
            method = commandMap.get(commandClass).get(argument).getA();
        } catch (NullPointerException e) {
            sender.sendMessage(StringUtil.color("&c알수 없는 명령어입니다."));
            return true;
        }

        String permission = commandMap.get(commandClass).get(argument).getB();

        if (permission != null && !sender.hasPermission(permission)) {
            sender.sendMessage(StringUtil.color("&c해당 명령어를 사용할 권한이 없습니다."));
            return true;
        }

        try {
            method.invoke(getCommandInstance(commandClass),sender, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
