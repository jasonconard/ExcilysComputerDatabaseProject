package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.persistence.LogsDAO;

@Service
public class LogsServices {
	
	@Autowired
	LogsDAO logsDAO;
	public void setLogsDAO(LogsDAO logsDAO){
		this.logsDAO = logsDAO;
	}

	public List<Logs> getAllLogs(){
		return logsDAO.retrieveAll();
	}

	public Logs getLog(long idLog){
		return logsDAO.retrieveByLogId(idLog);
	}

	public void insert(String description, String type){
		logsDAO.insert(description, type);
	}
}
