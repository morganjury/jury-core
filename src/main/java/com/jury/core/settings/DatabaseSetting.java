package com.jury.core.settings;

public enum DatabaseSetting implements Setting {

    DBMS("database.dbms"),
    HOST("database.host"),
    PORT("database.port"),
    DB_NAME("database.name"),
    DB_USER("database.user"),
    DB_PWD("database.pwd"),
    BACKUP_LOCATION("database.backup.location"),
    TEST_DBMS("test.database.dbms"),
    TEST_HOST("test.database.host"),
    TEST_PORT("test.database.port"),
    TEST_DB_NAME("test.database.name");

    private final String key;

    DatabaseSetting(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return name();
    }

}
