package com.excilys.project.computerdatabase.domain;


public class Company {
	private final long id;
	private final String name;
	
	/*public Company(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}*/

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
        private  String name;
        
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
