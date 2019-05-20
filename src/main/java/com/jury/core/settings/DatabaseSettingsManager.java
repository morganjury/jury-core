package com.jury.core.settings;

import com.jury.core.exception.SettingException;
import com.jury.core.session.DBMS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class DatabaseSettingsManager {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSettingsManager.class);
    private final SettingsFileManager settingsFileManager;

    public DatabaseSettingsManager() throws IOException {
        this(getDatabaseSettingsFile());
    }

    public DatabaseSettingsManager(File databaseSettingsFile) {
        settingsFileManager = new SettingsFileManager(databaseSettingsFile);
    }

    public boolean setDbms(DBMS dbms) {
        return changeSetting(DatabaseSetting.DBMS, dbms);
    }

    public boolean setHost(String host) {
        return changeSetting(DatabaseSetting.HOST, host);
    }

    public boolean setPort(int port) {
        return changeSetting(DatabaseSetting.PORT, port);
    }

    public boolean setDbName(String dbName) {
        return changeSetting(DatabaseSetting.DB_NAME, dbName);
    }

    public boolean setBackupLocation(File outputFolder) {
        return changeSetting(DatabaseSetting.BACKUP_LOCATION, outputFolder);
    }

    public DBMS getDbms() {
        return DBMS.valueOf(settingsFileManager.getSettingOrDefault(DatabaseSetting.DBMS, "POSTGRES"));
    }

    public String getHost() {
        return settingsFileManager.getSettingOrDefault(DatabaseSetting.HOST, "localhost");
    }

    public int getPort() {
        String defaultPort = String.valueOf(getDbms().getDefaultPort());
        return Integer.parseInt(settingsFileManager.getSettingOrDefault(DatabaseSetting.PORT, defaultPort));
    }

    public String getDbName() throws SettingException {
        return settingsFileManager.getSetting(DatabaseSetting.DB_NAME);
    }

    public DBMS getTestDbms() {
        return DBMS.valueOf(settingsFileManager.getSettingOrDefault(DatabaseSetting.TEST_DBMS, "POSTGRES"));
    }

    public String getTestHost() {
        return settingsFileManager.getSettingOrDefault(DatabaseSetting.TEST_HOST, "localhost");
    }

    public int getTestPort() {
        String defaultPort = String.valueOf(getDbms().getDefaultPort());
        return Integer.parseInt(settingsFileManager.getSettingOrDefault(DatabaseSetting.TEST_PORT, defaultPort));
    }

    public String getTestDbName() throws SettingException {
        return settingsFileManager.getSetting(DatabaseSetting.TEST_DB_NAME);
    }

    public File getBackupLocation() {
        try {
            return new File(settingsFileManager.getSetting(DatabaseSetting.BACKUP_LOCATION));
        } catch (SettingException e) {
            return null;
        }
    }

    private <V> boolean changeSetting(DatabaseSetting setting, V value) {
        try {
            settingsFileManager.changeSetting(setting, String.valueOf(value));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static File getDatabaseSettingsFile() throws IOException {
        File dir = new File("settings");
        if (!dir.exists() && !dir.mkdir()) {
            logger.error("Failed to create application settings directory");
            throw new IOException("Failed to create settings folder");
        }
        File file = new File(dir, "database.properties");
        if (!file.exists() && !file.createNewFile()) {
            logger.error("Failed to create database.properties file");
            throw new IOException("Failed to create settings file");
        }
        return file;
    }

}
