package net.cherryflavor.api.cherrybungee.tools;

import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Chat {

    public static String colorize (String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> convertArrayToList(String... array) {
        List<String> newList = new ArrayList<>();
        for (String string : array) {
            newList.add(string);
        }
        return newList;
    }

    public static String carveList(List<String> list) {
        return list.toString().replace("[","").replace("]","");
    }

    public static boolean parseBoolean(String string) {
        if (string.equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
    }

}
