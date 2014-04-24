package com.excilys.project.computerdatabase.services;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.project.computerdatabase.domain.Logs;
import com.excilys.project.computerdatabase.repository.LogsRepository;

@Service
@Transactional
public class LogsServices {
	
	@Autowired
	LogsRepository logsRepository;

	@Transactional
	public List<Logs> getAllLogs(){
		return logsRepository.findAll();
	}

	@Transactional
	public Logs getLog(long idLog){
		return logsRepository.findOne(idLog);
	}
	
	@SuppressWarnings("static-access")
	@Transactional
	public void addLog(String description, String type){
		Logs log = new Logs(description,type,new LocalDate().now()); 
		logsRepository.save(log);
	}
}
