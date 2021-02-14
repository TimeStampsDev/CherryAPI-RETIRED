package net.cherryflavor.api.cherrybungee.database.punishments;

import net.cherryflavor.api.cherrybungee.database.CherryDB;
import net.cherryflavor.api.cherrybungee.database.User;
import net.cherryflavor.api.cherrybungee.tools.CherryDebug;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PunishmentsDB {

    public static String punishments = "punishments";
    public static String playerpunishdata = "playerpunishdata";

    public static void registerUser(User user) {
        String id = user.getPlayerID();

        CherryDB.insertInto("INSERT INTO " + playerpunishdata + " (PlayerID, " +

                "muted, mutedreason, mutedby, muteddate, " +
                "tempmuted, tempmutedreason, tempmutedby, tempmuteddate, tempmutedexp, tempmutedamt, " +
                "banned, bannedreason, bannedby, banneddate, " +
                "tempbanned, tempbannedreason, tempbannedby, tempbanneddate, tempbannedexp, tempbannedamt) " +

                "VALUES " +
                "('" + id + "'," +
                "'false', '', '', 0, " +
                "'false', '', '', 0, 0, 0, " +
                "'false', '', '', 0, " +
                "'false', '', '', 0, 0, 0);");

        CherryDebug.debugDB("INSERT INTO " + playerpunishdata + " (PlayerID, " +
                "muted, mutedreason, mutedby, muteddate, " +
                "tempmuted, tempmutedreason, tempmutedby, tempmuteddate, tempmutedexp, tempmutedamt, " +
                "banned, bannedreason, bannedby, banneddate, " +
                "tempbanned, tempbannedreason, tempbannedby, tempbanneddate, tempbannedexp, tempbannedamt) " +

                "VALUES " +
                "('" + id + "', 'false', '', '', 0, 'false', '', '', 0, 0, 0, 'false', '', '', 0, 'false', '', '', 0, 0, 0);");
    }

    public static boolean check(User user) {
        String playerID = user.getPlayerID();
        ResultSet result = CherryDB.getResultSet("SELECT * FROM " + playerpunishdata + " WHERE PlayerID = '" + playerID + "';");
        try {
            while (result.next()) {
                return false;
            }
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    public static void registerKick(User user, User performedBy, String reason) {
        String playerID = user.getPlayerID();
        String punishType = Punishtypes.KICK.getName();
        long expiration = 0;
        long dateperformed = System.currentTimeMillis();
        String performedby = performedBy.getPlayerID();
        long amountedtime = 0;

        CherryDB.insertInto("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
        CherryDebug.debugDB("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
    }

    public static void registerMute(User user, User performedBy, String reason) {
        String playerID = user.getPlayerID();
        String punishType = Punishtypes.MUTE.getName();
        long expiration = 1;
        long dateperformed = System.currentTimeMillis();
        String performedby = performedBy.getPlayerID();
        long amountedtime = 0;

        CherryDB.insertInto("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
        CherryDebug.debugDB("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");

        CherryDB.insertInto("INSERT INTO " + playerpunishdata + " (muted, mutedreason, mutedby, muteddate) VALUES ('true', '" + reason + "', '" + performedby + "', " + dateperformed + "');");

        CherryDB.update("UPDATE " + playerpunishdata + " SET muted = 'true' WHERE PlayerID = '" + playerID + "';");
    }

    public static void registerMute(String playerID, User performedBy, String reason) {
        String punishType = Punishtypes.MUTE.getName();
        long expiration = 1;
        long dateperformed = System.currentTimeMillis();
        String performedby = performedBy.getPlayerID();
        long amountedtime = 0;

        CherryDB.insertInto("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
        CherryDebug.debugDB("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");

        CherryDB.update("UPDATE " + playerpunishdata + " SET muted = 'true', mutedreason = '" + reason + "', mutedby = '" + performedby + "', muteddate = " + dateperformed + " WHERE PlayerID = '" + playerID + "';");

        CherryDB.update("UPDATE " + playerpunishdata + " SET muted = 'true' WHERE PlayerID = '" + playerID + "';");
    }

    public static void registerUnMute(String playerID) {

        CherryDB.update("UPDATE " + punishments + " SET expiration = 0 WHERE PlayerID = '" + playerID + "' AND punishtype = 'Mute' AND expiration = 1;");

        CherryDB.update("UPDATE " + playerpunishdata + " SET muted = 'false', mutedreason = '', mutedby = '', muteddate = 0 WHERE PlayerID = '" + playerID + "';");

    }

    public static void registerBan(String playerID, User performedBy, String reason) {
        String punishType = Punishtypes.BAN.getName();
        long expiration = 1;
        long dateperformed = System.currentTimeMillis();
        String performedby = performedBy.getPlayerID();
        long amountedtime = 0;

        CherryDB.insertInto("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
        CherryDebug.debugDB("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");

        CherryDB.update("UPDATE " + playerpunishdata + " SET banned = 'true', bannedreason = '" + reason + "', bannedby = '" + performedby + "', banneddate = " + dateperformed + " WHERE PlayerID = '" + playerID + "';");

        CherryDB.update("UPDATE " + playerpunishdata + " SET banned = 'true' WHERE PlayerID = '" + playerID + "';");
    }

    public static void registerUnBan(String playerID) {

        CherryDB.update("UPDATE " + punishments + " SET expiration = 0 WHERE PlayerID = '" + playerID + "' AND punishtype = 'Ban' AND expiration = 1;");

        CherryDB.update("UPDATE " + playerpunishdata + " SET banned = 'false', bannedreason = '', bannedby = '', banneddate = 0 WHERE PlayerID = '" + playerID + "';");

    }

    public static void registerTempBan(String playerID, User performedBy, long expiration, long amountedtime, String reason) {
        String punishType = Punishtypes.TEMPBAN.getName();
        long dateperformed = System.currentTimeMillis();
        String performedby = performedBy.getPlayerID();

        CherryDB.insertInto("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
        CherryDebug.debugDB("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");

        CherryDB.update("UPDATE " + playerpunishdata + " SET tempbanned = 'true', tempbannedreason = '" + reason + "', tempbannedby = '" + performedby + "', tempbannedexp = " + expiration + ", tempbanneddate = " + dateperformed + ", tempbannedamt = " + amountedtime + " WHERE PlayerID = '" + playerID + "';");

        CherryDB.update("UPDATE " + playerpunishdata + " SET tempbanned = 'true' WHERE PlayerID = '" + playerID + "';");
    }

    public static void registerUnTempBan(String playerID) {

        CherryDB.update("UPDATE " + punishments + " SET expiration = 0 WHERE PlayerID = '" + playerID + "' AND punishtype = 'TempBan';");

        CherryDB.update("UPDATE " + playerpunishdata + " SET tempbanned = 'false', tempbannedreason = '', tempbannedby = '', tempbannedexp = 0, tempbannedamt = 0, tempbanneddate = 0 WHERE PlayerID = '" + playerID + "';");

    }

    public static void registerTempMute(String playerID, User performedBy, long expiration, long amountedtime, String reason) {
        String punishType = Punishtypes.TEMPMUTE.getName();
        long dateperformed = System.currentTimeMillis();
        String performedby = performedBy.getPlayerID();

        CherryDB.insertInto("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");
        CherryDebug.debugDB("INSERT INTO " + punishments + " (playerid, punishtype, expiration, dateperformed, performedbyid, reason, amountedtime) VALUES ('" + playerID + "','" + punishType + "'," + expiration + "," + dateperformed + ",'" + performedby + "','" + reason + "'," + amountedtime + ");");

        CherryDB.update("UPDATE " + playerpunishdata + " SET tempmuted = 'true', tempmutedreason = '" + reason + "', tempmutedby = '" + performedby + "', tempmutedexp = " + expiration + ", tempmuteddate = " + dateperformed + ", tempmutedamt = " + amountedtime + " WHERE PlayerID = '" + playerID + "';");

        CherryDB.update("UPDATE " + playerpunishdata + " SET tempmuted = 'true' WHERE PlayerID = '" + playerID + "';");
    }

    public static void registerUnTempMute(String playerID) {

        CherryDB.update("UPDATE " + punishments + " SET expiration = 0 WHERE PlayerID = '" + playerID + "' AND punishtype = 'TempMute';");

        CherryDB.update("UPDATE " + playerpunishdata + " SET tempmuted = 'false', tempmutedreason = '', tempmutedby = '', tempmutedexp = 0, tempmutedamt = 0, tempmuteddate = 0 WHERE PlayerID = '" + playerID + "';");

    }

}
