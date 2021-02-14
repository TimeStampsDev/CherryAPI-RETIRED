package net.cherryflavor.api.cherrybungee;

import net.cherryflavor.api.cherrybungee.chatchannels.ChatChannelManager;
import net.cherryflavor.api.cherrybungee.database.CherryDB;
import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.ranks.RankManagement;
import net.cherryflavor.api.cherrybungee.tools.CherryDebug;
import net.cherryflavor.api.cherrybungee.tools.ConfigYML;
import net.cherryflavor.api.cherrybungee.tools.command.CherryCommand;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CherryProxy extends Plugin {

    private static CherryProxy cherryProxy;

    public void onEnable() {
        cherryProxy = this;

        loadDataFolder();
        ConfigYML.enable();

        CherryDebug.debuginfo("Plugin has begun loading...");

        CherryDB.enable();
        RankManagement.enable();
        ChatChannelManager.enable();
    }

    public void onDisable() {
        CherryDB.disable();
        RankManagement.disable();
    }

    public static CherryProxy getCherryProxy() {
        return cherryProxy;
    }

    private void loadDataFolder() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
    }

    public void registerEvent(Listener listener) {
        cherryProxy.getProxy().getPluginManager().registerListener(this, listener);
        CherryDebug.debugEvents(listener.getClass().getSimpleName() + " has been successfully registered...");
    }

    public void registerCommand(CherryCommand command) {
        cherryProxy.getProxy().getPluginManager().registerCommand(this, command);
        CherryDebug.debugCommmands(command.getClass().getSimpleName() + " has been successfully registered...");
    }

    public void log(String log) {
        getLogger().info(log);
    }

    public List<User> getOnlineUsers() {
        List<User> onlineUsers = new ArrayList<>();
        for (ProxiedPlayer all : getProxy().getPlayers()) {
            onlineUsers.add(new User(all));
        }
        return onlineUsers;
    }

    public List<String> getServersList() {
        List<String> servers = new ArrayList<>();
        for (ServerInfo server : getProxy().getServers().values()) {
            servers.add(server.getName());
        }
        return servers;
    }

    public boolean checkOnlineStatus(ServerInfo server) {
        Map<ServerInfo, Boolean> serverBooleanMap = new HashMap<>();
        server.ping(new Callback<ServerPing>() {
            @Override
            public void done(ServerPing serverPing, Throwable throwable) {
                if (throwable != null) {
                    serverBooleanMap.put(server, false);
                    return;
                }
            }
        });
        return serverBooleanMap.get(server);
    }
}
