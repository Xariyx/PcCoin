package com.xariyx;

import com.xariyx.Listeners.InteractListener;
import com.xariyx.Listeners.KillListener;
import com.xariyx.Listeners.MineListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private ItemStack pcCoin;
    private FileConfiguration customConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        createCustomConfig();
        loadPcCoin();
        loader();

    }

    @Override
    public void onDisable() {

    }




     private void loadPcCoin() {
        ItemStack temp =  this.getConfig().getItemStack("pcCoin");
        assert temp != null;
        temp.setAmount(1);
        this.pcCoin = temp;

     }

     public ItemStack getPcCoin() {
        return this.pcCoin;
     }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void createCustomConfig() {
        File customConfigFile = new File(getDataFolder(), "messages.yml");
        if (!customConfigFile.exists()) {
            if(customConfigFile.getParentFile().mkdirs()){
                System.out.println(customConfigFile + " created!");
            }
            saveResource("messages.yml", false);
        }

        customConfig= new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }




    private void loader(){
        InteractListener interactListener = new InteractListener(this);
        Bukkit.getPluginManager().registerEvents(interactListener, this);
        Bukkit.getPluginManager().registerEvents(new KillListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MineListener(this), this);

        Objects.requireNonNull(this.getCommand("pccoin")).setExecutor(new Commands(this, interactListener));

    }

}
