package net.cherryflavor.api.cherrybungee.database;

import net.cherryapi.bungee.tools.CherryDebug;
import net.cherrybungee.CherryProxy;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class CherryDB {

    public static String playerdata = "playerdata";

    private static String host, port, database, username, password;
    private static Connection connection;

    private static Statement statement;

    private static File credentials;

    public static void enable() {
        createFiles();
        establishCredentials();
        openConnection();

        CherryDebug.debugDB("Fully loaded and ready to access...");
    }

    public static void disable() {
        try {
            if (connection != null && !connection.isClosed()) {
                CherryDebug.debugDB("Database connection has already closed...");
            } else {
                connection.close();
                CherryDebug.debugDB("Database connection has been closed...");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        CherryDebug.debugDB("Fully unloaded...");
    }

    public static void createFiles() {
        File dbCreds = new File(CherryProxy.getCherryProxy().getDataFolder(), "dbcredentials.yml");

        if (!dbCreds.exists()) {
            try (InputStream in = CherryProxy.getCherryProxy().getResourceAsStream("dbcredentials.yml")) {
                Files.copy(in, dbCreds.toPath());
                CherryDebug.debugDB("Credentials file has been created.");
                credentials = dbCreds;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        credentials = dbCreds;

    }

    public static void establishCredentials() {
        Configuration yml = null;
        try {
            yml = ConfigurationProvider.getProvider(YamlConfiguration.class).load(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
        host = yml.getString("hostname");
        port = yml.getString("port");
        database = yml.getString("database");
        username = yml.getString("username");
        password = yml.getString("password");
        CherryDebug.debugDB("Credentials have successfully loaded...");
    }

    public static void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            Properties info = new Properties();
            info.put("user", username);
            info.put("password", password);

            connection = DriverManager.getConnection(url, info);

            connection.setAutoCommit(false);

            statement = connection.createStatement();
            if(connection != null) {
                CherryDebug.debugDB("Database connection has been established...");
            } else {
                CherryDebug.debugDB("Database connection could not be established...");
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            CherryDebug.debugDB("Database connection could not be established...");
        }


    }

    public static void closeConnection() {
        try {
            connection.close();
            CherryDebug.debugDB("Database connection closed...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResultSet(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void insertInto(String query) {
        try {
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void update(String query) {
        try {
            PreparedStatement update = connection.prepareStatement(query);
            update.executeUpdate();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void insertInto(String database, List<String> into, List<String> values) {
        try {
            statement.executeQuery("INSERT INTO " + database + " (" + into + ") VALUES (" + values + ");");
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
