package com.example.demo.listener;

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
import com.example.demo.beans.MovementDTO;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
  private static final Logger log =
      LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("============ JOB FINISHED ============ Verifying the results....\n");

      List<MovementDTO> results =
          jdbcTemplate.query(
              "SELECT valueDate, category, subcategory, description, comment, image, amount, balance FROM movement",
              new RowMapper<MovementDTO>() {
                @Override
                public MovementDTO mapRow(ResultSet rs, int row) throws SQLException {
                  return new MovementDTO(
                      rs.getString(1),
                      rs.getString(2),
                      rs.getString(3),
                      rs.getString(4),
                      rs.getString(5),
                      rs.getString(6),
                      rs.getFloat(7),
                      rs.getFloat(8));
                }
              });

      for (MovementDTO movementDTO : results) {
        log.info("Discovered <" + movementDTO + "> in the database.");
      }
    }
  }
}
