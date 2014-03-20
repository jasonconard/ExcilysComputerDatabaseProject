package com.excilys.project.computerdatabase.dto;

import com.excilys.project.computerdatabase.domain.Company;

public class ComputerDTO {
	private final long id;
	private final String name;
	private final String introduced;
	private final String discontinued;
	private final Company company;

	public ComputerDTO(ComputerDTOBuilder computerBuilder) {
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
	
	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
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
		ComputerDTO other = (ComputerDTO) obj;
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
	
	public static class ComputerDTOBuilder {
		private final long id;
		private final String name;
		private String introduced;
		private String discontinued;
		private Company company = null;
        
        public ComputerDTOBuilder(long id, String name) {
            this.id = id;
            this.name = name;
        }
        
        public ComputerDTOBuilder introduced(String introduced) {
            this.introduced = introduced;
            return this;
        }
 
        public ComputerDTOBuilder discontinued(String discontinued) {
            this.discontinued = discontinued;
            return this;
        }
 
        public ComputerDTOBuilder company(Company company) {
            this.company = company;
            return this;
        }
 
        public ComputerDTO build() {
            return new ComputerDTO(this);
        }
 
    }
}
