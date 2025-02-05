package com.github.jaeukkang12.lib.builder;

import com.github.jaeukkang12.lib.utils.StringUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("all")
public class ItemBuilder {
    private final ItemStack itemStack;
    private ItemMeta itemMeta;

    /**
     * ItemBuilder 오브젝트를 생성합니다.
     *
     * @param       material        생성할 타입
     */
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * ItemBuilder 오브젝트를 생성합니다.
     *
     * @param       material        생성할 타입
     * @param       amount          생성할 개수
     */
    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     * 아이템의 개수를 설정합니다.
     *
     * @param       amount          설정할 개수
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * 아이템의 이름을 설정합니다.
     *
     * @param       displayName     설정할 이름
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.displayName(Component.text(StringUtil.color(displayName)));
        return this;
    }

    /**
     * 아이템의 로어를 설정합니다.
     *
     * @param       lore            설정할 로어
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setLore(String... lore) {
        itemMeta.lore(Arrays.stream(lore).map(StringUtil::color).map(Component::text).collect(Collectors.toList()));
        return this;
    }

    /**
     * 아이템의 로어를 설정합니다.
     *
     * @param       lore            설정할 로어
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setLore(List<String> lore) {
        itemMeta.lore(lore.stream().map(StringUtil::color).map(Component::text).collect(Collectors.toList()));
        return this;
    }

    /**
     * 아이템의 로어를 추가합니다.
     *
     * @param       line            추가할 로어
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder addLoreLine(String line) {
        List<Component> lore = itemMeta.lore() == null ? new ArrayList<>() : itemMeta.lore();
        lore.add(Component.text(StringUtil.color(line)));
        itemMeta.lore(lore);
        return this;
    }

    /**
     * 아이템의 커스텀 모델 데이터를 설정합니다.
     *
     * @param       customModelData     설정할 커스텀 모델 데이터
     * @return      ItemBuilder         오브젝트
     */
    public ItemBuilder setCustomModelData(int customModelData) {
        itemMeta.setCustomModelData(customModelData);
        return this;
    }

    /**
     * 아이템의 ItemMeta를 설정합니다.
     *
     * @param       itemMeta        설정할 ItemMeta
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    /**
     * 아이템에 인챈트를 추가합니다. (1레벨)
     *
     * @param       enchantment     추가할 인챈트
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder addEnchantment(Enchantment enchantment) {
        itemMeta.addEnchant(enchantment, 1, true);
        return this;
    }

    /**
     * 아이템에 인챈트를 추가합니다.
     *
     * @param       enchantment     추가할 인챈트
     * @param       level           추가할 인챈트의 레벨
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, Integer level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * 아이템에 인챈트를 추가합니다. (인챈트북 전용)
     *
     * @param       enchantment     추가할 인챈트
     * @param       level           추가할 인챈트의 레벨
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder addBookEnchantment(Enchantment enchantment, Integer level) {
        ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(enchantment, level, true);
        return this;
    }

    /**
     * 아이템에서 인챈트를 제거합니다. (인챈트북 전용)
     *
     * @param       enchantment     제거할 인챈트
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder removeBookEnchantment(Enchantment enchantment) {
        ((EnchantmentStorageMeta) itemMeta).removeStoredEnchant(enchantment);
        return this;
    }

    /**
     * 아이템에 인챈트를 추가합니다.
     *
     * @param       enchantment     추가할 인챈트
     * @param       level           추가할 인챈트의 레벨
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, Integer level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * 아이템에서 인챈트를 제거합니다.
     *
     * @param       enchantment     제거할 인챈트
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        itemMeta.removeEnchant(enchantment);
        return this;
    }

    /**
     * 아이템의 내구성을 설정합니다.
     *
     * @param       durability      설정할 내구성
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setDamage(int damage) {
        ((Damageable)itemMeta).setDamage(damage);
        return this;
    }

    /**
     * 아이템을 부술 수 없는지 설정합니다.
     *
     * @param       unbreakable     부술 수 없는지 여부
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * 아이템에 PDC를 추가합니다.
     *
     * @param       key             PDC 키 (STRING)
     * @param       value           PDC 값 (STRING)
     * @param       type            PDC 타입 (PersistentDataType)
     * @param       plugin          플러그인
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder setPDC(String key, String value, PersistentDataType type, JavaPlugin plugin) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), type, value);
        return this;
    }

    /**
     * 아이템의 PDC를 제거합니다.
     *
     * @param       key             PDC 키 (STRING)
     * @param       plugin          플러그인
     * @return      ItemBuilder     재귀함수
     */
    public ItemBuilder removePDC(String key, JavaPlugin plugin) {
        itemMeta.getPersistentDataContainer().remove(new NamespacedKey(plugin, key));
        return this;
    }

    public ItemBuilder addFlag(ItemFlag flag) {
        itemMeta.addItemFlags(flag);
        return this;
    }

    /**
     * 아이템을 빌드합니다.
     *
     * @return      ItemStack       빌드된 아이템
     */
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}