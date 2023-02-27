package net.nimbus.structures.commands.executors;

import net.nimbus.structures.NStructure;
import net.nimbus.structures.Utils;
import net.nimbus.structures.YmlFile;
import net.nimbus.structures.structure.Structure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class StructureCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("create")) {
                    if (args.length > 1) {
                        String key = args[1];
                        if (!YmlFile.exists("Structures", key, false, true)) {
                            if (NStructure.a.point1.containsKey(p.getName()) && NStructure.a.point2.containsKey(p.getName())) {
                                Location loc1 = NStructure.a.point1.get(p.getName());
                                Location loc2 = NStructure.a.point2.get(p.getName());
                                Structure.create(key, loc1, loc2);
                                p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.create.process").replace("%structure%", key).replace("%volume%", "" + (Math.abs(loc1.getX() - loc2.getX())+1) * (Math.abs(loc1.getY() - loc2.getY())+1) * (Math.abs(loc1.getZ() - loc2.getZ())+1))));
                            } else
                                p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.create.no-points").replace("%selection_tool%", NStructure.a.selection_tool.name())));
                        } else
                            p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.create.already-exists").replace("%name%", key)));
                    } else
                        p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.create.usage")));
                } else if (args[0].equalsIgnoreCase("generate")) {
                    if (args.length > 1) {
                        String key = args[1];
                        if (Structure.hash.containsKey(args[1])) {
                            Structure str = Structure.hash.get(key);
                            if (args.length > 2) {
                                try {
                                    str.generate(p.getLocation(), BlockFace.valueOf(args[2].toUpperCase(Locale.ROOT)));
                                } catch (Exception e) {
                                    str.generate(p.getLocation());
                                }
                            } else str.generate(p.getLocation());
                        } else
                            p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.generate.no-structure").replace("%name%", key)));
                    } else
                        p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.generate.usage")));
                } else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("delete")) {
                    if(args.length > 1) {
                        String key = args[1];
                        if (YmlFile.exists("Structures", key, false, true)) {
                            YmlFile file = YmlFile.get("Structures", key);
                            file.getFile().delete();
                            YmlFile.hash.remove("Structures/"+key);
                            p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.remove.removed").replace("%name%", key)));
                        } else
                            p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.generate.no-structure").replace("%name%", key)));
                    } else
                        p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.remove.usage")));
                } else {
                    p.sendMessage(Utils.toPrefix(NStructure.a.getConfig().getString("Messages.Commands.structure.usage")));
                    int max = (int) Math.sqrt(Material.values().length) + 1;
                    int x = 0;
                    int y = 0;
                    for(Material m : Material.values()){
                        try{
                            p.getLocation().add(x*2, -1 , y*2).getBlock().setType(Material.GRASS_BLOCK);
                            p.getLocation().add(x*2, 0 , y*2).getBlock().setType(m);
                            x++;
                            if(x > max){
                                y++;
                                x = 0;
                            }
                        } catch (Exception ex){}
                    }
                }
            }
        }
        return true;
    }
}
