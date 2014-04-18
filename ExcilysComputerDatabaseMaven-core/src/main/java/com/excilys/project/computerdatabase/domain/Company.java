package com.excilys.project.computerdatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	@Column(name="name")
	private String name;
	
	public Company(){}
	
	public Company(CompanyBuilder companyBuilder) {
		this.id = companyBuilder.id;
        this.name = companyBuilder.name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
		
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return "(id: "+id+", name: "+name+")";
	}
	
	@Override
	public int hashCode() {
		return (int) (id%4);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public static class CompanyBuilder {
        private final long id;
        private String name;
        
        public CompanyBuilder(long id) {
            this.id = id;
        }
        
        public CompanyBuilder name(String name) {
        	this.name = name;
            return this;
        }
 
        public Company build() {
            return new Company(this);
        }
 
    }
}
