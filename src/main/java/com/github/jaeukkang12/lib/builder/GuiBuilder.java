package com.github.jaeukkang12.lib.builder;

import com.github.jaeukkang12.lib.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiBuilder {

    private final Inventory gui;

    public GuiBuilder(String name, int rows) {
        this.gui = Bukkit.createInventory(null, 9 * rows, StringUtil.color(name));
    }

    public GuiBuilder setItem(int x, int y, ItemStack itemStack) {
        int slot = (y - 1) * 9 + (x - 1);
        gui.setItem(slot, itemStack);
        return this;
    }
}
