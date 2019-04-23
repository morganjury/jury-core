package com.jury.core.io;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class FileHandler {

    public interface LineAction {
        void perform(String line);
    }

    public static void write(String content, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        }
    }

    public static void readWithAction(File file, LineAction action) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                action.perform(line);
            }
        }
    }

    public static void executeSqlFile(Connection connection, File file) throws IOException, SQLException {
        connection.setAutoCommit(false);
        Savepoint before = connection.setSavepoint();
        connection.commit();
        try {
            StringBuilder sb = new StringBuilder();
            FileHandler.readWithAction(file, sb::append);
            String[] commands = sb.toString()
                    .replace("\t", "")
                    .replace("\n", "")
                    .split(";");
            for (String command : commands) {
                connection.prepareStatement(command).execute();
            }
        } catch (Exception e) {
            connection.rollback(before);
            connection.releaseSavepoint(before);
            connection.setAutoCommit(true);
            connection.commit();
            throw e;
        }
    }

}
