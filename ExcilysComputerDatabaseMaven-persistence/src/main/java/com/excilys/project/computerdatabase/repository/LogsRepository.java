package com.excilys.project.computerdatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.project.computerdatabase.domain.Logs;

public interface LogsRepository  extends JpaRepository<Logs, Long> {
	
}
