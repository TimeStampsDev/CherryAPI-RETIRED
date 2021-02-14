package net.cherryflavor.api.cherrybungee.tools;

import net.cherrybungee.CherryProxy;

public class CherryDebug {

    public static void debuginfo(String debug) {

        if (ConfigYML.getConfig().getString("debug").equalsIgnoreCase("true")) {
            CherryProxy.getCherryProxy().log(debug);
        }
    }

    public static void debugDB(String debug) {
        debuginfo(Chat.colorize("&c[Cherry-DB] &f") + debug);
    }

    public static void debugEvents(String debug) {
        debuginfo(Chat.colorize("&e[Events] &f") + debug);
    }

    public static void debugCommmands(String debug) {
        debuginfo(Chat.colorize("&6[Commands] &f") + debug);
    }

    public static void debugRanks(String debug) {
        debuginfo(Chat.colorize("&9[Ranks] &f") + debug);
    }

    public static void debugChatChannels(String debug) {
        debuginfo(Chat.colorize("&1[ChatChannels] &f") + debug);
    }

}
