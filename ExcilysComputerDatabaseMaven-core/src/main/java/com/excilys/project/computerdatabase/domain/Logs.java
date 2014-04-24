package com.excilys.project.computerdatabase.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity
@Table(name="logs")
public class Logs {
	@Id @GeneratedValue
	private long id;
	
	@Column(name="description")
	private String description;
	
	@Column(name="type")
	private String type;
	
	@Column(name="date_logs")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate dateLogs;

	public Logs(LogsBuilder logsBuilder) {
		this.id = logsBuilder.id;
        this.description = logsBuilder.description;
        this.type = logsBuilder.type;
        this.dateLogs = logsBuilder.dateLogs;
	}
	
	public long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public String getType(){
		return type;
	}
	
	public LocalDate getDateLogs() {
		return dateLogs;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDateLogs(LocalDate dateLogs) {
		this.dateLogs = dateLogs;
	}



	public static class LogsBuilder {
        private final long id;
        private final String description;
        private final String type;
        private final LocalDate dateLogs;
        
        public LogsBuilder(long id, String description, String type, LocalDate dateLogs) {
            this.id = id;
            this.description = description;
            this.type = type;
            this.dateLogs = dateLogs;
        }
 
        public Logs build() {
            return new Logs(this);
        }
 
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
		Logs other = (Logs) obj;
		if (dateLogs == null) {
			if (other.dateLogs != null)
				return false;
		} else if (!dateLogs.equals(other.dateLogs))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Logs [id=" + id + ", description=" + description + ", type="
				+ type + ", dateLogs=" + dateLogs + "]";
	}
	
	
	
}
