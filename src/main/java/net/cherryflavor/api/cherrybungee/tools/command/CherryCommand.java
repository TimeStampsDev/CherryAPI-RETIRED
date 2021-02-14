package net.cherryflavor.api.cherrybungee.tools.command;

import net.cherryapi.bungee.database.User;
import net.cherryapi.bungee.tools.Chat;
import net.cherrybungee.CherryProxy;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class CherryCommand extends Command implements TabExecutor {

    private String command;
    private String permission;
    private List<String> aliases;

    private List<TabCommand> tabCommandList;

    public CherryCommand(String command, String... aliases) {
        super(command, null, aliases);
        tabCommandList = new ArrayList<>();
        this.permission = "cherrybungee." + command;
        this.aliases = Chat.convertArrayToList(aliases);
    }

    public void addTabCommand(TabCommand tabCommand) throws TabCommand.TabArgumentInvalidException {
        for (TabCommand command : tabCommandList) {
            if (tabCommand.getArgument() == command.getArgument()) {
                throw new TabCommand.TabArgumentInvalidException("Cannot have more than 1 list for argument!");
            }
        }
        tabCommandList.add(tabCommand);
    }

    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            User user = new User(player);

            if (user.getRank().hasPermission(this.permission) == false) {
                player.sendMessage(Chat.colorize("&cYou don't have permission to use this command!"));
                return;
            } else if ((user.getRank().hasPermission(this.permission) == true) || (user.getRank().hasPermission("cherrybungee.*") == true)) {
                executeCommand(new User(player), args);
            }

        } else {
            sender.sendMessage(new TextComponent(Chat.colorize("&cCherryBungee commands can only be performed by players!")));
        }
    }

    public abstract void executeCommand(User user, String[] args);

    public boolean isOnline(String name) {
        if (CherryProxy.getCherryProxy().getProxy().getPlayer(name) == null) {
            return false;
        } else {
            return true;
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            for (TabCommand tabCommand : tabCommandList) {
                if (args.length == tabCommand.getArgument()) {
                    return tabCommand.getTabList();
                }
            }
        }
        return Collections.emptyList();
    }


    public String getSentence(String[] args, int num){
        StringBuilder sb = new StringBuilder();
        for(int i = num; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        return sb.toString().trim();
    }

}
