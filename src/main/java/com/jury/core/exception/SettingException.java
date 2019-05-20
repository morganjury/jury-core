package com.jury.core.exception;

import com.jury.core.settings.Setting;

public class SettingException extends Exception {
	
	@SuppressWarnings("unused")
	private SettingException() {
		
	}
	
	public SettingException(Setting setting) {
		super(String.format("Setting %s not found", setting.getKey()));
	}

	public SettingException(Setting setting, Throwable cause) {
		super(String.format("Setting %s not found", setting.getKey()), cause);
	}
	
}
