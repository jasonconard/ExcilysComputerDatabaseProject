package com.excilys.project.computerdatabase.common;

import org.springframework.data.domain.Sort;

public enum OrderBy {
	COMPUTER_NAME_ASC		(new Sort(Sort.Direction.ASC , "name"			), "name"			, "ASC" ),
	COMPUTER_NAME_DESC		(new Sort(Sort.Direction.DESC, "name"			), "name"			, "DESC"),
	INTRODUCED_DATE_ASC		(new Sort(Sort.Direction.ASC , "introduced"		), "introduced"		, "ASC" ),
	INTRODUCED_DATE_DESC	(new Sort(Sort.Direction.DESC, "introduced"		), "introduced"		, "DESC"),
	DISCONTINUED_DATE_ASC	(new Sort(Sort.Direction.ASC , "discontinued"	), "discontinued"	, "ASC" ),
	DISCONTINUED_DATE_DESC	(new Sort(Sort.Direction.DESC, "discontinued"	), "discontinued"	, "DESC"),
	COMPANY_NAME_ASC		(new Sort(Sort.Direction.ASC , "company.name"	), "company.name"	, "ASC" ),
	COMPANY_NAME_DESC		(new Sort(Sort.Direction.DESC, "company.name"	), "company.name"	, "DESC");

	private Sort value;
	private String column = "name";
	private String direction = "ASC";

	private OrderBy(Sort value, String column, String direction) {
		this.setValue(value);
		this.setColumn(column);
		this.setDirection(direction);
	}

	public Sort getValue() {
		return value;
	}

	private void setValue(Sort value) {
		this.value = value;
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

	private void setDirection(String direction) {
		this.direction = direction;
	}

	public static OrderBy get(String column, String direction) {
		if (column == null)
			return null;

		if (direction == null)
			direction = "ASC";

		OrderBy[] list = OrderBy.values();

		for (OrderBy col : list) {
			if (col.getColumn().equals(column) && col.getDirection().equals(direction))
				return col;
		}
		return null;
	}

	public String getDirForCol(String column) {

		if (this.getColumn().equals(column) && this.getDirection().equals("ASC"))
			return "DESC";

		return "ASC";
	}

	public static OrderBy getOrderByFromSort(Sort sort) {
		if (sort == null)
			return null;

		OrderBy[] list = OrderBy.values();

		for (OrderBy ob : list) {
			if (ob.getValue().equals(sort))
				return ob;
		}
		return null;
	}
}
