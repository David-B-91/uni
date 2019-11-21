package com.bytebach.impl;

import java.util.*;

import com.bytebach.model.*;

public class MyDatabase implements Database {
	
	private ArrayList<Table> tables;
	
	public MyDatabase(){
		
		super();
		tables = new ArrayList<Table>();
		
	}
	
	public Collection<? extends Table> tables() {
		return tables;
	}
	
	@Override
	public Table table(String name) {
		for (Table t : tables) {
			if (t.name().equals(name)) return t;
		}
		return null;
	}

	@Override
	public void createTable(String name, List<Field> fields) {
			if (table(name) != null) {
				throw new InvalidOperation("duplicate table name being added");
			} else {
				tables.add(new MyTable(this,name,fields));
			}
	}

	@Override
	public void deleteTable(String name) {
		Table del = table(name);
		if (del != null) tables.remove(del);
		
	}
	
}
