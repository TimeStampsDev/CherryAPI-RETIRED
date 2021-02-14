package net.cherryflavor.api.cherrybungee.chatchannels;

import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.tools.Chat;
import net.cherryflavor.api.cherrybungee.CherryProxy;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.List;

public class ChatChannel {

    private String name;
    private String permission;
    private String prefix;

    private String isIsolated;

    public ChatChannel(String name, String prefix, String isIsolated) {
        this.name = name;
        this.permission = "cherrybungee.chat." + name.toLowerCase();
        this.prefix = prefix;
        this.isIsolated = isIsolated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    public String isIsolated() {
        return isIsolated;
    }

    public void setIsIsolated(String isIsolated) {
        this.isIsolated = isIsolated;
    }

    public List<User> getPermissibleUsers() {
        List<User> permissibleUsers = new ArrayList<>();
        for (User user : CherryProxy.getCherryProxy().getOnlineUsers()) {
            if (user.getRank().hasPermission(permission)) {
                permissibleUsers.add(user);
            }
        }
        return permissibleUsers;
    }

    public List<User> getPermissibleUsers(User except) {
        List<User> permissibleUsers = new ArrayList<>();
        for (User user : CherryProxy.getCherryProxy().getOnlineUsers()) {
            if (user.getRank().hasPermission(permission)) {
                if (user.getUsername() != except.getUsername()) {
                    permissibleUsers.add(user);
                }
            }
        }
        permissibleUsers.remove(except);
        return permissibleUsers;
    }


    public List<User> getPermissibleUsersOnServer(ServerInfo info) {
        List<User> permissibleUsers = new ArrayList<>();
        for (User user : ChatChannelManager.getUsersOnServer(info)) {
            if (user.getRank().hasPermission(permission)) {
                permissibleUsers.add(user);
            }
        }
        return permissibleUsers;
    }

    public void message(String message) {
        List<User> users = getPermissibleUsers();
        for (User user : users) {
            user.sendMessage(getPrefix() + "&r" + message);
        }
    }

    public void message(String message, User except) {
        List<User> users = getPermissibleUsers(except);
        users.remove(except);
        for (User user : users) {
            if (user != except) {
                user.sendMessage(getPrefix() + "&r" + message);
            }
        }
    }

    public void message(TextComponent message) {
        List<User> users = getPermissibleUsers();
        for (User user : users) {
            user.sendMessage(getPrefix() + "&r" + message);
        }
    }

    public void message(TextComponent message, User except) {
        List<User> users = getPermissibleUsers(except);
        users.remove(except);
        for (User user : users) {
            if (user != except) {
                user.sendMessage(getPrefix() + "&r" + message);
            }
        }
    }

    public void message(BaseComponent[] message) {
        List<User> users = getPermissibleUsers();
        for (User user : users) {
            BaseComponent[] build = new ComponentBuilder().append(Chat.colorize(getPrefix() + "&r")).append(message).create();
            user.sendMessage(build);
        }
    }

    public void message(BaseComponent[] message, User except) {
        List<User> users = getPermissibleUsers(except);
        users.remove(except);
        for (User user : users) {
            if (user != except) {
                BaseComponent[] build = new ComponentBuilder().append(Chat.colorize(getPrefix() + "&r")).append(message).create();
                user.sendMessage(build);
            }
        }
    }

    public void sendIsolatedMessage(ServerInfo info, String message, User except) {
        List<User> users = getPermissibleUsersOnServer(info);
        users.remove(except);
        for (User user : users) {
            if (user != except) {
                user.sendMessage(getPrefix() + "&r" + message);
            }
        }
    }

    public void sendIsolatedMessage(ServerInfo info, String message) {
        List<User> users = getPermissibleUsersOnServer(info);
        for (User user : users) {
            user.sendMessage(getPrefix() + "&r" + message);
        }
    }

    public void sendIsolatedMessageWithoutColor(ServerInfo info, String message) {
        List<User> users = getPermissibleUsersOnServer(info);
        for (User user : users) {
            user.sendMessageWithoutColor(Chat.colorize(getPrefix()) + message);
        }
    }

}
