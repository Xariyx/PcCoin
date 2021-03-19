package com.xariyx;

import com.xariyx.Listeners.InteractListener;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import static com.xariyx.Utils.translateColor;


public class Commands implements CommandExecutor {

    Main main;
    ItemStack pcCoin;
    FileConfiguration messages;
    FileConfiguration config;
    InteractListener listener;

    public Commands(Main main, InteractListener listener) {
        this.messages = main.getCustomConfig();
        this.pcCoin = main.getPcCoin();
        this.config = main.getConfig();
        this.listener = listener;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        if (args.length <= 0) {
            return false;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("buff")) {

            int amount = config.getInt("costs.buff");

            if (!Utils.removeItemStack(player, pcCoin, amount)) {
                player.sendMessage(translateColor(messages
                        .getString("noCoin")
                        .replace("%amount%", String.valueOf(amount))));
                return true;
            }

            int duration = 600;
            PotionEffect mining = new PotionEffect(PotionEffectType.FAST_DIGGING, duration, 5);
            PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, duration, 2);
            PotionEffect jump = new PotionEffect(PotionEffectType.JUMP, duration, 2);
            PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, duration / 10, 5);
            PotionEffect attack = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, duration, 2);
            PotionEffect resist = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, duration, 2);
            PotionEffect levitation = new PotionEffect(PotionEffectType.LEVITATION, 3, 2);

            player.addPotionEffect(speed);
            player.addPotionEffect(mining);
            player.addPotionEffect(jump);
            player.addPotionEffect(regen);
            player.addPotionEffect(attack);
            player.addPotionEffect(resist);
            player.addPotionEffect(levitation);

            player.sendMessage(translateColor(messages.getString("godMode")));
            return true;

        }


        if (args[0].equalsIgnoreCase("message")) {

            if (args.length < 2) {

                player.sendMessage(translateColor((messages
                        .getString("shortMessage"))));
                return true;
            }

            int amount = config.getInt("costs.message");

            if (!Utils.removeItemStack(player, pcCoin, amount)) {
                player.sendMessage(translateColor(messages
                        .getString("noCoin")
                        .replace("%amount%", String.valueOf(amount))));
                return true;
            }


            String[] messageParts = new String[args.length - 1];
            System.arraycopy(args, 1, messageParts, 0, messageParts.length);


            Bukkit.getServer().broadcastMessage(
                    translateColor(messages
                            .getString("message")
                            .replace("%player%", player.getName())
                            .replace("%message%", Utils.preMessageBuilder(messageParts))
                    ));

            return true;
        }


        if (!player.isOp()) {
            player.sendMessage(translateColor(messages.getString("noPermission")));
            return true;
        }

        if (args[0].equalsIgnoreCase("set")) {
            player.sendMessage(translateColor(messages.getString("set")));
            config.set("pcCoin", player.getInventory().getItemInMainHand());
            main.saveConfig();
            return true;
        }

        if (args[0].equalsIgnoreCase("get")) {
            player.sendMessage(translateColor(messages.getString("get")));
            player.getInventory().addItem(pcCoin);
            Utils.playPcCoin(player);
            return true;
        }

        if (args[0].equalsIgnoreCase("addmob")) {
            player.sendMessage(translateColor(messages.getString("clickMob")));
            listener.setHasSelectedMob(player);

            return true;

        }


        if (args[0].equalsIgnoreCase("addblock")) {
            player.sendMessage(translateColor(messages.getString("clickBlock")));
            listener.setHasSelectedBlock(player);

            return true;

        }


        return true;
    }


}
