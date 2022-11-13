package net.bote.training.backend.translation;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Elias Arndt | bote100
 * Created on 25.01.2020
 */

public class TranslateableInventory {

    private HashMap<Integer, String> items = Maps.newHashMap();
    private int size;
    private String name;
    private InventoryType type;
    private InventoryHolder holder;

    public TranslateableInventory(Inventory inv, String name) {
        for(int i =0; i < inv.getSize(); i++) {
            if (inv.getItem(i) != null) {
                if(inv.getItem(i).getType() != Material.AIR) items.put(i, itemTo64(inv.getItem(i)));
            }
        }

        this.size = inv.getSize();
        this.name = name;
        this.type = inv.getType();
        this.holder = inv.getHolder();

        // DEBUG items.forEach((Integer i, String c) -> System.out.println("i -> " + c));

        if(!type.equals(InventoryType.ENDER_CHEST) && !type.equals(InventoryType.CHEST))
            throw new IllegalArgumentException("Only ENDER_CHEST or CHEST inventorys are valid.");

    }

    public TranslateableInventory(Inventory inv) {
        new TranslateableInventory(inv, "Inventar");
    }

    public Inventory getInventory() {
        Inventory inv;
        inv = Bukkit.createInventory(holder, size, name);
        for(int i = 0; i < inv.getSize(); i++) {
            try {
                if(items.containsKey(i)) {
                    inv.setItem(i, itemFrom64(items.get(i)));
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
        return inv;
    }

    public String itemTo64(ItemStack stack) throws IllegalStateException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeObject(stack);

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        }
        catch (Exception e) {
            throw new IllegalStateException("Unable to save item stack.", e);
        }
    }

    public ItemStack itemFrom64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            try {
                return (ItemStack) dataInput.readObject();
            } finally {
                dataInput.close();
            }
        }
        catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

}
