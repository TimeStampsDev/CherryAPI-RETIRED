package net.cherryflavor.api.cherrybungee.ranks;


import java.util.List;

public class Rank {

    String name;

    List<String> permissions;

    String prefix;
    String suffix;

    public Rank(String name, List<String> permissions, String prefix, String suffix) {
        this.name = name;
        this.permissions = permissions;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public boolean hasPermission(String permission) {
        return permissions.contains(permission);
    }

    public String getName() { return this.name; }

    public String getPrefix() { return this.prefix; }
    public String getSuffix() { return this.suffix; }

    public List<String> getPermissions() { return this.permissions; }

}
