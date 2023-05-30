package germi.ctmvanilla;

import org.bukkit.entity.Player;


public class Methods {
    
    public static void setDefaultHealth(){
        for(Player p : Main.plugin.getServer().getOnlinePlayers()){
            p.resetMaxHealth();
            p.setAbsorptionAmount(0);
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
        }
    }
    
    public static String formatSeconds(int timeInSeconds)
{
    int secondsLeft = timeInSeconds % 3600 % 60;
    int minutes = (int) Math.floor(timeInSeconds % 3600 / 60);
    int hours = (int) Math.floor(timeInSeconds / 3600);

    String HH = ((hours       < 10) ? "0" : "") + hours;
    String MM = ((minutes     < 10) ? "0" : "") + minutes;
    String SS = ((secondsLeft < 10) ? "0" : "") + secondsLeft;

    return HH + ":" + MM + ":" + SS;
}   
    
}
