package com.excilys.project.computerdatabase.persistence;

public class WrapperComputer {
	private int page;
	private String column;
	private String direction;
	private String filter;
	
	public static final int NBLINEPERPAGES = 10;
	
	public WrapperComputer(int page, String column, String direction,
			String filter) {
		super();
		this.page = page;
		this.column = column;
		this.direction = direction;
		this.filter = filter;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	
	
	
	
}
