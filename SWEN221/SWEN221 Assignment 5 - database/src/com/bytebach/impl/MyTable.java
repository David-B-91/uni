package com.bytebach.impl;

import java.util.ArrayList;
import java.util.List;

import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

public class MyTable implements Table{
	
	protected MyDatabase parent;
	private String name;
	protected List<Field> fields;
	private List<Field> keys;
	private MyRows rows;

	public MyTable(MyDatabase myDatabase, String name, List<Field> fields) {
		
		this.parent = myDatabase;
		this.name = name;
		this.fields = fields;
		this.rows = new MyRows(this);
		this.keys = new ArrayList<Field>();
		for (Field f : fields){
			if (f.isKey()) keys.add(f);
		}
		
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public List<Field> fields() {
		return fields;
	}

	@Override
	public List<List<Value>> rows() {
		return rows;
	}

	@Override
	public List<Value> row(Value... keys) {
		if (keys.length != this.keys.size()) {
			throw new InvalidOperation("key ammount mismatch");
		}
		//INFINITELOOP GRRRR
		/*for (List<Value> r : rows) {
			boolean found = true;
			for (int i = 0 ; i < keys.length ; i++) {
				if (!(keys[i]).equals(r.get(i)))found = false;
			}
			if (found) {
				return r;
			}
		}*/
		return null;
	}

	@Override
	public void delete(Value... keys) {
		List<Value> del = row(keys);
		rows.remove(del);
	}


}
