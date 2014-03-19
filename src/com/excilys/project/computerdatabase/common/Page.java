package com.excilys.project.computerdatabase.common;

import java.util.List;

public class Page<E> {
	public static final int NBLINEPERPAGES = 10;
		
	private int numero;
	private int number;
	private String column;
	private String direction;
	private String filter;
	private List<E> list;
	
	public Page() {}
	
	public int getNumero(){
		return numero;
	}

	public int getNumber() {
		return number;
	}

	public String getColumn() {
		return column;
	}

	public String getDirection() {
		return direction;
	}

	public String getFilter() {
		return filter;
	}

	public List<E> getList() {
		return list;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public void setList(List<E> list) {
		this.list = list;
	}

	
}
