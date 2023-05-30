package germi.ctmvanilla;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Game {
    
    private int countdown;
    private boolean started;
    private final ArrayList<Material> objective;
    private final ArrayList<Material> pickedup;
    private int time; //Seconds
    private int deaths;
    
    public Game(){
        countdown = 5;
        objective = new ArrayList<>();
        pickedup = new ArrayList<>();
        started = false;
        time = 0;
        deaths = 0;
    }
    
    public int getDeaths(){
        return deaths;
    }
    
    public void addDeath(){
        deaths++;
    }
    
    public void addObjective(Material m){
        objective.add(m);
    }
    
    public void addItem(Material m){
        pickedup.add(m);
    }

    public ArrayList<Material> getPickedup() {
        return pickedup;
    }
    
    public boolean isStarted(){
        return started;
    }

    public int getTime() {
        return time;
    }
    
    public ArrayList<Material> getObjective(){
        return objective;
    }

    public void setTime(int time) {
        this.time = time;
    }
    
    private void setScoreboard(){
        ScoreboardManager sm = Bukkit.getScoreboardManager();
        time = 0;
        
        new BukkitRunnable(){
            @Override
            public void run() {
                Scoreboard s = sm.getNewScoreboard();
                Objective info = s.registerNewObjective("info", "dummy", "§6§lCTM Vanilla");
                info.setDisplaySlot(DisplaySlot.SIDEBAR);
                info.getScore("    ").setScore(10);
                info.getScore("  §aObjetivos").setScore(9);
                info.getScore("  " + pickedup.size() +  " / " + objective.size()).setScore(8);
                info.getScore("   ").setScore(7);
                info.getScore("  §cTiempo").setScore(6);
                info.getScore("  " + Methods.formatSeconds(time)).setScore(5);
                info.getScore("  ").setScore(4);
                info.getScore("  §dMuertes: §f" + deaths).setScore(3);
                info.getScore(" ").setScore(2);
                info.getScore("  §7By Germi123re").setScore(1);
                info.getScore("").setScore(0);
                for(Player online : Main.plugin.getServer().getOnlinePlayers()){
                    online.setScoreboard(s);
                }
                time++;
            }
            
        }.runTaskTimer(Main.plugin, 0L, 20L);
        
        
        
        
    }
    
    
    public void start(){
        started = true;
        
        new BukkitRunnable(){
            @Override
            public void run() {
                if(countdown != 0){
                    for(Player p : Main.plugin.getServer().getOnlinePlayers()){
                        p.sendTitle(ChatColor.GOLD + Integer.toString(countdown), "", 0, 20, 0);
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 1);
                    }
                    countdown--;
                }
                else{
                    for(Player p : Main.plugin.getServer().getOnlinePlayers()){
                        p.sendTitle(ChatColor.GREEN + "GO!", "", 0, 40, 0);
                        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 2);
                    }
                    Bukkit.getScheduler().cancelTasks(Main.plugin);
                    setScoreboard();
                }
                
            }
            
        }.runTaskTimer(Main.plugin, 20L, 20L);
        countdown = 5;
        Methods.setDefaultHealth();
        
    }
    
    public void stop(){
        Bukkit.getScheduler().cancelTasks(Main.plugin);
        Methods.setDefaultHealth();
        pickedup.clear();
        started = false;
    }
    
    @Override
    public String toString(){
        String s = "";
        s += "§6Empezado: §a" + started + "\n";
        s += "§6Tiempo: §a" + Methods.formatSeconds(time) + "\n \n";
        s += "§6§l----§6§n§lITEMS§6§l----\n \n";
        for(Material m : objective){
            String color = "§c";
            for(Material p : pickedup){
                if(p.equals(m)){
                    color = "§a";
                }
            }
            
            s += "§6- " + color + m.name() + "\n";
        }
        
        return s;
    }
    
    
    
    
}
