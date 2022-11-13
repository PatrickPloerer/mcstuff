package net.bote.training.util;

import net.bote.training.frontend.listener.ItemInteractListener;
import net.bote.training.util.transport.InventoryClickTransporter;
import net.bote.training.util.transport.ItemInteractTransporter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author Elias Arndt | bote100
 * Created on 11.09.2019
 */

public class ItemBuilder {

    private Material material;
    private int amount;
    private int subid;
    private ItemMeta meta;
    private ItemStack itemStack;
    private String id;

    public ItemBuilder(Material material, int amount, int subid) {
        this.setMaterial(material);
        this.setAmount(amount);
        this.setSubid(subid);
        this.itemStack = new ItemStack(material, amount, (short)subid);
        this.meta = itemStack.getItemMeta();
    }

    @SuppressWarnings("deprecation")
	public ItemBuilder(ItemStack itemStack) {
        this.setMaterial(itemStack.getType());
        this.setAmount(itemStack.getAmount());
        this.setSubid(itemStack.getTypeId());
        this.meta = itemStack.getItemMeta();
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material, int amount) {
        this.setMaterial(material);
        this.setAmount(amount);
        this.itemStack = new ItemStack(material, amount, (short)0);
        this.meta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this.setMaterial(material);
        this.itemStack = new ItemStack(material);
        this.meta = itemStack.getItemMeta();
    }

    public ItemBuilder setName(String s) {
        this.meta.setDisplayName(s);
        return this;
    }

    public ItemBuilder setID(String id) {
        this.id = id;
        return this;
    }

    public ItemBuilder setLore(List<String> list) {
        this.meta.setLore(list);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        this.meta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder addAction(ItemInteractTransporter transporter) {
        ItemInteractListener.getInteractItemAction().put(this.meta.getDisplayName() + "-" + this.id, transporter);
        return this;
    }

    public ItemBuilder addClick(InventoryClickTransporter transporter) {
        ItemInteractListener.getClickItemAction().put(this.meta.getDisplayName() + "-" + this.id, transporter);
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getSubid() {
		return subid;
	}

	public void setSubid(int subid) {
		this.subid = subid;
	}

}
