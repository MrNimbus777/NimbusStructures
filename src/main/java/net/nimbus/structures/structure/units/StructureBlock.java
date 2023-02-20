package net.nimbus.structures.structure.units;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class StructureBlock implements StructureUnit {
    BlockData blockData;

    public StructureBlock(BlockData blockData){
        this.blockData = blockData;
    }

    public Block setBlock(Location location){
        Block b = location.getWorld().getBlockAt(location);
        b.setBlockData(blockData);
        return b;
    }
}
