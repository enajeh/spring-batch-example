package com.exemple.batch.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.exemple.batch.entities.ActeurEntity;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {
	private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOGGER.info("============ JOB FINISHED ============ Verifying the results....\n");

			List<ActeurEntity> results = jdbcTemplate.query("SELECT id, nom, prenom, age, film FROM acteur", new RowMapper<ActeurEntity>() {
				@Override
				public ActeurEntity mapRow(ResultSet rs, int row) throws SQLException {
					return new ActeurEntity(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5));
				}
			});

			for (ActeurEntity entity : results) {
				LOGGER.info("Discovered <" + entity + "> in the database.");
			}

		}
	}
	
}
