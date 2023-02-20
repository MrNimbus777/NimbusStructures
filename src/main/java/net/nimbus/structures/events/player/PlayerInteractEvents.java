package net.nimbus.structures.events.player;

import net.nimbus.structures.NStructure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEvents implements Listener {
    boolean sleep = false;

    @EventHandler
    public void PIE(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            p.sendMessage("" + (e.getClickedBlock().getBlockData() instanceof Directional));
            if (!sleep) {
                sleep = true;
                if (p.hasPermission("nst.selection")) {
                    if (p.getEquipment().getItemInMainHand().getType() == NStructure.a.selection_tool) {
                        e.setCancelled(true);
                        Location loc = e.getClickedBlock().getLocation();
                        if (!NStructure.a.point1.getOrDefault(p.getName(), loc).getWorld().equals(loc.getWorld()))
                            NStructure.a.point1.remove(p.getName());
                        NStructure.a.point2.put(p.getName(), loc);
                    }
                }
            } else sleep = false;
        } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (p.hasPermission("nst.selection")) {
                if (p.getEquipment().getItemInMainHand().getType() == NStructure.a.selection_tool) {
                    e.setCancelled(true);
                    Location loc = e.getClickedBlock().getLocation();
                    if (!NStructure.a.point2.getOrDefault(p.getName(), loc).getWorld().equals(loc.getWorld()))
                        NStructure.a.point2.remove(p.getName());
                    NStructure.a.point1.put(p.getName(), loc);
                    p.sendMessage();
                }
            }
        }
    }
}