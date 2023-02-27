package net.nimbus.structures;

import net.nimbus.structures.commands.completers.StructureCompleter;
import net.nimbus.structures.commands.executors.StructureCommand;
import net.nimbus.structures.events.player.BlockPlaceEvents;
import net.nimbus.structures.events.player.PlayerInteractEvents;
import net.nimbus.structures.events.world.ChunkGenerateEvents;
import net.nimbus.structures.structure.ChestDataPattern;
import net.nimbus.structures.structure.ItemStackPattern;
import net.nimbus.structures.structure.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class NStructure extends JavaPlugin {

    public static NStructure a;

    public Random r;

    public HashMap<String, Location> point1 = new HashMap<>();
    public HashMap<String, Location> point2 = new HashMap<>();


    public YmlFile itemPatterns;
    public YmlFile chestPatterns;

    public Material selection_tool;

    public String prefix;

    public void loadConfig(boolean reload){
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
            getLogger().info("Created new config.yml file at " + config.getPath());
        } else if (reload) {
            try {
                getConfig().load(config);
                getLogger().info("Config reloaded.");
            } catch (Exception exception) {
            }
        }
    }
    void loadEvents(){
        loadEvent(new BlockPlaceEvents());
        loadEvent(new ChunkGenerateEvents());
        loadEvent(new PlayerInteractEvents());
    }
    void loadCommands(){
        loadCommand("structure", new StructureCommand(), new StructureCompleter());
    }

    void loadVariables(){
        try {
            selection_tool = Material.valueOf(getConfig().getString("Settings.selection_tool_material").toUpperCase(Locale.ROOT));
        } catch (Exception e){
            selection_tool = Material.WOODEN_AXE;
        }
        prefix = Utils.toColor(getConfig().getString("Settings.prefix"));
    }

    public void onEnable() {
        a = this;
        r = new Random();

        loadConfig(false);
        loadCommands();
        loadEvents();
        loadVariables();
        Utils.loadHashes();

        saveResource("Patterns/ItemPatterns.yml", false);
        itemPatterns = new YmlFile("Patterns", "ItemPatterns").create();
        saveResource("Patterns/ChestPatterns.yml", false);
        chestPatterns = new YmlFile("Patterns", "ChestPatterns").create();

        getLogger().info("Loading Items Patterns. . .");
        try {
            for (String key : itemPatterns.getConfig().getConfigurationSection("").getKeys(false)) {
                ItemStack item;
                try {
                    item = itemPatterns.getConfig().getItemStack(key+".item");
                    if (item == null) {
                        getLogger().severe("Unable to load Item "+key+": null");
                        continue;
                    }
                } catch (Exception e){
                    getLogger().severe("Unable to load Item "+key+": null");
                    continue;
                }
                int max = itemPatterns.getConfig().getInt(key+".max");
                int min = itemPatterns.getConfig().getInt(key+".min");
                new ItemStackPattern(key, item, min, max);
            }
            getLogger().info("Loaded Item Patterns!");
            getLogger().info("Loading Chests Patterns. . .");
            try {
                for (String key : chestPatterns.getConfig().getConfigurationSection("").getKeys(false)) {
                    int max = chestPatterns.getConfig().getInt(key+".max_slots");
                    int min = chestPatterns.getConfig().getInt(key+".min_slots");
                    ArrayList<ItemStackPattern> generatedItems = new ArrayList<>();
                    chestPatterns.getConfig().getStringList(key+".items").forEach(item -> {
                        if(ItemStackPattern.hash.containsKey(item)){
                            generatedItems.add(ItemStackPattern.hash.get(item));
                        }
                    });
                    new ChestDataPattern(key, max, min, generatedItems);
                }
                getLogger().info("Loaded Chest Patterns!");
            } catch (Exception e){
                e.printStackTrace();
                getLogger().severe("Unable to load Chest Patterns.");
            }
        } catch (Exception e){
            e.printStackTrace();
            getLogger().severe("Unable to load Item Patterns.");
        }
        for(String key : YmlFile.getDirList("Structures")){
            key = key.replace(".yml", "");
            getLogger().info("Loading Structure "+key+"!");
            if(YmlFile.exists("Structures", key, false, true)){
                YmlFile file = YmlFile.get("Structures", key);
                int xBox = file.getConfig().getInt("size.xBox");
                int yBox = file.getConfig().getInt("size.yBox");
                int zBox = file.getConfig().getInt("size.zBox");

                new Structure(key, xBox, yBox, zBox);
                getLogger().info("Structure "+key+" loaded!");
            }
        }
    }

    public void onDisable(){

    }

    void loadEvent(Listener listener){
        try {
            getServer().getPluginManager().registerEvents(listener, a);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void loadCommand(String cmd, CommandExecutor executor){
        try {
            getCommand(cmd).setExecutor(executor);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    void loadCommand(String cmd, CommandExecutor executor, TabCompleter completer){
        try {
            getCommand(cmd).setExecutor(executor);
            getCommand(cmd).setTabCompleter(completer);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
