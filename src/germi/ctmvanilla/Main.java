/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package germi.ctmvanilla;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin{
    
    public static Main plugin;
    public static Game game;
   
    
    
    @Override
    public void onEnable(){
        
        for(Player p : getServer().getOnlinePlayers()){
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        
        plugin = this;
        game = new Game();
        Executor e = new Executor();
        Events ev = new Events();
        TabComplete t = new TabComplete();
        this.getServer().getPluginManager().registerEvents(ev, plugin);
        this.getCommand("ctm").setExecutor(e);
        this.getCommand("ctm").setTabCompleter(t);
    }
    
    @Override
    public void onDisable(){
        Methods.setDefaultHealth();
    }
           
}
