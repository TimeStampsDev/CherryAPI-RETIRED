package net.cherryflavor.api.cherrybungee.tools;

import net.cherrybungee.CherryProxy;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ConfigYML {

    private static Configuration configuration;

    public static void enable() {
        createFiles();
        loadFiles();
    }

    public static Configuration getConfig() { return configuration; }

    public static Configuration getPunishMessages() { return configuration.getSection("punish-messages"); }
    public static Configuration getPunishChannelMessages() { return configuration.getSection("punish-channel-messages"); }
    public static Configuration getBasicMessages() { return configuration.getSection("basic-messages"); }
    public static Configuration getStaffChatMessages() { return getBasicMessages().getSection("staffchat"); }
    public static Configuration getDeniedMessages() { return configuration.getSection("denied-messages"); }

    public static void loadFiles() {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configuration = config;

    }

    public static void createFiles() {
        File configFile = new File(CherryProxy.getCherryProxy().getDataFolder() , "config.yml");

        if (!configFile.exists()) {
            try (InputStream in = CherryProxy.getCherryProxy().getResourceAsStream("config.yml")) {
                Files.copy(in, configFile.toPath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
