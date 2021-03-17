package com.xariyx.Listeners;

import com.xariyx.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import static com.xariyx.Utils.translateColor;

public class MineListener implements Listener {

    final private ItemStack pcCoin;
    final private FileConfiguration messages;
    final private FileConfiguration config;

    public MineListener(Main main) {
        this.messages = main.getCustomConfig();
        this.pcCoin = main.getPcCoin();
        this.config = main.getConfig();
    }

    @EventHandler
    private void playerMineListener(BlockBreakEvent event) {


        ConfigurationSection cSection = config.getConfigurationSection("drop.blocks");
        if (cSection == null) {
            return;
        }

        String blockType = event.getBlock().getType().name();

        if (!cSection.contains(blockType)) {
            return;
        }

        if (Math.random() <= cSection.getDouble(blockType)) {
            return;
        }

        Player player = event.getPlayer();
        player.getInventory().addItem(pcCoin);
        player.sendMessage(translateColor(messages.getString("get")));

    }
}
