package com.excilys.project.computerdatabase.persistence;

import com.excilys.project.computerdatabase.domain.QComputer;
import com.mysema.query.types.OrderSpecifier;

public class Ordered {
	private OrderSpecifier<?> mySpecifier;
	private QComputer computer = QComputer.computer;

	public Ordered(String type, String field){
		if("DESC".equals(type)){
			if("computer.id".equals(field)){
				mySpecifier = computer.id.desc();
			}
			else if("computer.name".equals(field)){
				mySpecifier = computer.name.desc();
			}
			else if("computer.introduced".equals(field)){
				mySpecifier = computer.introduced.desc();
			}
			else if("computer.discontinued".equals(field)){
				mySpecifier = computer.discontinued.desc();
			}
			else if("company.name".equals(field)){
				mySpecifier = computer.company.name.desc();
			}
			else{
				mySpecifier = computer.name.desc();
			}
		}
		else{//ASC
			if("computer.id".equals(type)){
				mySpecifier = computer.id.asc();
			}
			else if("computer.name".equals(field)){
				mySpecifier = computer.name.asc();
			}
			else if("computer.introduced".equals(field)){
				mySpecifier = computer.introduced.asc();
			}
			else if("computer.discontinued".equals(field)){
				mySpecifier = computer.discontinued.asc();
			}
			else if("company.name".equals(field)){
				mySpecifier = computer.company.name.asc();
			}
			else{
				mySpecifier = computer.name.asc();
			}
		}
	}

	public OrderSpecifier<?> getMySpecifier() {
		return mySpecifier;
	}

	public void setMySpecifier(OrderSpecifier<?> mySpecifier) {
		this.mySpecifier = mySpecifier;
	}
}