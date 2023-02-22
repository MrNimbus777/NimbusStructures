package net.nimbus.structures.events.world;

import net.nimbus.structures.NStructure;
import net.nimbus.structures.Point3;
import net.nimbus.structures.structure.Structure;
import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;


public class ChunkGenerateEvents implements Listener {
    @EventHandler
    public void CGE(ChunkLoadEvent e){
        Chunk c = e.getChunk();
        if(e.isNewChunk()){
            int x0 = 16*c.getX();
            int z0 = 16*c.getZ();
            HashMap<Biome, ArrayList<Point3>> biomes = new HashMap<>();
            for (int x = 0; x < 16; x++){
                for (int z = 0; z < 16; z++){
                    int y = c.getWorld().getHighestBlockYAt(x0+x, z0+z);
                    try {
                        Biome biome = c.getBlock(x, y, z).getBiome();
                        ArrayList<Point3> list = biomes.getOrDefault(biome, new ArrayList<>());
                        list.add(new Point3(x, y, z));
                        biomes.put(biome, list);
                    } catch (Exception ignored){}
                }
            }
            Biome biome = biggestBiome(biomes);
            int x = NStructure.a.r.nextInt(16);
            int z = NStructure.a.r.nextInt(16);
            Block b = c.getBlock(x, getMiddle(biomes.get(biome), "Y"), z);
            ArrayList<Structure> structures = new ArrayList<>();
            try{
                for(String name : NStructure.a.getConfig().getConfigurationSection("Generator").getKeys(false)){
                    if(NStructure.a.r.nextInt(NStructure.a.getConfig().getInt("Generator."+name+".chance")) < 1){
                        String[] sHeight = NStructure.a.getConfig().getString("Generator."+name+".height").split("-");
                        int min = Integer.parseInt(sHeight[0]);
                        int max = Integer.parseInt(sHeight[1]);
                        if(b.getY() >= min && b.getY() <= max){
                            for(String world : NStructure.a.getConfig().getStringList("Generator."+name+".worlds")){
                                if(world.equalsIgnoreCase(b.getWorld().getName())){
                                    for(String bi : NStructure.a.getConfig().getStringList("Generator."+name+".biomes")){
                                        if(bi.equalsIgnoreCase(biome.name())){
                                            structures.add(Structure.hash.get(name));
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex){}
            if(structures.size() > 0) {
               Structure str = structures.get(NStructure.a.r.nextInt(structures.size()));
               if(x < 4){
                   if(z < 4){
                       str.generate(b.getLocation());
                   } else {
                       str.generate(b.getLocation(), BlockFace.WEST);
                   }
               } else {
                   if(z < 4){
                       str.generate(b.getLocation(), BlockFace.EAST);
                   } else {
                        str.generate(b.getLocation(), BlockFace.SOUTH);
                   }
               }
            }
        }
    }
    public Biome biggestBiome(HashMap<Biome,  ArrayList<Point3>> biomes) {
        int largestID = 0;
        ArrayList<Biome> list = new ArrayList<>(biomes.keySet());
        for (int x = 1; x < list.size(); x++) {
            if (biomes.get(list.get(largestID)).size() < biomes.get(list.get(x)).size())
                largestID = x;
        }
        return list.get(largestID);
    }

    public int getMiddle(ArrayList<Point3> points, String coordonate) {
        Method method;
        try{
            method = Point3.class.getMethod("get"+coordonate.toUpperCase(Locale.ROOT));
            int amount = 0;
            int sum = 0;
            for(Point3 p : points){
                amount ++;
                sum += (int) method.invoke(p);
            }
            return sum/amount;
        } catch (Exception e){
            return 0;
        }
    }
}
