package net.cherryflavor.api.cherrybungee.database.punishments.history;

import net.cherryflavor.api.cherrybungee.database.punishments.Punishtypes;

import java.text.SimpleDateFormat;

public class HistoryEntry {

    private String playerId;
    private Punishtypes type;
    private long expiration;
    private long datePerformed;
    private String performedById;
    private String reason;
    private long amountedTime;

    public HistoryEntry(String playerId, Punishtypes type, long expiration, long datePerformed, String performedById, String reason, long amountedTime) {
        this.playerId = playerId;
        this.type = type;
        this.expiration = expiration;
        this.datePerformed = datePerformed;
        this.performedById = performedById;
        this.reason = reason;
        this.amountedTime = amountedTime;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Punishtypes getType() {
        return type;
    }

    public long getExpiration() {
        return expiration;
    }

    public long getDatePerformed() {
        return datePerformed;
    }

    public String getPerformedById() {
        return performedById;
    }

    public String getReason() {
        return reason;
    }

    public long getAmountedTime() {
        return amountedTime;
    }

    public String formattedDatePerformed() {
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/YY");
        return sdf.format(datePerformed);
    }
}
