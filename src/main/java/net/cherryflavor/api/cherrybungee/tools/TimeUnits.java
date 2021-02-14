package net.cherryflavor.api.cherrybungee.tools;

import java.util.ArrayList;
import java.util.List;

public enum TimeUnits {

    SECONDS("seconds", 1000, new String[] {"s","sec", "second", "seconds"}),
    MINUTES("minutes", 60000, new String[] {"m","min","minute","minutes"}),
    HOURS("hours", 3600000, new String[] {"h", "hr", "hour", "hours"}),
    DAYS("days", 43200000, new String[] {"d", "day", "days"});

    private String name;
    private long inMS;
    private String[] abbreviations;

    TimeUnits(String name, long inMS, String[] abbreviations) {
        this.name = name;
        this.inMS = inMS;
        this.abbreviations = abbreviations;
    }

    public String getName() {
        return name;
    }

    public long getInMS() {
        return inMS;
    }

    public String[] getAbbreviations() {
        return abbreviations;
    }

    public List<String> getAbbreviationsAsList() {
        List<String> asArray = new ArrayList<>();
        for (String abbreviation : abbreviations) {
            asArray.add(abbreviation);
        }
        return asArray;
    }

    //

    public static void convertStringToLong(String string) {
        int seconds;
        char[] arrayChar = string.toCharArray();


    }

}
