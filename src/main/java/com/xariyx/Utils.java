package com.xariyx;

import org.bukkit.ChatColor;
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

        itemStack.setAmount(amount);
        player.getInventory().removeItem(itemStack);
        player.updateInventory();
        return true;


    }

    public static String preMessageBuilder(String[] parts){

        StringBuilder result = new StringBuilder();
        for (String part : parts) {

            result.append(" ");
            result.append(part);

        }

        return result.toString();

    }

}
