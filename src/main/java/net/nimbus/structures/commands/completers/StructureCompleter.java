package net.nimbus.structures.commands.completers;

import net.nimbus.structures.structure.Structure;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StructureCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        if (args.length == 1) {
            ArrayList<String> all = new ArrayList<>();
            all.add("generate");
            all.add("create");
            for(String arg : all){
                if(arg.startsWith(args[0].toLowerCase(Locale.ROOT))){
                    list.add(arg);
                }
            }
        } else if (args.length == 2) {
            if(args[0].equalsIgnoreCase("generate")){
                ArrayList<String> all = new ArrayList<>(Structure.hash.keySet());
                for(String arg : all){
                    if(arg.startsWith(args[0])){
                        list.add(arg);
                    }
                }
            } else if(args[0].equalsIgnoreCase("create")){
                return new ArrayList<>();
            } else return new ArrayList<>();
        } else if (args.length == 3) {
            if(args[0].equalsIgnoreCase("generate")){
                ArrayList<String> all = new ArrayList<>();
                all.add("north");
                all.add("east");
                all.add("west");
                all.add("south");
                for(String arg : all){
                    if(arg.startsWith(args[0])){
                        list.add(arg);
                    }
                }
            } else return new ArrayList<>();
        }
        return list;
    }
}
