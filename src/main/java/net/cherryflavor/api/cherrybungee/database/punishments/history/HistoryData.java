package net.cherryflavor.api.cherrybungee.database.punishments.history;

import net.cherryflavor.api.cherrybungee.database.CherryDB;
import net.cherryflavor.api.cherrybungee.database.punishments.PunishmentsDB;
import net.cherryflavor.api.cherrybungee.database.punishments.Punishtypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryData {

    private List<HistoryEntry> history;

    private String playerID;

    private List<HistoryEntry> kicks;
    private List<HistoryEntry> bans;
    private List<HistoryEntry> tempbans;
    private List<HistoryEntry> mutes;
    private List<HistoryEntry> tempmutes;

    public HistoryData(String userID) {
        this.playerID = userID;
        this.kicks = new ArrayList<>();
        this.bans = new ArrayList<>();
        this.tempbans = new ArrayList<>();
        this.mutes = new ArrayList<>();
        this.tempmutes = new ArrayList<>();
    }

    public List<HistoryEntry> call() {
        Punishtypes type = null;
        long expiration = 0;
        long datePerformed = 0;
        String performedById  = null;
        String reason = null;
        long amountedtime = 0;

        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.punishments + " WHERE playerid = '" + playerID + "';");

        List<HistoryEntry> entries = new ArrayList<>();

        try {
            while (result.next()) {
                type = Punishtypes.parse(result.getString("punishtype"));
                expiration = result.getLong("expiration");
                datePerformed = result.getLong("datePerformed");
                performedById = result.getString("performedbyid");
                reason = result.getString("reason");
                amountedtime = result.getLong("amountedtime");

                HistoryEntry entry = new HistoryEntry(playerID, type, expiration, datePerformed, performedById, reason, amountedtime);
                entries.add(entry);
            }
        } catch (SQLException throwables) {

        }

        history = entries;

        for (HistoryEntry historyEntry : entries) {
            if (historyEntry.getType() == Punishtypes.KICK) {
                kicks.add(historyEntry);
            } else if(historyEntry.getType() == Punishtypes.BAN) {
                bans.add(historyEntry);
            } else if (historyEntry.getType() == Punishtypes.TEMPBAN) {
                tempbans.add(historyEntry);
            } else if(historyEntry.getType() == Punishtypes.MUTE) {
                mutes.add(historyEntry);
            } else if (historyEntry.getType() == Punishtypes.TEMPMUTE) {
                tempmutes.add(historyEntry);
            }
        }

        return entries;
    }


}
