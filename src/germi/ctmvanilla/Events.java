/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package germi.ctmvanilla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author germi
 */
public class Events implements Listener{
    
    private int counter;
    
    public Events(){
        counter = 0;
    }
    
    private Color getColor(int i) {
        Color c = null;
        if(i==1){
        c=Color.AQUA;
        }
        if(i==2){
        c=Color.BLACK;
        }
        if(i==3){
        c=Color.BLUE;
        }
        if(i==4){
        c=Color.FUCHSIA;
        }
        if(i==5){
        c=Color.GRAY;
        }
        if(i==6){
        c=Color.GREEN;
        }
        if(i==7){
        c=Color.LIME;
        }
        if(i==8){
        c=Color.MAROON;
        }
        if(i==9){
        c=Color.NAVY;
        }
        if(i==10){
        c=Color.OLIVE;
        }
        if(i==11){
        c=Color.ORANGE;
        }
        if(i==12){
        c=Color.PURPLE;
        }
        if(i==13){
        c=Color.RED;
        }
        if(i==14){
        c=Color.SILVER;
        }
        if(i==15){
        c=Color.TEAL;
        }
        if(i==16){
        c=Color.WHITE;
        }
        if(i==17){
        c=Color.YELLOW;
        }

        return c;
    }
    
    private void fireworks(Player p){
            Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();
           
            //Our random generator
            Random r = new Random();   
 
            //Get the type
            int rt = r.nextInt(4) + 1;
            Type type = Type.BALL;       
            if (rt == 1) type = Type.BALL;
            if (rt == 2) type = Type.BALL_LARGE;
            if (rt == 3) type = Type.BURST;
            if (rt == 4) type = Type.CREEPER;
            if (rt == 5) type = Type.STAR;
           
            //Get our random colours   
            int r1i = r.nextInt(17) + 1;
            int r2i = r.nextInt(17) + 1;
            Color c1 = getColor(r1i);
            Color c2 = getColor(r2i);
           
            //Create our effect with this
            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
           
            //Then apply the effect to the meta
            fwm.addEffect(effect);
           
            //Generate some random power and set it
            int rp = r.nextInt(2) + 1;
            fwm.setPower(rp);
           
            //Then apply this to our rocket
            fw.setFireworkMeta(fwm);   
    }
    
    private void win(){
        counter = 0;
        Main.game.stop();
        new BukkitRunnable(){
            @Override
            public void run() {
                
                                
                if(counter == 40){
                    Bukkit.getScheduler().cancelTasks(Main.plugin);
                    return;
                }
                
                if(counter <= 20){
                    for(Player online : Main.plugin.getServer().getOnlinePlayers()){
                        if(counter == 0){
                            online.sendMessage("§9Victoria en §b" + Methods.formatSeconds(Main.game.getTime()) + ". §9¡ENHORABUENA!");
                            online.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                        }
   
                        if(counter % 4 == 0){
                            online.sendTitle("§a§l¡¡¡VICTORIA!!!!", "§9" + Methods.formatSeconds(Main.game.getTime()), 0, 20, 0);
                            fireworks(online);
                        }
                        else{
                            online.sendTitle("§a§l¡¡¡VICTORIA!!!!", "§b" + Methods.formatSeconds(Main.game.getTime()), 0, 20, 0);
                        }
                        online.playSound(online.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 2);
                        
                    }
                }
                counter++;
            }
            
        }.runTaskTimer(Main.plugin, 0L, 5L);
    }
    
    private void pickup(Material pick, Player player){
       if(Main.game.isStarted()){
            for(Material m : Main.game.getObjective()){
                for(Material i : Main.game.getPickedup()){
                    if(pick.equals(i)){
                        return;
                    }
                }
                if(m.equals(pick)){
                    Main.game.addItem(m);
                    
                    if(Main.game.getObjective().size() == Main.game.getPickedup().size() && !Main.game.getObjective().isEmpty()){
                        win();
                        return;
                    }
                    
                    for(Player p : Main.plugin.getServer().getOnlinePlayers()){
                        p.sendTitle("§6¡Item Recogido!", "§b" + player.getName() + "§9 ha recogido " + m.name() , 0, 20*4, 20);
                        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1);
                        ItemMeta im = gapple.getItemMeta();
                        im.setDisplayName("§cManzana de oro especial");
                        List<String> lore = new ArrayList<>();
                        lore.add("§d§o+2 ♥");
                        im.setLore(lore);
                        gapple.setItemMeta(im);
                        
                        p.getInventory().addItem(gapple);
                        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_AMBIENT, 100, 1);
                        p.sendMessage("§6Recibes una manzana de oro muy especial como recompensa.");
                        
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120*20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 120*20, 0));
                    
                   
                }
            }
        }
    }
    
    @EventHandler
    public void onDrop(PlayerPickupItemEvent e){
        pickup(e.getItem().getItemStack().getType(), e.getPlayer());
    }
    
    @EventHandler
    public void onCraft(CraftItemEvent e){
        if(e.getWhoClicked() instanceof Player){
            pickup(e.getRecipe().getResult().getType(), (Player) e.getWhoClicked());
        }
    }
    
    @EventHandler
    public void onExtract(FurnaceExtractEvent e){
        pickup(e.getItemType(), e.getPlayer());
    }
    
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(Main.game.isStarted()){
            e.getDrops().clear();
            Main.game.addDeath();
            e.setKeepInventory(true);   
        }
        
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        if(Main.game.isStarted()){
            Player p = e.getPlayer();
            if(p.getMaxHealth() > 4){
                p.setMaxHealth(p.getMaxHealth() - 4);
            }
            
            double newabs = 20 - p.getMaxHealth();
            if(newabs < 0){
                newabs = 0;
            }
            
            p.setAbsorptionAmount(newabs);
            p.sendMessage("§cRecibes una penalización de dos corazones... Ahora no puedes regenerarlos");
            
            
           
        }
    }
    
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        if(e.getItem().getType() == Material.GOLDEN_APPLE && Main.game.isStarted() && e.getItem().getItemMeta().hasLore() && e.getItem().getItemMeta().getLore().contains("§d§o+2 ♥")){
            //MANZANA ESPECIAL
            p.setMaxHealth(p.getMaxHealth() + 2);
            double newabs = p.getAbsorptionAmount() - 2;
            if(newabs < 0){
                newabs = 0;
            }
            p.setAbsorptionAmount(newabs);
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 0));
            p.setFoodLevel(p.getFoodLevel()+4);
            p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
            p.sendMessage("§7§oEstaba bastante buena...");
            
            e.setCancelled(true);
        }
    }
    
    
    
}
