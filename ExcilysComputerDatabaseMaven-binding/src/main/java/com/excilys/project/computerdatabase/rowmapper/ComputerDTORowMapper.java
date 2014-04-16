package com.excilys.project.computerdatabase.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.project.computerdatabase.dto.ComputerDTO;

@SuppressWarnings("rawtypes")
public class ComputerDTORowMapper implements RowMapper{
	
		@Bean(name = "messageSource")
		public ResourceBundleMessageSource messageSource() {
			ResourceBundleMessageSource bean = new ResourceBundleMessageSource();
		    bean.setBasename("computerDatabase");
		    return bean;
		}
	
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			String pattern = messageSource().getMessage("view.addComputer.dateFormat", null, LocaleContextHolder.getLocale());
			
			ComputerDTO computerDTO = new ComputerDTO();
			
			computerDTO.setId(rs.getLong("id"));
			computerDTO.setName(rs.getString("name"));
			
			String introducedString = "";
			String discontinuedString = "";
			
			Date introduced = rs.getDate("introduced");
			Date discontinued = rs.getDate("discontinued");
			LocalDate introducedLD = null;
			if(introduced != null){
				introducedLD = LocalDate.fromDateFields(introduced);
				if(introducedLD != null){
					introducedString = introducedLD.toString(pattern);
				}
			}
			LocalDate discontinuedLD = null;
			if(discontinued != null){
				discontinuedLD = LocalDate.fromDateFields(discontinued);
				if(discontinuedLD != null){
					discontinuedString = discontinuedLD.toString(pattern);
				}
			}
			
			computerDTO.setIntroduced(introducedString);
			computerDTO.setDiscontinued(discontinuedString);
			
			computerDTO.setCompanyId(rs.getLong("company_id"));
			computerDTO.setCompanyName(rs.getString("company_name"));
			
			return computerDTO;
		}
}