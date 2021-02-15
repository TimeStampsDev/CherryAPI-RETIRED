package net.cherryflavor.api.cherrybungee.tools.command;

import net.cherryflavor.api.cherrybungee.tools.Chat;

import java.util.List;

public class TabCommand {

    private int argument;
    private List<String> tabList;

    public TabCommand(int argument, List<String> tabList) {
        this.argument = argument;
        this.tabList = tabList;
    }

    public TabCommand(int argument, String... list) {
        this.argument = argument;
        this.tabList = Chat.convertArrayToList(list);
    }

    public void setArgument(int argument) {
        this.argument = argument;
    }

    public void setTabList(List<String> tabList) {
        this.tabList = tabList;
    }

    public int getArgument() {
        return argument;
    }

    public List<String> getTabList() {
        return tabList;
    }

    public static class TabArgumentInvalidException extends Exception {

        public TabArgumentInvalidException(String message) {
            super(message);
        }

    }
}
