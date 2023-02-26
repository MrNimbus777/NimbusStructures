package net.nimbus.structures.structure.units;

import net.nimbus.structures.Utils;
import net.nimbus.structures.structure.ChestDataPattern;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

public class StructureChestPattern implements StructureUnit {



    BlockData blockData;

    ChestDataPattern chestData;

    public StructureChestPattern(String chestData, BlockData blockData){
        this.blockData = blockData;
        this.chestData = ChestDataPattern.hash.getOrDefault(chestData, null);
        if(this.chestData == null) throw new NullPointerException();
    }

    @Override
    public Block setBlock(Location location) {
        Block b = location.getWorld().getBlockAt(location);
        b.setBlockData(blockData);
        try {
            Chest chest = (Chest) b.getState();
            chest.getInventory().setContents(chestData.generateContents());
        } catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public Block setBlock(Location location, BlockFace face) {
        Block b = location.getWorld().getBlockAt(location);
        Directional dir = (Directional) blockData;
        dir.setFacing(Utils.summingFaces(dir.getFacing(), face));
        b.setBlockData(dir);
        try {
            Chest chest = (Chest) b.getState();
            chest.getInventory().setContents(chestData.generateContents());
        } catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
