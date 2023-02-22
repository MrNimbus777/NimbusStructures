package net.nimbus.structures.structure;

import net.nimbus.structures.*;
import net.nimbus.structures.structure.units.StructureBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Structure {

    public static HashMap<String, Structure> hash = new HashMap<>();

    public static HashMap<BlockFace, Integer> faceToInt = new HashMap<>();
    public static HashMap<Integer, BlockFace> intToFace = new HashMap<>();

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
                                switch (type) {
                                    case "block" -> {
                                        Block b = new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(location.clone().add(-z, y, x));
                                        try {
                                            Directional dir = (Directional) b.getBlockData();
                                            dir.setFacing(summingFaces(dir.getFacing(), face));
                                            b.setBlockData(dir);
                                        } catch (Exception ignored) {
                                            String data = b.getBlockData().getAsString();
                                            data = data.replace("north=", "hortn=");
                                            data = data.replace("west=", "north=");
                                            data = data.replace("south=", "west=");
                                            data = data.replace("east=", "south=");
                                            data = data.replace("hortn=", "east=");
                                            b.setBlockData(Bukkit.createBlockData(data));
                                        }
                                    }
                                    case "container" -> {
                                        Block b = Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(-z, y, x));
                                        try {
                                            Directional dir = (Directional) b.getBlockData();
                                            dir.setFacing(summingFaces(dir.getFacing(), face));
                                            b.setBlockData(dir);
                                        } catch (Exception ignored) {
                                            String data = b.getBlockData().getAsString();
                                            data = data.replace("north=", "hortn");
                                            data = data.replace("west=", "north=");
                                            data = data.replace("south=", "west=");
                                            data = data.replace("east=", "south=");
                                            data = data.replace("hortn=", "east=");
                                            b.setBlockData(Bukkit.createBlockData(data));
                                        }
                                    }
                                    case "chestGenerator" -> {
                                        try {
                                            Block b = Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(-z, y, x));
                                            try {
                                                Directional dir = (Directional) b.getBlockData();
                                                dir.setFacing(summingFaces(dir.getFacing(), face));
                                                b.setBlockData(dir);
                                            } catch (Exception ignored) {
                                                String data = b.getBlockData().getAsString();
                                                data = data.replace("north=", "hortn=");
                                                data = data.replace("west=", "north=");
                                                data = data.replace("south=", "west=");
                                                data = data.replace("east=", "south=");
                                                data = data.replace("hortn=", "east=");
                                                b.setBlockData(Bukkit.createBlockData(data));
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
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
                                switch (type) {
                                    case "block" -> {
                                        Block b = new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(location.clone().add(-x, y, -z));
                                        try {
                                            Directional dir = (Directional) b.getBlockData();
                                            dir.setFacing(summingFaces(dir.getFacing(), face));
                                            b.setBlockData(dir);
                                        } catch (Exception ignored) {
                                            String data = b.getBlockData().getAsString();
                                            data = data.replace("east=", "teas=");
                                            data = data.replace("west=", "east=");
                                            data = data.replace("teas=", "west=");
                                            data = data.replace("north=", "hortn=");
                                            data = data.replace("south=", "north=");
                                            data = data.replace("hortn=", "south=");
                                            b.setBlockData(Bukkit.createBlockData(data));
                                        }
                                    }
                                    case "container" -> {
                                        Block b = Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(-x, y, -z));
                                        try {
                                            Directional dir = (Directional) b.getBlockData();
                                            dir.setFacing(summingFaces(dir.getFacing(), face));
                                            b.setBlockData(dir);
                                        } catch (Exception ignored) {
                                            String data = b.getBlockData().getAsString();
                                            data = data.replace("east=", "teas=");
                                            data = data.replace("west=", "east=");
                                            data = data.replace("teas=", "west=");
                                            data = data.replace("north=", "hortn=");
                                            data = data.replace("south=", "north=");
                                            data = data.replace("hortn=", "south=");
                                            b.setBlockData(Bukkit.createBlockData(data));
                                        }
                                    }
                                    case "chestGenerator" -> {
                                        try {
                                            Block b = Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(-x, y, -z));
                                            try {
                                                Directional dir = (Directional) b.getBlockData();
                                                dir.setFacing(summingFaces(dir.getFacing(), face));
                                                b.setBlockData(dir);
                                            } catch (Exception ignored) {
                                                String data = b.getBlockData().getAsString();
                                                data = data.replace("east=", "teas=");
                                                data = data.replace("west=", "east=");
                                                data = data.replace("teas=", "west=");
                                                data = data.replace("north=", "hortn=");
                                                data = data.replace("south=", "north=");
                                                data = data.replace("hortn=", "south=");
                                                b.setBlockData(Bukkit.createBlockData(data));
                                            }
                                        } catch (Exception ignored) {
                                        }
                                    }
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
                                switch (type) {
                                    case "block": {
                                        Block b = new StructureBlock(Utils.getBlockData(config, "blocks." + x + "." + y + "." + z)).setBlock(location.clone().add(z, y, -x));

                                        try {
                                            Directional dir = (Directional) b.getBlockData();
                                            dir.setFacing(summingFaces(dir.getFacing(), face));
                                            b.setBlockData(dir);
                                        } catch (Exception ignored) {
                                            String data = b.getBlockData().getAsString();
                                            data = data.replace("north=", "hortn=");
                                            data = data.replace("east=", "north=");
                                            data = data.replace("south=", "east=");
                                            data = data.replace("west=", "south=");
                                            data = data.replace("hortn=", "west=");
                                            b.setBlockData(Bukkit.createBlockData(data));
                                        }
                                        break;
                                    }
                                    case "container": {
                                        Block b = Utils.getContainer(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(z, y, -x));
                                        try {
                                            Directional dir = (Directional) b.getBlockData();
                                            dir.setFacing(summingFaces(dir.getFacing(), face));
                                            b.setBlockData(dir);
                                        } catch (Exception ignored) {
                                            String data = b.getBlockData().getAsString();
                                            data = data.replace("north=", "hortn=");
                                            data = data.replace("east=", "north=");
                                            data = data.replace("south=", "east=");
                                            data = data.replace("west=", "south=");
                                            data = data.replace("hortn=", "west=");
                                            b.setBlockData(Bukkit.createBlockData(data));
                                        }
                                        break;
                                    }
                                    case "chestGenerator":
                                        try {
                                            Block b = Utils.getChestPattern(config, "blocks." + x + "." + y + "." + z).setBlock(location.clone().add(z, y, -x));
                                            try {
                                                Directional dir = (Directional) b.getBlockData();
                                                dir.setFacing(summingFaces(dir.getFacing(), face));
                                                b.setBlockData(dir);
                                            } catch (Exception ignored) {
                                                String data = b.getBlockData().getAsString();
                                                data = data.replace("north=", "hortn=");
                                                data = data.replace("east=", "north=");
                                                data = data.replace("south=", "east=");
                                                data = data.replace("west=", "south=");
                                                data = data.replace("hortn=", "west=");
                                                b.setBlockData(Bukkit.createBlockData(data));
                                            }
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

    BlockFace summingFaces(BlockFace face1, BlockFace face2){
        return intToFace.get(Math.abs((faceToInt.getOrDefault(face1, 0)+faceToInt.getOrDefault(face2, 0)) % 4));
    }

    YmlFile getStructureYML(String key){
        return YmlFile.get("Structures", key);
    }

    public static void loadHashes(){
        faceToInt.put(BlockFace.NORTH, 0);
        faceToInt.put(BlockFace.EAST, 1);
        faceToInt.put(BlockFace.SOUTH, 2);
        faceToInt.put(BlockFace.WEST, 3);

        intToFace.put(0, BlockFace.NORTH);
        intToFace.put(1, BlockFace.EAST);
        intToFace.put(2, BlockFace.SOUTH);
        intToFace.put(3, BlockFace.WEST);
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