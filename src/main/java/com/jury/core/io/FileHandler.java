package com.jury.core.io;

import java.io.*;

public class FileHandler {

    public interface LineAction {
        void perform(String line);
    }

    public static void readWithAction(File file, LineAction action) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                action.perform(line);
            }
        }
    }

    public static void write(String content, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        }
    }

}
