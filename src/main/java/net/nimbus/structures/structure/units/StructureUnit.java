package net.nimbus.structures.structure.units;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public interface StructureUnit {
    Block setBlock(Location location);
    Block setBlock(Location location, BlockFace face);
}
