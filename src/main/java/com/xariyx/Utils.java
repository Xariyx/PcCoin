package com.xariyx;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static String translateColor(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static boolean removeItemStack(Player player, ItemStack itemStack, int amount) {

        if (!player.getInventory().containsAtLeast(itemStack, amount)) {
            return false;
        }

        int i = amount;
        for (ItemStack stack : player.getInventory()) {

            if (i <= 0) {
                break;
            }

            if (stack == null) {
                continue;
            }

            if (stack.isSimilar(itemStack)) {

                while (stack.getAmount() > 0 && i > 0) {
                    int temp = stack.getAmount();
                    stack.setAmount(temp - 1);
                    i--;
                    player.updateInventory();
                }


            }

            player.updateInventory();


        }


        return true;


    }

    public static String preMessageBuilder(String[] parts) {

        StringBuilder result = new StringBuilder();
        for (String part : parts) {

            result.append(" ");
            result.append(part);

        }

        return result.toString();

    }


    public static void playPcCoin(Player player){
        player.stopSound(Sound.BLOCK_END_PORTAL_SPAWN);
        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, SoundCategory.BLOCKS, 3, 1);
    }

}
