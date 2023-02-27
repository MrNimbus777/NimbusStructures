package net.nimbus.structures.structure;

import net.nimbus.structures.*;
import net.nimbus.structures.structure.units.StructureBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.*;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Fence;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Structure {

    public static HashMap<String, Structure> hash = new HashMap<>();


    String key;
    int xBox;
    int yBox;
    int zBox;


    public Structure(String key, int xBox, int yBox, int zBox){
        this.key = key;
        this.xBox = xBox;
        this.yBox = yBox;
        this.zBox = zBox;

        hash.put(key, this);
    }

    public void generate(Location location, BlockFace face) {
        FileConfiguration config = getStructureYML(key).getConfig();
        switch (face) {
            case EAST -> {
                new Cicle() {
                    int x = 0;
                    int y = 0;
                    int z = 0;

                    @Override
                    public void run() {
                        for(int i = 0; i < 100; i++) {
                            String type = config.getString("blocks." + x + "." + y + "." + z + ".type");
                            if (type != null) {
                                Location loc = location.clone().add(-z, y, x);
                                switch (type) {
                                    case "block": {
                                        Block b = new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(loc, face);
                                        break;
                                    }
                                    case "container": {
                                        Block b = Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(loc, face);
                                        break;
                                    }
                                    case "chestGenerator":
                                        try {
                                            Block b = Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(loc, face);
                                        } catch (Exception e) {
                                        }
                                        break;
                                }
                            }
                            z++;
                            if (z > zBox) {
                                z = 0;
                                x++;
                                if (x > xBox) {
                                    x = 0;
                                    y++;
                                    if (y > yBox) {
                                        y = 0;
                                    }
                                }
                            }
                        }
                    }
                }.runCicle((xBox + 1) * (yBox + 1) * (zBox + 1)/100);
                return;
            }
            case SOUTH -> {
                new Cicle() {
                    int x = 0;
                    int y = 0;
                    int z = 0;

                    @Override
                    public void run() {
                        for(int i = 0; i < 100; i++) {
                            String type = config.getString("blocks." + x + "." + y + "." + z + ".type");
                            if (type != null) {
                                Location loc = location.clone().add(-x, y, -z);
                                switch (type) {
                                    case "block": {
                                        Block b = new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(loc, face);
                                        break;
                                    }
                                    case "container": {
                                        Block b = Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(loc, face);
                                        break;
                                    }
                                    case "chestGenerator":
                                        try {
                                            Block b = Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(loc, face);
                                        } catch (Exception e) {
                                        }
                                        break;
                                }
                            }
                            z++;
                            if (z > zBox) {
                                z = 0;
                                x++;
                                if (x > xBox) {
                                    x = 0;
                                    y++;
                                    if (y > yBox) {
                                        y = 0;
                                    }
                                }
                            }
                        }
                    }
                }.runCicle((xBox + 1) * (yBox + 1) * (zBox + 1)/100);
                return;
            }
            case WEST -> {
                new Cicle() {
                    int x = 0;
                    int y = 0;
                    int z = 0;

                    @Override
                    public void run() {
                        for(int i = 0; i < 100; i++) {
                            String type = config.getString("blocks." + x + "." + y + "." + z + ".type");
                            if (type != null) {
                                Location loc = location.clone().add(z, y, -x);
                                switch (type) {
                                    case "block": {
                                        new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(loc, face);
                                        break;
                                    }
                                    case "container": {
                                        Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(loc, face);
                                        break;
                                    }
                                    case "chestGenerator":
                                        try {
                                            Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(loc, face);
                                        } catch (Exception e) {
                                        }
                                        break;
                                }
                            }
                            z++;
                            if (z > zBox) {
                                z = 0;
                                x++;
                                if (x > xBox) {
                                    x = 0;
                                    y++;
                                    if (y > yBox) {
                                        y = 0;
                                    }
                                }
                            }
                        }
                    }
                }.runCicle((xBox + 1) * (yBox + 1) * (zBox + 1)/100);
                return;
            }
        }
        generate(location);
    }

    public void generate(Location location){
        FileConfiguration config = getStructureYML(this.key).getConfig();
        new Cicle() {
            int x = 0;
            int y = 0;
            int z = 0;
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    String type = config.getString("blocks." + x + "." + y + "." + z + ".type");
                    if (type != null) {
                        switch (type) {
                            case "block":
                                new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(location.clone().add(x, y, z));
                                break;
                            case "container":
                                Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(x, y, z));
                                break;
                            case "chestGenerator":
                                try {
                                    Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(x, y, z));
                                } catch (Exception e) {
                                }
                                break;
                        }
                    }
                    z++;
                    if (z > zBox) {
                        z = 0;
                        x++;
                        if (x > xBox) {
                            x = 0;
                            y++;
                            if (y > yBox) {
                                y = 0;
                            }
                        }
                    }
                }
            }
        }.runCicle((xBox+1)*(yBox+1)*(zBox+1)/100);
    }



    YmlFile getStructureYML(String key){
        return YmlFile.get("Structures", key);
    }

    public static void create(String key, Location loc1, Location loc2){
        YmlFile file = new YmlFile("Structures", key).create();
        if(!loc1.getWorld().equals(loc2.getWorld())) return;
        World w = loc1.getWorld();

        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        new Cicle() {
            @Override
            public void onFinish() {
                file.getConfig().set("size.xBox", Math.abs(x2-x1)+1);
                file.getConfig().set("size.yBox", Math.abs(y2-y1)+1);
                file.getConfig().set("size.zBox", Math.abs(z2-z1)+1);
                file.save();
                new Structure(key, Math.abs(x2-x1)+1, Math.abs(y2-y1)+1, Math.abs(z2-z1)+1);
                Bukkit.getServer().getConsoleSender().sendMessage(NStructure.a.getConfig().getString("Messages.Commands.structure.create.success").replace("%structure%", key).replace("%file%", file.getFile().getName()));
            }

            int x = x1;
            int y = y1;
            int z = z1;
            @Override
            public void run() {
                for(int i = 0; i < 100; i++) {
                    Block b = w.getBlockAt(x, y, z);
                    if (b.getState() instanceof Container c) {
                        if (ChestDataPattern.hash.containsKey(c.getCustomName()) && c.getType() == Material.CHEST) {
                            Utils.set(file.getConfig(), "blocks." + Math.abs(x - x1) + "." + Math.abs(y - y1) + "." + Math.abs(z - z1), ChestDataPattern.hash.get(c.getCustomName()));
                        } else
                            Utils.set(file.getConfig(), "blocks." + Math.abs(x - x1) + "." + Math.abs(y - y1) + "." + Math.abs(z - z1), c);
                    } else if (b.getType() == Material.SPAWNER) {
                        Utils.set(file.getConfig(), "blocks." + Math.abs(x - x1) + "." + Math.abs(y - y1) + "." + Math.abs(z - z1), (CreatureSpawner) b.getState());
                    } else if (b instanceof Sign) {
                        Utils.set(file.getConfig(), "blocks." + Math.abs(x - x1) + "." + Math.abs(y - y1) + "." + Math.abs(z - z1), (Sign) b);
                    } else {
                        Utils.set(file.getConfig(), "blocks." + Math.abs(x - x1) + "." + Math.abs(y - y1) + "." + Math.abs(z - z1), b.getBlockData());
                    }
                    z++;
                    if (z > z2) {
                        z = z1;
                        x++;
                        if (x > x2) {
                            x = x1;
                            y++;
                            if (y > y2) {
                                y = y1;
                                break;
                            }
                        }
                    }
                }
            }
        }.runCicle((Math.abs(x2-x1)+1)*(Math.abs(y2-y1)+1)*(Math.abs(z2-z1)+1)/100);
    }
}