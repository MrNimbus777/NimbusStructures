package net.nimbus.structures.structure.units;

import net.nimbus.structures.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.Rail;

import java.util.Set;

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

    @Override
    public Block setBlock(Location location, BlockFace face) {
        Block b = location.getWorld().getBlockAt(location);
        if(blockData instanceof Directional bd){
            bd.setFacing(Utils.summingFaces(bd.getFacing(), face));
            b.setBlockData(bd);
        } else if(blockData instanceof MultipleFacing bd){
            Set<BlockFace> faceSet = bd.getFaces();
            for(BlockFace face1 : faceSet){
                bd.setFace(face1, false);
                bd.setFace(Utils.summingFaces(face1, face), true);
            }
            b.setBlockData(bd);
        } else if(blockData instanceof Rail bd){
            if(bd.getShape().name().contains("ASCENDING")){
                bd.setShape(Rail.Shape.valueOf("ASCENDING_"+Utils.summingFaces(BlockFace.valueOf(bd.getShape().name().replace("ASCENDING_", "")), face).name()));
            }
            b.setBlockData(bd);
        } else b.setBlockData(blockData);
        return b;
    }
}
