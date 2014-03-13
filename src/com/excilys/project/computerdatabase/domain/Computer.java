package com.excilys.project.computerdatabase.domain;

import java.util.Date;

public class Computer {
	private final long id;
	private final String name;
	private final Date introduced;
	private final Date discontinued;
	private final Company company;


	/*public Computer(long id, String name, Date introduced, Date discontinued,
			long companyId) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		
		company = new Company.CompanyBuilder(companyId,"").build();
	}*/
	
	/*public Computer(long id, String name, Date introduced, Date discontinued,
			long companyId, String companyName) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		
		company = new Company.CompanyBuilder(companyId,companyName).build();
	}*/
	
	/*public Computer(long id, String name, Date introduced, Date discontinued,
			Company company) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}*/


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
	
	public Date getIntroduced() {
		return introduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}
	
	public Company getCompany() {
		return company;
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
		private Date introduced;
		private Date discontinued;
		private Company company = null;
        
        public ComputerBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public ComputerBuilder introduced(Date introduced) {
            this.introduced = introduced;
            return this;
        }
 
        public ComputerBuilder discontinued(Date discontinued) {
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
