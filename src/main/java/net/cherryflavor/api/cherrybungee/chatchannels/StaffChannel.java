package net.cherryflavor.api.cherrybungee.chatchannels;

import net.cherryflavor.api.cherrybungee.CherryProxy;
import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.tools.ConfigYML;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaffChannel extends ChatChannel {

    public static List<String> toggledUsers;
    public static List<String> silentToggleUsers;

    public StaffChannel() {
        super("","","");
        Configuration config = null;
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CherryProxy.getCherryProxy().getDataFolder(), "chatchannelconfig.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setName(config.getString("staff-chat-channel.name"));
        setPrefix(config.getString("staff-chat-channel.prefix"));
        setPermission("cherrybungee.chat.staff");
        setIsIsolated(config.getString("staff-chat-channel.isolation"));

        toggledUsers = new ArrayList<>();
        silentToggleUsers = new ArrayList<>();
    }

    public void addToggleUser(User user) {
        toggledUsers.add(user.getUsername());
    }

    public void removeToggleUser(User user) {
        toggledUsers.remove(user.getUsername());
    }

    public List<String> getToggledUsers() {
        return toggledUsers;
    }

    public boolean isToggledUser(User user) {
        return toggledUsers.contains(user.getUsername());
    }

    public void addSilentToggleUser(User user) {
        silentToggleUsers.add(user.getUsername());
    }

    public void removeSilentToggleUser(User user) {
        silentToggleUsers.remove(user.getUsername());
    }

    public List<String> getSilentToggleUsers() {
        return silentToggleUsers;
    }

    public boolean isSilentToggleUser(User user) {
        return silentToggleUsers.contains(user.getUsername());
    }

    public void msg(String message) {
        for (User userWithPermission : CherryProxy.getCherryProxy().getOnlineUsers()) {
            if (userWithPermission.getRank().hasPermission(getPermission())) {
                if (isSilentToggleUser(userWithPermission) == false) {
                    userWithPermission.sendMessage((ConfigYML.getStaffChatMessages().getString("preview-prefix").equalsIgnoreCase("true") ? getPrefix() : "") + "&f" + message);
                }
            }
        }

    }


}
