package com.jury.core.settings;

import com.jury.core.exception.SettingException;
import com.jury.core.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SettingsFileManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final File file;

	public SettingsFileManager(File userSettingsFile) {
		this.file = userSettingsFile;
	}

	public String getSettingOrDefault(Setting setting, String defaultValue) {
		try {
			return getSetting(setting);
		} catch (SettingException e) {
			logger.info("Setting file targetted: " + file.getAbsolutePath());
			logger.error("Failed to get setting " + setting.getKey() + " using default value " + defaultValue, e);
			return defaultValue;
		}
	}
	
	public String getSetting(Setting setting) throws SettingException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] pair = line.split("=");
				if (pair[0].equals(setting.getKey())) {
					return pair[1];
				}
			}
			throw new SettingException(setting);
		} catch (IOException e) {
			throw new SettingException(setting, e);
		}
	}
	
	public void changeSetting(Setting setting, String newValue) throws IOException {
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
			String line, amendedFileText = "";
			boolean found = false;
			while ((line = bufferedReader.readLine()) != null) {
				String[] pair = line.split("=");
				if (setting.getKey().equals(pair[0])) {
					amendedFileText += String.format("%s=%s\n", setting.getKey(), newValue);
					found = true;
				} else {
					amendedFileText += String.format("%s\n", line);
				}
			}
			if (!found) {
				amendedFileText += String.format("%s=%s\n", setting.getKey(), newValue);
			}
			FileHandler.write(amendedFileText, file);
		}
	}
	
}
