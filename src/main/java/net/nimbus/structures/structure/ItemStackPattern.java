package net.nimbus.structures.structure;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemStackPattern {

    public static HashMap<String, ItemStackPattern> hash = new HashMap<>();


    ItemStack item;
    int max;
    int min;

    public ItemStackPattern(String key, ItemStack item, int max, int min){
        this.item = item.clone();
        if(max < min){
            this.max = min;
            this.min = max;
        } else {
            this.max = max;
            this.min = min;
        }

        hash.put(key, this);
    }

    public ItemStack getItem() {
        return item;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
