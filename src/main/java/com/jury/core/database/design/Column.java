package com.jury.core.database.design;

public interface Column {
	
	String getTitle();
	<T> Class<T> getType();
	
}
