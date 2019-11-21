package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.bytebach.model.Field;
import com.bytebach.model.Value;
import com.bytebach.model.Field.Type;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Table;

public class MyRows implements List<List<Value>> {
	
	protected MyTable parent;
	private List<MyRow> rows;
	protected Map<Field, List<Value>> keyValues = new HashMap<Field, List<Value>>();
	
	public MyRows(MyTable parent){
		this.parent = parent;
		this.rows = new ArrayList<MyRow>();
	}

	@Override
	public boolean add(List<Value> valueList) {
		MyRow value = checkValidAdd(valueList);
		if (value != null) {
			rows.add(value);
			return true;
		}
		throw new InvalidOperation(null);
	}

	private MyRow checkValidAdd(List<Value> valueList) {
		boolean validAdd = true;
		
		if (parent.fields().size() == valueList.size()){ //check list sizes are same.
			
			for (int i = 0 ; i < valueList.size() ; i++) { //check if field is key and if that key is already added
				
				Field current = this.parent.fields().get(i);
				
				if (current.isKey()) {
					
					if (keyValues.isEmpty()) {
						
						validAdd = true;
						List<Value> temp;
						
						if(keyValues.get(current) == null) {
							temp = new ArrayList<Value>();
						} else {
							temp = keyValues.get(current);
						}
						
						temp.add(valueList.get(i));
						keyValues.put(current,temp);
						
					} else if (keyValues.get(current) == null || !keyValues.get(current).contains(valueList.get(i))) {
						
						validAdd = true;
						List<Value> temp;
						
						if (keyValues.get(current) == null) {
							temp = new ArrayList<Value>();
						} else {
							temp = keyValues.get(current);
						}
						
						temp.add(valueList.get(i));
						keyValues.put(current,temp);
						
					} else if (keyValues.get(current).contains(valueList.get(i))) {
						validAdd = false;
					}
				} else {
					
					if (validAdd) {
						MyRow tempRow = new MyRow(this);
						tempRow.addAll(valueList);
						return tempRow;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<Value> get(int index) {
		List<Value> row = rows.get(index);
		return row;
	}

	@Override
	public Iterator<List<Value>> iterator() {
		return new MyIterator<List<Value>>();
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof MyRow) {
			MyRow rowToDel = (MyRow) o;
			
			for (int index = 0 ; index < rows.size() ; index++) {
				if (rows.get(index).contains(rowToDel)) {
					Map <Table, List<List<Value>>> toDelMap = new HashMap<Table, List<List<Value>>>();
					boolean okToDel = false;
					
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<Value> remove(int index) {
		if (rows.size() >= index) {
			return rows.remove(index);
		} 
		throw new InvalidOperation(null);
	}

	@Override
	public List<Value> set(int index, List<Value> element) {
		List<Value> temp = rows.get(index);
		MyRow value = checkValidAdd(element);
		if (value != null) {
			rows.set(index, value);
			return temp;
		}
		throw new InvalidOperation(null);
	}
	
	@Override
	public int size() {
		return rows.size();
	}

	private class MyIterator <T> implements Iterator<T> {
		
		private int index;

		@Override
		public boolean hasNext() {
			return this.index < rows.size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			if (this.hasNext()) {
				int current = index;
				current++;
				return (T) rows.get(current);
			}
			throw new NoSuchElementException();
		}
		
	}
	
	//Unimplemented Methods

	@Override
	public void add(int index, List<Value> element) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean addAll(Collection<? extends List<Value>> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean addAll(int index, Collection<? extends List<Value>> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public void clear() {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean contains(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public int indexOf(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean isEmpty() {
		throw new InvalidOperation(null);
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public ListIterator<List<Value>> listIterator() {
		throw new InvalidOperation(null);
	}

	@Override
	public ListIterator<List<Value>> listIterator(int index) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public List<List<Value>> subList(int fromIndex, int toIndex) {
		throw new InvalidOperation(null);
	}

	@Override
	public Object[] toArray() {
		throw new InvalidOperation(null);
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new InvalidOperation(null);
	}

}
