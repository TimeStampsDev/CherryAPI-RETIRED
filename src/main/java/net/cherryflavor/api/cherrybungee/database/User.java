package net.cherryflavor.api.cherrybungee.database;

import net.cherryflavor.api.cherrybungee.CherryProxy;
import net.cherryflavor.api.cherrybungee.database.punishments.PunishInfo;
import net.cherryflavor.api.cherrybungee.database.punishments.PunishmentsDB;
import net.cherryflavor.api.cherrybungee.tools.Chat;
import net.cherryflavor.api.cherrybungee.tools.CherryDebug;
import net.cherryflavor.api.cherrybungee.tools.ConfigYML;
import net.cherryflavor.api.cherrybungee.ranks.Rank;
import net.cherryflavor.api.cherrybungee.ranks.RankManagement;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {

    private String playerID;
    private int currency;
    private long lastSeen;
    private Rank rank;
    private String username;

    private ProxiedPlayer player;

    public User(ProxiedPlayer proxiedPlayer) {
        this.player = proxiedPlayer;
        this.playerID = proxiedPlayer.getUUID();
        this.currency = retrieveCurrency(playerID);
        this.lastSeen = retrieveLastSeen(playerID);
        this.username = proxiedPlayer.getName();

        proxiedPlayer.getAddress().getAddress();
    }

    public static List<String> getRegisteredUsersList() {
        List<String> registered = new ArrayList<>();
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + ";");
        try {
            while (result.next()) {
                registered.add(result.getString("username"));
            }
        } catch (SQLException throwables) {
            return null;
        }
        return registered;
    }

    public static List<String> getOnlineUsers() {
        List<String> onlineUsers = new ArrayList<>();
        for (User user : CherryProxy.getCherryProxy().getOnlineUsers()) {
            onlineUsers.add(user.getUsername());
        }
        return onlineUsers;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPlayerID() { return this.playerID; }

    public Long getLastSeen() { return this.lastSeen; }

    public void sendMessage(String message) {
        player.sendMessage(new TextComponent(Chat.colorize(message)));
    }

    public void sendMessageWithoutColor(String message) {
        player.sendMessage(new TextComponent(message));
    }

    public void sendMessage(TextComponent component) {
        player.sendMessage(component);
    }

    public void sendMessage(BaseComponent component) {
        player.sendMessage(component);
    }

    public void sendMessage(BaseComponent[] components) {
        player.sendMessage(components);
    }



    public void sendTo(ServerInfo server) {
        player.connect(server);
    }

    public ServerInfo getCurrentServer() {
        return player.getServer().getInfo();
    }

    public int getCurrency() {
        return this.currency;
    }

    public Rank getRank() {
        return RankManagement.parseFromString(retrieveRank(playerID));
    }

    public void kick(String reason, User by) {
        PunishmentsDB.registerKick(new User(player), by, reason);
        player.disconnect(new TextComponent(Chat.colorize(ConfigYML.getPunishMessages().getString("kick.to-target").replace("%performedby%", by.getUsername()).replace("%reason%",reason))));
    }

    public void ban(String reason, User by) {
        player.disconnect(new TextComponent(Chat.colorize(ConfigYML.getPunishMessages().getString("ban.to-target").replace("%performedby%",by.getUsername()).replace("%reason%",reason))));
    }

    public void tempban(String time, String reason, long expiration, long amountedtime, User by) {
        player.disconnect(new TextComponent(Chat.colorize(ConfigYML.getPunishMessages().getString("tempban.to-target").replace("%performedby%",by.getUsername()).replace("%reason%",reason).replace("%time%",time))));
    }

    public PunishInfo getPunishInfo() {
        return new PunishInfo(username);
    }

    ///             STATIC METHODS                                                                                  ////

    public static void register(ProxiedPlayer connectedPlayer) {
        String playerID = connectedPlayer.getUUID();
        int currency = 0;
        String rank = "member";
        String username = connectedPlayer.getName();
        String ip = connectedPlayer.getPendingConnection().getSocketAddress().toString().split(":")[0].replace("/","").replace(".","x");
        CherryDB.insertInto("INSERT INTO " + CherryDB.playerdata + " (PlayerID, currency, donater, username, ip, lastseen) VALUES ('" + playerID + "'," + currency + ",'" + rank + "','" + username + "','" + ip + "', 0);");
        CherryDebug.debugDB("INSERT INTO " + CherryDB.playerdata + " (PlayerID, currency, donater, username, ip, lastseen) VALUES ('" + playerID + "'," + currency + ",'" + rank + "','" + username + "','" + ip + "', 0);");
    }

    public static boolean check(ProxiedPlayer connectedPlayer) {
        String playerID = connectedPlayer.getUUID();
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + " WHERE PlayerID = '" + playerID + "';");
        try {
            while (result.next()) {
                return false;
            }
        } catch (SQLException throwables) {
            return false;
        }
        updateName(connectedPlayer);
        return true;
    }

    public static String getIP(String playerID) {
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + " WHERE PlayerID = '" + playerID + "';");
        try {
            while (result.next()) {
                return result.getString("ip").replace("x",".");
            }
        } catch (SQLException throwables) {
            return null;
        }
        return null;
    }

    public void updateData() {
        String ip =  player.getPendingConnection().getSocketAddress().toString().split(":")[0].replace("/","").replace(".","x");
        long lastSeen = System.currentTimeMillis();
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET username = '" + player.getName() + "', ip = '" + ip + "', lastseen = " + lastSeen + " WHERE PlayerID = '" + playerID + "';");
    }

    public static void updateName(ProxiedPlayer player) {
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET username = '" + player.getName() + "' WHERE PlayerID = '" + player.getUniqueId().toString() + "';");
        CherryDebug.debugDB("Name updated for " + player.getName() + "...");
    }

    public static void updateIP(ProxiedPlayer player) {
        String ip =  player.getPendingConnection().getSocketAddress().toString().split(":")[0].replace("/","").replace(".","x");
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET ip = '" + ip + "' WHERE PlayerID = '" + player.getUniqueId().toString() + "';");
        CherryDebug.debugDB("Ip updated for " + player.getName() + "... " + ip);
    }

    public static void updateLastSeen(ProxiedPlayer player) {
        long lastSeen = System.currentTimeMillis();
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET lastseen = " + lastSeen + " WHERE PlayerID = '" + player.getUniqueId().toString() + "';");
        CherryDebug.debugDB("Last seen updated for " + player.getName() + "...");
    }

    public static void addCurrnecy(int addition, ProxiedPlayer player) {
        int newCurrency = retrieveCurrency(player.getUniqueId().toString()) + addition;
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET currency = " + newCurrency + " WHERE PlayerID = '" + player.getUniqueId().toString() + "';");
        CherryDebug.debugDB("Currency updated for " + player.getName() + " (added " + addition + ")...");
    }

    public static void subtractCurrnecy(int subtraction, ProxiedPlayer player) {
        int newCurrency = retrieveCurrency(player.getUniqueId().toString()) + subtraction;
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET currency = " + newCurrency + " WHERE PlayerID = '" + player.getUniqueId().toString() + "';");
        CherryDebug.debugDB("Last seen updated for " + player.getName() + "(subtracted " + subtraction + ")...");
    }

    private static int retrieveCurrency(String playerID) {
        ResultSet result = CherryDB.getResultSet("SELECT currency FROM " + CherryDB.playerdata + " WHERE PlayerID = '" + playerID + "';");
        try {
            while (result.next()) {
                return (int) result.getObject("currency");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static long retrieveLastSeen(String playerID) {
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + " WHERE PlayerID = '" + playerID + "';");
        try {
            while (result.next()) {
                return (long) result.getObject("lastseen");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private String retrieveRank(String playerID) {
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + " WHERE PlayerID = '" + playerID + "';");
        try {
            while (result.next()) {
                this.rank = RankManagement.parseFromString((String) result.getObject("donater"));
                return (String) result.getObject("donater");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "Member";
    }

    public static boolean isOnline(String name) {
        if (CherryProxy.getCherryProxy().getProxy().getPlayer(name) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String retrieveUUIDbyName(String username) throws NullPointerException {
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + " WHERE username = '" + username + "';");
        try {
            while (result.next()) {

                return (String) result.getString("PlayerID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String retrieveNameByUUID(String UUID) throws NullPointerException {
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + CherryDB.playerdata + " WHERE PlayerID = '" + UUID + "';");
        try {
            while (result.next()) {
                return (String) result.getString("username");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
