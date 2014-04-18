package com.excilys.project.computerdatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name="computer")
public class Computer {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name="introduced")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate introduced;
	
	@Column(name="discontinued")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate discontinued;
	
	@JoinColumn(name="company_id")
	@ManyToOne(targetEntity=Company.class)
	private Company company;

	public Computer(){}
	
	public Computer(ComputerBuilder computerBuilder) {
		this.id = computerBuilder.id;
        this.name = computerBuilder.name;
        this.introduced = computerBuilder.introduced;
        this.discontinued = computerBuilder.discontinued;
        this.company = computerBuilder.company;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString(){
		return "(id: "+id+", name: "+name+", "
				+ "introduced: "+introduced+", discontinued: "+discontinued+")";
	}

	@Override
	public int hashCode() {
		return (int) id%4;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public static class ComputerBuilder {
		private final long id;
		private final String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company = null;
        
        public ComputerBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public ComputerBuilder introduced(LocalDate introduced) {
            this.introduced = introduced;
            return this;
        }
 
        public ComputerBuilder discontinued(LocalDate discontinued) {
            this.discontinued = discontinued;
            return this;
        }
 
        public ComputerBuilder company(Company company) {
            this.company = company;
            return this;
        }
 
        public Computer build() {
            return new Computer(this);
        }
 
    }
}
