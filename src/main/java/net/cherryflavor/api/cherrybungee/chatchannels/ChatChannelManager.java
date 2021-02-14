package net.cherryflavor.api.cherrybungee.chatchannels;

import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.tools.CherryDebug;
import net.cherryflavor.api.cherrybungee.CherryProxy;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatChannelManager {

    private static ChatChannel punishChannel;
    private static ChatChannel rankMNGChannel;
    private static StaffChannel staffChannel;
    private static List<ChatChannel> serverDefaultChannels;
    private static List<ChatChannel> partyChatChannels;

    public static void enable() {
        serverDefaultChannels = new ArrayList<>();
        partyChatChannels = new ArrayList<>();

        createPartyChannelFolder();
        createConfigFile();

        registerRegularChannels();
    }

    public static void registerRegularChannels() {
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "chatchannelconfig.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String punishChannelName = config.getString("punishment-chat-channel.name");
        String punishChannelPrefix = config.getString("punishment-chat-channel.prefix");

        String rankMNGChannelName = config.getString("rankmng-chat-channel.name");
        String rankMNGChannelPrefix = config.getString("rankmng-chat-channel.prefix");

        punishChannel = new ChatChannel(punishChannelName, punishChannelPrefix, "false");
        punishChannel.setPermission("cherrybungee.chat.punish");

        rankMNGChannel = new ChatChannel(rankMNGChannelName, rankMNGChannelPrefix, "false");
        rankMNGChannel.setPermission("cherrybungee.chat.rankmng");

        staffChannel = new StaffChannel();

        CherryDebug.debugChatChannels("Punish Chat Channel has been registered.");
        CherryDebug.debugChatChannels("Ranks Chat Channel has been registered.");
        CherryDebug.debugChatChannels("Staff Chat Channel has been registered.");

        Map<String, ServerInfo> serversMap = CherryProxy.getCherryProxy().getProxy().getServers();

        for (String string : serversMap.keySet()) {
            ServerInfo info = serversMap.get(string);

            String serverDefaultChannelName = info.getName();
            String serverDefaultChannelPrefix = config.getString("default-chat-channel-config.prefix");
            String isolated = config.getString("default-chat-channel-config.isolation");

            ChatChannel serverChannel = new ChatChannel(serverDefaultChannelName, serverDefaultChannelPrefix, isolated);

            serverChannel.setPermission("cherrybungee.chat.default");

            serverDefaultChannels.add(serverChannel);

            CherryDebug.debugChatChannels("Server Chat Channel " + serverChannel.getName() + " has been registered.");
        }



    }

    public static ChatChannel getPunishChannel() {
        return punishChannel;
    }

    public static ChatChannel getRankMNGChannel() { return rankMNGChannel; }

    public static StaffChannel getStaffChannel() { return staffChannel; }

    public static List<User> getUsersOnServer(ServerInfo info) {
        List<User> userOnServer = new ArrayList<>();

        for (User user : CherryProxy.getCherryProxy().getOnlineUsers()) {
            if (user.getCurrentServer() == info) {
                userOnServer.add(user);
            }
        }

        return userOnServer;
    }

    public static ChatChannel findServerChatChannel(ServerInfo info) {
        for (ChatChannel channel : serverDefaultChannels) {
            if (channel.getName().equalsIgnoreCase(info.getName())) {
                return channel;
            }
        }
        return null;
    }

    private static void createConfigFile() {

        File chatChannelConfig = new File(CherryProxy.getCherryProxy().getDataFolder() , "chatchannelconfig.yml");

        if (!chatChannelConfig.exists()) {
            try (InputStream in = CherryProxy.getCherryProxy().getResourceAsStream("chatchannelconfig.yml")) {
                Files.copy(in, chatChannelConfig.toPath());
                CherryDebug.debugChatChannels("ChatChannel config file has been created.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void createPartyChannelFolder() {
        File folder = new File(CherryProxy.getCherryProxy().getDataFolder(), "/partychannels/");
        if (!folder.exists()) {
            folder.mkdir();

            CherryDebug.debugChatChannels("Party Channel folder created.");
        }
    }

}
