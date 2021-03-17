package com.xariyx.Listeners;

import com.xariyx.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import static com.xariyx.Utils.translateColor;


public class KillListener implements Listener {

    final private ItemStack pcCoin;
    final private FileConfiguration messages;
    final private FileConfiguration config;

    public KillListener(Main main){
        this.messages = main.getCustomConfig();
        this.pcCoin = main.getPcCoin();
        this.config = main.getConfig();

    }

    @EventHandler
    private void EntityKilled(EntityDeathEvent event){

        if(event.getEntity().getKiller() == null) {
            return;
        }

        ConfigurationSection cSection = config.getConfigurationSection("drop.mobs");
        if(cSection == null){
            return;
        }

        String entityType = event.getEntity().getType().name();
        if(!cSection.contains(entityType)){
            return;
        }

        if(Math.random() <= cSection.getDouble(entityType)){
            return;
        }
        Player player = event.getEntity().getKiller();

        player.getInventory().addItem(pcCoin);
        player.sendMessage(translateColor(messages.getString("get")));



    }






}
