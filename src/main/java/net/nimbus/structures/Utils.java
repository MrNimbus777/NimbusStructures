package net.nimbus.structures;

import net.md_5.bungee.api.ChatColor;
import net.nimbus.structures.structure.ChestDataPattern;
import net.nimbus.structures.structure.units.StructureContainer;
import net.nimbus.structures.structure.units.StructureChestPattern;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void set(FileConfiguration config, String key, BlockData blockData){
        config.set(key, null);
        config.set(key+".type", "block");
        config.set(key+".blockData", blockData.getAsString());
    }
    public static void set(FileConfiguration config, String key, Container chest){

        config.set(key+".type", "container");
        config.set(key+".blockData", chest.getBlockData().getAsString());
        for(int i = 0; i < chest.getInventory().getSize(); i++)config.set(key+".inventory."+i, chest.getInventory().getItem(i));
    }
    public static void set(FileConfiguration config, String key, ChestDataPattern chest){
        config.set(key, null);
        config.set(key+".type", "chestGenerator");
        config.set(key+".chestData", chest.getKey());
    }

    public static BlockData getBlockData(FileConfiguration config, String key){
        try {
            return Bukkit.getServer().createBlockData(config.getString(key+".blockData"));
        } catch (Exception e){
            e.printStackTrace();
            return Bukkit.getServer().createBlockData(Material.STONE);
        }
    }
    public static BlockData getBlockData(FileConfiguration config, String key, Material default_material){
        try {
            return Bukkit.getServer().createBlockData(config.getString(key+".blockData"));
        } catch (Exception e){
            e.printStackTrace();
            return Bukkit.getServer().createBlockData(default_material);
        }
    }
    public static StructureContainer getContainer(FileConfiguration config, String key){
        ItemStack[] contents = new ItemStack[27];
        for(int i = 0; i < 27; i++){
            try {
                contents[i] = config.getItemStack(key+".inventory."+i);
            } catch (Exception e){
                contents[i] = null;
                e.printStackTrace();
            }
        }
        return new StructureContainer(getBlockData(config, key, Material.CHEST), contents);
    }

    public static StructureChestPattern getChestPattern(FileConfiguration config, String key){
        String chest = config.getString(key+".chestData")+"";
        if(ChestDataPattern.hash.containsKey(chest)){
            return new StructureChestPattern(chest, getBlockData(config, key, Material.CHEST));
        }
        return null;
    }


    private static final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
    /**
     * Returns a colored String. Supports HEX format
     * */
    public static String toColor(String str) {
        Matcher match = pattern.matcher(str);
        while (match.find()) {
            String color = str.substring(match.start() + 1, match.end());
            str = str.replace("&" + color, ChatColor.of(color) + "");
            match = pattern.matcher(str);
        }
        return str.replace("&", "\u00a7");
    }
    /**
     * Returns a prefixed colored String
     * */
    public static String toPrefix(String str) {
        return NStructure.a.prefix+toColor(str);
    }
}
