package com.excilys.project.computerdatabase.dto;

public class ComputerDTO {
	private long id;
	private String name;
	private String introduced;
	private String discontinued;
	private long companyId;
	private String companyName;

	public ComputerDTO(){}
	
		public ComputerDTO(long id, String name, String introduced,
			String discontinued, long companyId, String companyName) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}



	public ComputerDTO(ComputerDTOBuilder computerBuilder) {
		this.id = computerBuilder.id;
        this.name = computerBuilder.name;
        this.introduced = computerBuilder.introduced;
        this.discontinued = computerBuilder.discontinued;
        this.companyId = computerBuilder.companyId;
        this.companyName = computerBuilder.companyName;
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
	
	public long getCompanyId() {
		return companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + ", companyName=" + companyName
				+ "]";
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
		if (companyId != other.companyId)
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
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
		private long companyId;
		private String companyName;
        
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
 
        public ComputerDTOBuilder companyId(long companyId) {
            this.companyId = companyId;
            return this;
        }

        public ComputerDTOBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }
        
        public ComputerDTO build() {
            return new ComputerDTO(this);
        }
 
    }
}
