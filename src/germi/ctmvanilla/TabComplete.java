/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package germi.ctmvanilla;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 *
 * @author germi
 */
public class TabComplete implements TabCompleter{

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        
        if(label.equalsIgnoreCase("ctm") && args.length == 1){
            String[] list = {"status", "additem", "removeitem", "start", "stop"};
            List<String> listt = new ArrayList<>();
            for(String s : list){
                if(s.startsWith(args[0]))
                listt.add(s);
            }
            
            return listt;
        }
        
        if(label.equalsIgnoreCase("ctm") && args[0].equalsIgnoreCase("additem") && args.length == 2){
            List<String> list = new ArrayList<>();
            for(Material m : Material.values()){
                if(m.name().startsWith(args[1].toUpperCase()))
                list.add(m.name());
            }
            return list;
        }
        
        else if(label.equalsIgnoreCase("ctm") && args[0].equalsIgnoreCase("removeitem") && args.length == 2){
            List<String> list = new ArrayList<>();
            for(Material m : Main.game.getObjective()){
                if(m.name().startsWith(args[1].toUpperCase()))
                list.add(m.name());
            }
            
            return list;
        }
        return null;
    }
    
}
