package net.cherryflavor.api.cherrybungee.database.punishments;

import net.cherryflavor.api.cherrybungee.database.CherryDB;
import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.tools.Chat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class PunishInfo {

    private User user;
    private String ID;

    private String muted;
    private String mutedReason;
    private String mutedBy;
    private long mutedDate;

    private boolean tempmuted;
    private String tempmutedreason;
    private String tempmutedby;
    private long tempmutedexp;
    private long tempmuteddate;

    private boolean banned;
    private String bannedReason;
    private String bannedBy;
    private long bannedDate;

    private boolean tempbanned;
    private String tempbannedreason;
    private String tempbannedby;
    private long tempbannedexp;
    private long tempbanneddate;

    public PunishInfo(User user) {
        this.user = user;
        this.ID = user.getPlayerID();
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + user.getPlayerID() + "';");
        try {
            while (result.next()) {
                muted = result.getString("muted");
                mutedReason = result.getString("mutedreason");
                mutedBy = result.getString("mutedby");
                mutedDate = result.getLong("mutedDate");

                tempmuted = Chat.parseBoolean(result.getString("tempmuted"));
                tempmutedreason = result.getString("tempmutedreason");
                tempmutedby = result.getString("tempmutedby");
                tempmutedexp = result.getLong("tempmutedexp");
                tempmuteddate = result.getLong("tempmuteddate");

                banned = Chat.parseBoolean(result.getString("banned"));
                bannedReason = result.getString("bannedreason");
                bannedBy = result.getString("bannedby");
                bannedDate = result.getLong("banneddate");

                tempbanned = Chat.parseBoolean(result.getString("tempbanned"));
                tempbannedreason = result.getString("tempbannedreason");
                tempbannedby = result.getString("tempbannedby");
                tempbannedexp = result.getLong("tempbannedexp");
                tempbanneddate = result.getLong("tempbanneddate");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public PunishInfo(String username) {
        this.ID = User.retrieveUUIDbyName(username);
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + this.ID + "';");
        try {
            while (result.next()) {
                muted = result.getString("muted");
                mutedReason = result.getString("mutedreason");
                mutedBy = result.getString("mutedby");
                mutedDate = result.getLong("mutedDate");

                tempmuted = Chat.parseBoolean(result.getString("tempmuted"));
                tempmutedreason = result.getString("tempmutedreason");
                tempmutedby = result.getString("tempmutedby");
                tempmutedexp = result.getLong("tempmutedexp");
                tempmuteddate = result.getLong("tempmuteddate");

                banned = Chat.parseBoolean(result.getString("banned"));
                bannedReason = result.getString("bannedreason");
                bannedBy = result.getString("bannedby");
                bannedDate = result.getLong("banneddate");

                tempbanned = Chat.parseBoolean(result.getString("tempbanned"));
                tempbannedreason = result.getString("tempbannedreason");
                tempbannedby = result.getString("tempbannedby");
                tempbannedexp = result.getLong("tempbannedexp");
                tempbanneddate = result.getLong("tempbanneddate");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void refresh() {
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + this.ID + "';");
        try {
            while (result.next()) {
                muted = result.getString("muted");
                mutedReason = result.getString("mutedreason");
                mutedBy = result.getString("mutedby");
                mutedDate = result.getLong("mutedDate");

                tempmuted = Chat.parseBoolean(result.getString("tempmuted"));
                tempmutedreason = result.getString("tempmutedreason");
                tempmutedby = result.getString("tempmutedby");
                tempmutedexp = result.getLong("tempmutedexp");
                tempmuteddate = result.getLong("tempmuteddate");

                banned = Chat.parseBoolean(result.getString("banned"));
                bannedReason = result.getString("bannedreason");
                bannedBy = result.getString("bannedby");
                bannedDate = result.getLong("banneddate");

                tempbanned = Chat.parseBoolean(result.getString("tempbanned"));
                tempbannedreason = result.getString("tempbannedreason");
                tempbannedby = result.getString("tempbannedby");
                tempbannedexp = result.getLong("tempbannedexp");
                tempbanneddate = result.getLong("tempbanneddate");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public User getUser() {
        return user;
    }

    public String getID() {
        return ID;
    }

    public static String isMuted(String userID) {
        String ismuted = null;
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + userID + "';");
        try {
            while (result.next()) {
                ismuted = result.getString("muted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ismuted;
    }

    public String getMutedBy() {
        refresh();
        return mutedBy;
    }

    public String getMutedReason() {
        refresh();
        return mutedReason;
    }

    public long getMutedDate() {
        refresh();
        return mutedDate;
    }

    public String getTempMutedReason() {
        refresh();
        return tempmutedreason;
    }

    public String getTempMutedBy() {
        refresh();
        return tempmutedby;
    }

    public long getTempMutedExp() {
        refresh();
        return tempmutedexp;
    }

    public long getTempMutedDate() {
        refresh();
        return tempmuteddate;
    }

    public String getBannedReason() {
        refresh();
        return bannedReason;
    }

    public String getBannedBy() {
        refresh();
        return bannedBy;
    }

    public long getBannedDate() {
        refresh();
        return bannedDate;
    }

    public String getTempBannedReason() {
        refresh();
        return tempbannedreason;
    }

    public String getTempBannedBy() {
        refresh();
        return tempbannedby;
    }

    public long getTempBannedExp() {
        refresh();
        return tempbannedexp;
    }

    public long getTempBannedDate() {
        refresh();
        return tempbanneddate;
    }

    public static String isTempMuted(String userID) {
        String isTempMuted = null;
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + userID + "';");
        try {
            while (result.next()) {
                isTempMuted = result.getString("tempmuted");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isTempMuted;
    }

    public static String isBanned(String userID) {
        String isbanned = null;
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + userID + "';");
        try {
            while (result.next()) {
                isbanned = result.getString("banned");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isbanned;
    }

    public static String isTempBanned(String userID) {
        String isTempBanned = null;
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + PunishmentsDB.playerpunishdata + " WHERE PlayerID = '" + userID + "';");
        try {
            while (result.next()) {
                isTempBanned = result.getString("tempbanned");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return isTempBanned;
    }

    public String formatDatePerformed(long datePerformed) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEEE, MMMM d, YYYY 'at' h:mm a zzz");
        return sdf.format(datePerformed);
    }

}
