package net.cherryflavor.api.cherrybungee.ranks;

import net.cherryflavor.api.cherrybungee.CherryProxy;
import net.cherryflavor.api.cherrybungee.database.CherryDB;
import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.tools.CherryDebug;

import net.cherryflavor.api.cherrybungee.chatchannels.ChatChannelManager;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RankManagement {

    private static File ranks;

    private static List<Rank> rankList;

    public static void enable() {
        rankList = new ArrayList<>();
        createFiles();
        updateRankList();

        CherryDebug.debugRanks("Fully loaded and ready to access...");
    }

    public static void disable() {
        saveFiles();
    }

    public static Rank parseFromString(String rank) {

        updateRankList();

        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Rank fromConfig;

        String name;
        List<String> permissions;
        String prefix;
        String suffix;

        name = config.getString(rank.toLowerCase() + ".name");
        prefix = config.getString(rank.toLowerCase() + ".prefix");
        suffix = config.getString(rank.toLowerCase() + ".suffix");
        permissions = config.getStringList(rank.toLowerCase() + ".permissions");

        fromConfig = new Rank(name, permissions, prefix, suffix);

        return fromConfig;

    }

    public static Rank getDefaultRank() {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String defaultRankName = config.getString("default-rank");

        return parseFromString(defaultRankName);
    }

    public static void setRank(User user, Rank rank) {
        ChatChannelManager.getStaffChannel().removeToggleUser(user);
        ChatChannelManager.getStaffChannel().removeSilentToggleUser(user);
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET donater = '" + rank.getName().toLowerCase() + "' WHERE PlayerID = '" + user.getPlayerID() + "';");
        CherryDebug.debugRanks("Rank updated to " + rank.getName() +  " for " + user.getUsername() + "...");
    }

    public static void setRank(String userID, Rank rank) {
        if (User.isOnline(User.retrieveNameByUUID(userID))) {
            User user = new User(CherryProxy.getCherryProxy().getProxy().getPlayer(User.retrieveNameByUUID(userID)));
            ChatChannelManager.getStaffChannel().removeToggleUser(user);
            ChatChannelManager.getStaffChannel().removeSilentToggleUser(user);
        }
        CherryDB.update("UPDATE " + CherryDB.playerdata + " SET donater = '" + rank.getName().toLowerCase() + "' WHERE PlayerID = '" + userID + "';");
        CherryDebug.debugRanks("Rank updated to " + rank.getName() +  " for " + User.retrieveNameByUUID(userID) + "...");
    }

    public static void createRank(String rank) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cLabel = rank.toLowerCase();

        config.set(rank.toLowerCase() + ".name", rank);
        config.set(cLabel + ".prefix", "");
        config.set(cLabel + ".suffix", "");
        config.set(cLabel + ".permissions", new ArrayList<>());

        saveFiles(config);
    }

    public static void removeRank(Rank rank) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CherryDebug.debugRanks(rank.getName() + " was deleted...");

        config.set(rank.getName().toLowerCase(), null);
        saveFiles(config);
    }

    public static void setPrefix(Rank rank, String newPrefix) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CherryDebug.debugRanks(rank.getName() + " has a new prefix was set.");

        config.set(rank.getName().toLowerCase() + ".prefix", newPrefix);
        saveFiles(config);
    }

    public static void setSuffix(Rank rank, String newSuffix) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        CherryDebug.debugRanks(rank.getName() + " has a new suffix was set.");

        config.set(rank.getName().toLowerCase() + ".suffix", newSuffix);
        saveFiles(config);
    }

    public static void addPermission(Rank rank, String permission) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> permissions = config.getStringList(rank.getName().toLowerCase() + ".permissions");

        permissions.add(permission);

        config.set(rank.getName().toLowerCase() + ".permissions", permissions);
        saveFiles(config);
    }

    public static void removePermission(Rank rank, String permission) {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> permissions = config.getStringList(rank.getName().toLowerCase() + ".permissions");

        permissions.remove(permission);

        config.set(rank.getName().toLowerCase() + ".permissions", permissions);
        saveFiles(config);
    }

    public static List<String> getRankStringList() {
        updateRankList();
        List<String> ranks = new ArrayList<>();
        for (Rank rank : rankList) {
            ranks.add(rank.getName().toLowerCase());
        }
        return ranks;
    }

    private static void updateRankList() {
        File ranksYML = new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml");

        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rankList.clear();

        Collection<String> keysExceptDefault = config.getKeys();
        keysExceptDefault.remove("default-rank");

        for (String ranks : keysExceptDefault) {
            Rank fromConfig;

            String name;
            List<String> permissions;
            String prefix;
            String suffix;

            name = config.getString(ranks + ".name");
            permissions = config.getStringList(ranks + ".permissions");
            prefix = config.getString(ranks + ".prefix");
            suffix = config.getString(ranks + ".suffix");

            fromConfig = new Rank(name, permissions, prefix, suffix);

            rankList.add(fromConfig);
        }

        CherryDebug.debugRanks("Rank list has been updated...");

    }

    private static void createFiles() {
        File ranksYML = new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml");

        if (!ranksYML.exists()) {
            try (InputStream in = CherryProxy.getCherryProxy().getResourceAsStream("ranks.yml")) {
                Files.copy(in, ranksYML.toPath());
                CherryDebug.debugRanks("Ranks file has been created.");
                ranks = ranksYML;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        ranks = ranksYML;
    }

    private static void saveFiles(Configuration config) {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));

            CherryDebug.debugRanks("Ranks file has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFiles() {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(CherryProxy.getCherryProxy().getDataFolder(), "ranks.yml"));

            CherryDebug.debugRanks("Ranks file has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
