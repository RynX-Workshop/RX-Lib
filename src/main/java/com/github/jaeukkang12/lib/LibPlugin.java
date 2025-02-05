package com.github.jaeukkang12.lib;

import org.bukkit.plugin.java.JavaPlugin;

public class LibPlugin extends JavaPlugin {

    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        // Plugin Instance
        plugin = this;
    }

    /**
     * 플러그인 인스턴스를 반환합니다.
     *
     * @return {@link JavaPlugin} 플러그인 인스턴스
     */
    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
