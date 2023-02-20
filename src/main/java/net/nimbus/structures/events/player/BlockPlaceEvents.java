package net.nimbus.structures.events.player;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockPlaceEvents implements Listener {
    @EventHandler
    public void BPE(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Block b = e.getClickedBlock();
            if(b.getType() == Material.CHEST){
                Chest c = (Chest) b.getState();
                e.getPlayer().sendMessage(c.getCustomName());
            }
        }
    }
}
