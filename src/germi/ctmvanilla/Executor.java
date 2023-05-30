package germi.ctmvanilla;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Executor implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(label.equalsIgnoreCase("ctm")){
            if(args.length >= 1){
                switch(args[0]){
                    case "start":
                        if(sender.isOp() || sender.hasPermission("ctm.start")){
                            if(Main.game.isStarted()){
                            sender.sendMessage(ChatColor.RED + "La partida está ya empezada.");
                            }
                            else{
                                Main.game.start();
                                sender.sendMessage(ChatColor.GREEN + "¡Partida empezada!");
                            }    
                        }
                        else{
                            sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                        }
                        break;
                    case "stop":
                        if(sender.isOp() || sender.hasPermission("ctm.stop")){
                            if(!Main.game.isStarted()){
                                sender.sendMessage(ChatColor.RED + "No hay una partida empezada todavía.");
                            }
                            else{
                                Main.game.stop();
                                sender.sendMessage(ChatColor.GREEN + "Partida parada!");
                            }
                        }
                        else{
                            sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                        }
                        break;
                        
                    case "additem":
                        if(sender.isOp() || sender.hasPermission("ctm.additem")){
                            if(args.length == 2){
                            Material m = Material.getMaterial(args[1].toUpperCase());
                            for(Material i : Main.game.getObjective()){
                                if(i.equals(m)){
                                    sender.sendMessage(ChatColor.RED + "Este ítem ya está definido como objetivo.");
                                    return true;
                                }
                            }
                            
                            for(Material j : Material.values()){
                                if(j.equals(m)){
                                    Main.game.addObjective(m);
                                    sender.sendMessage(ChatColor.GREEN + "¡El ítem " + m.name() + " ha sido añadido!");
                                    return true;
                                }      
                            }

                            sender.sendMessage(ChatColor.RED + "Este ítem no existe.");
                            
                            }
                            else{
                                sender.sendMessage(ChatColor.RED + "¡Argumentos inválidos!");
                            }      
                        }
                        else{
                            sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                        }
                        
                        break;
                    
                    case "removeitem":
                        if(sender.isOp() || sender.hasPermission("ctm.removeitem")){
                            if(args.length == 2){
                            Material m = Material.getMaterial(args[1].toUpperCase());
                            for(Material i : Main.game.getObjective()){
                                if(i.equals(m)){
                                    Main.game.getObjective().remove(i);
                                    sender.sendMessage(ChatColor.GREEN + "Ítem " + m.name() + " eliminado!"); 
                                    return true;
                                }
                            }
                            
                            sender.sendMessage(ChatColor.RED + "No puedes eliminar este ítem");
                            
                            }
                            else{
                                sender.sendMessage(ChatColor.RED + "¡Argumentos inválidos!");
                            }
                            
                        }
                        else{
                            sender.sendMessage("§cNo tienes permisos para ejecutar este comando.");
                        }
                        
                        break;
                    case "status":
                        sender.sendMessage(Main.game.toString());
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "¡Argumentos inválidos!");
                        break;
                }
                
            }
            
        }
        return true;
    }
    
}
