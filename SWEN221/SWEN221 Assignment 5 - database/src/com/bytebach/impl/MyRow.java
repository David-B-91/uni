package com.bytebach.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;

import com.bytebach.model.Field;
import com.bytebach.model.InvalidOperation;
import com.bytebach.model.ReferenceValue;
import com.bytebach.model.Table;
import com.bytebach.model.Value;

public class MyRow implements List<Value> {
	
	private MyRows parent;
	private List<Value> values;
	
	public MyRow(MyRows parent) {
		this.parent = parent;
		this.values = new ArrayList<Value>();
	}
	
	private boolean validSet(int index, Value element) {
		
		Field field = parent.parent.fields().get(index);
		Map<Field, List<Value>> keyMap = parent.keyValues;
		
		if (keyMap.containsKey(field)) {
			List<Value> tempValList = keyMap.get(field);
			if (tempValList.contains(element)) {
				throw new InvalidOperation("key already in list");
			}
		}
		
		Value tempVal = values.get(index);
		
		if (tempVal.getClass() == element.getClass()) {
			if (tempVal.getClass().getSimpleName().equals("StringValue")) {
				if (element.toString().contains("\n") && !tempVal.toString().contains("\n")){
					throw new InvalidOperation("incorrect type");
				}
			} else if (element.getClass().getSimpleName().equals("ReferenceValue")) {
				
				ReferenceValue refVal = (ReferenceValue) element;
				Table refTable = parent.parent.parent.table(refVal.table());
				
				if (refTable != null) {
					List<Value> match = refTable.row(refVal.keys());
					if (match == null) {
						throw new InvalidOperation("no key");
					}
				} else {
					throw new InvalidOperation("no table");
				}
			}
			return true;
		} else {		
			return false;
		}
	}
	
	@Override
	public boolean addAll(Collection<? extends Value> c) {
		for (Value v : c){
			values.add(v);
		}
		return true;
	}

	@Override
	public boolean contains(Object o) {
		if (o instanceof MyRow) {
			if (((MyRow) o).size() == values.size()) {
				boolean contains = true;
				for (int i = 0 ; i < values.size() ; i++){
					if (!values.get(i).equals(((MyRow) o).get(i))) {
						contains = false;
					}
					return contains;
				}
			}
		}
		return false;
	}
	
	@Override
	public Value get(int index) {
		return values.get(index);
	}
	
	@Override
	public Iterator<Value> iterator() {
		return new MyIterator<Value>();
	}
	
	@Override
	public Value set(int index, Value element) {
		Value temp = values.get(index);
		if (validSet(index,element)) {
			this.values.set(index, element);
			return temp;
		}
		throw new InvalidOperation(null);
	}
	
	@Override
	public int size() {
		return values.size();
	}
	
	private class MyIterator<T> implements Iterator<T> {
		
		private int index;

		@Override
		public boolean hasNext() {
			return this.index < values.size();
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			if (this.hasNext()) {
				int current = index;
				current++;
				return (T) values.get(current);
			}
			throw new NoSuchElementException();
		}
		
	}
	
	//Unimplemented Methods.
	
	@Override
	public boolean add(Value e) {
		throw new InvalidOperation(null);
	}

	@Override
	public void add(int index, Value element) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Value> c) {
		throw new InvalidOperation(null);
	}

	@Override
	public void clear() {
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
	public ListIterator<Value> listIterator() {
		throw new InvalidOperation(null);
	}

	@Override
	public ListIterator<Value> listIterator(int index) {
		throw new InvalidOperation(null);
	}

	@Override
	public boolean remove(Object o) {
		throw new InvalidOperation(null);
	}

	@Override
	public Value remove(int index) {
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
	public List<Value> subList(int fromIndex, int toIndex) {
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
