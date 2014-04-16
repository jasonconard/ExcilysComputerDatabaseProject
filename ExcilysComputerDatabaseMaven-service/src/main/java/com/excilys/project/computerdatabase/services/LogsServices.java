package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.persistence.LogsDAO;

@Service
@Transactional
public class LogsServices {
	
	public static LogsServices instance = null;
	synchronized public static LogsServices getInstance(){
		if(instance == null){
			instance = new LogsServices();
		}
		return instance;
	}
	
	@Autowired
	LogsDAO logsDAO;
	public void setLogsDAO(LogsDAO logsDAO){
		this.logsDAO = logsDAO;
	}

	@Transactional(readOnly = true)
	public List<Logs> getAllLogs(){
		List<Logs> logs = null;
		
		logs = logsDAO.retrieveAll();
		
		return logs;
	}

	@Transactional(readOnly = true)
	public Logs getLog(long idLog){
		Logs log = null;

		log = logsDAO.retrieveByLogId(idLog);

		return log;
	}

	@Transactional
	public void insert(String description, String type){
		
		logsDAO.insert(description, type);

	}

}
