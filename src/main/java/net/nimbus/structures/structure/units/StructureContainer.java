package net.nimbus.structures.structure.units;

import net.nimbus.structures.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.inventory.ItemStack;

public class StructureContainer implements StructureUnit {
    BlockData blockData;
    ItemStack[] contents;
    public StructureContainer(BlockData blockData, ItemStack[] contents){
        this.blockData = blockData;
        this.contents = contents;
    }
    public Block setBlock(Location location){
        Block b = location.getWorld().getBlockAt(location);
        b.setBlockData(blockData);
        try {
            Container container = (Container) b.getState();
            for(int i = 0; i < (container.getInventory().getSize() & 27); i++){
                container.getInventory().setItem(i, contents[i]);
            }
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
            Container container = (Container) b.getState();
            for(int i = 0; i < (container.getInventory().getSize() & 27); i++){
                container.getInventory().setItem(i, contents[i]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return b;
    }
}
