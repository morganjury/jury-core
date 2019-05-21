package com.jury.core.database.design;

import java.util.List;

public interface Table {
	
	String getTitle();
	List<Column> getColumns();
	
}
