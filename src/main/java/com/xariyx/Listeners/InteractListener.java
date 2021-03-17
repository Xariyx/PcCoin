package com.xariyx.Listeners;

import com.xariyx.Main;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static com.xariyx.Utils.translateColor;

import java.util.HashMap;

public class InteractListener implements Listener {

    private final Main main;
    private final HashMap<Player, Boolean> hasSelectedBlock = new HashMap<>();
    private final HashMap<Player, Boolean> hasSelectedMob = new HashMap<>();
    private final FileConfiguration config;
    private final FileConfiguration messages;

    public InteractListener(Main main) {
        this.config = main.getConfig();
        this.messages = main.getCustomConfig();
        this.main = main;

    }

    @EventHandler
    private void BlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!hasSelectedBlock.containsKey(player)) {
            return;
        }

        if (hasSelectedBlock.get(event.getPlayer()).equals(false)) {
            return;
        }

        Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }
        String blockName = block.getType().name();
        config.set("drop.blocks." + blockName, "0");

        main.saveConfig();
        player.sendMessage(translateColor(
                messages.getString("blockAdd", "%name%")
                        .replace("%name%", blockName)));

        hasSelectedBlock.put(player, false);

    }


    @EventHandler
    private void EntityInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (!hasSelectedMob.containsKey(player)) {
            return;
        }

        if (hasSelectedMob.get(event.getPlayer()).equals(false)) {
            return;
        }

        String mobName = event.getRightClicked().getType().name();
        config.set("drop.mobs." + mobName, 0);
        main.saveConfig();

        player.sendMessage(translateColor(
                messages.getString("mobAdd", "%name%")
                        .replace("%name%", mobName)));

        hasSelectedMob.put(player, false);

    }


    public void setHasSelectedMob(Player player) {
        this.hasSelectedMob.put(player, true);
    }

    public void setHasSelectedBlock(Player player) {
        this.hasSelectedBlock.put(player, true);
    }

}