package com.excilys.project.computerdatabase.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SuppressWarnings("serial")
public class ComputerPage extends PageRequest{
	
	OrderBy orderBy;
	
	public ComputerPage(int numero, int number){
		super(numero, number);
	}
	
	public ComputerPage(int numero, int number, OrderBy orderBy){
		super(numero, number);
		this.setOrderBy(orderBy);
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public Sort getSort() {
		Sort sort = null;
		if(orderBy != null){
			sort = orderBy.getValue();
		}
		return sort;
	}
	
	
	
}
