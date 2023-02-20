package net.nimbus.structures.structure;

import net.nimbus.structures.NStructure;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ChestDataPattern {
    public static HashMap<String, ChestDataPattern> hash = new HashMap<>();

    String key;
    int max_slots;
    int min_slots;
    ArrayList<ItemStackPattern> generatedItemStacks;

    public ChestDataPattern(String key, int max_slots, int min_slots, ArrayList<ItemStackPattern> generatedItemStacks){
        this.key = key;
        if(max_slots < min_slots){
            this.max_slots = min_slots;
            this.min_slots = max_slots;
        } else {
            this.max_slots = max_slots;
            this.min_slots = min_slots;
        }
        this.generatedItemStacks = generatedItemStacks;

        hash.put(key, this);
    }

    public ItemStack[] generateContents(){
        int size = generatedItemStacks.size();
        int slots = NStructure.a.r.nextInt(min_slots, max_slots+1);
        ItemStack[] contents = new ItemStack[27];
        for(int i = 0; i < 27; i++){
            if(NStructure.a.r.nextInt(27)+1 < slots){
                ItemStackPattern gItem = generatedItemStacks.get(NStructure.a.r.nextInt(size));
                int amount = NStructure.a.r.nextInt(gItem.getMin(), gItem.getMax()+1);
                ItemStack item = gItem.getItem().clone();
                item.setAmount(amount);
                contents[i] = item;
            }
        }
        return contents;
    }

    public String getKey() {
        return key;
    }
}
