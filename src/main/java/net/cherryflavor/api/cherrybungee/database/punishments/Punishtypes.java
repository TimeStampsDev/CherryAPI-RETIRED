package net.cherryflavor.api.cherrybungee.database.punishments;

public enum Punishtypes {

    KICK("Kick"),
    MUTE("Mute"),
    BAN("Ban"),
    TEMPMUTE("TempMute"),
    TEMPBAN("TempBan"),
    IPBAN("IPBan");

    private String name;

    Punishtypes(String name) {
        this.name = name;
    }

    public static Punishtypes parseType(String string) {
        return Punishtypes.valueOf(string);
    }

    public static Punishtypes parse(String parseString) {
        if (parseString.equalsIgnoreCase("kick")) {
            return Punishtypes.KICK;
        } else if (parseString.equalsIgnoreCase("mute")) {
            return Punishtypes.MUTE;
        } else if (parseString.equalsIgnoreCase("tempmute")) {
            return Punishtypes.TEMPMUTE;
        } else if (parseString.equalsIgnoreCase("ban")) {
            return Punishtypes.BAN;
        } else if (parseString.equalsIgnoreCase("tempban")) {
            return Punishtypes.TEMPBAN;
        }
        return null;
    }

    public String getName() { return this.name; }

}
