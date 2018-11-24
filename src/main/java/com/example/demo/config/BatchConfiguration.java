package com.example.demo.config;

import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.example.demo.beans.MovementDTO;
import com.example.demo.listener.JobCompletionNotificationListener;
import com.example.demo.processor.MovementItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
  @Autowired public JobBuilderFactory jobBuilderFactory;

  @Autowired public StepBuilderFactory stepBuilderFactory;

  @Autowired public DataSource dataSource;

  @Bean
  public DataSource dataSource() {
    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/springbatch");
    dataSource.setUsername("root");
    // dataSource.setPassword("root");
    return dataSource;
  }

  @Bean
  public FlatFileItemReader<MovementDTO> reader() {
    FlatFileItemReader<MovementDTO> reader = new FlatFileItemReader<MovementDTO>();
    reader.setResource(new ClassPathResource("movements.csv"));
    reader.setLineMapper(
        new DefaultLineMapper<MovementDTO>() {
          {
            setLineTokenizer(
                new DelimitedLineTokenizer() {
                  {
                    setNames(
                        new String[] {
                          "valueDate",
                          "category",
                          "subcategory",
                          "description",
                          "comment",
                          "image",
                          "amount",
                          "balance"
                        });
                    setDelimiter(";");
                  }
                });
            setFieldSetMapper(
                new BeanWrapperFieldSetMapper<MovementDTO>() {
                  {
                    setTargetType(MovementDTO.class);
                  }
                });
          }
        });

    return reader;
  }

  @Bean
  public MovementItemProcessor processor() {
    return new MovementItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<MovementDTO> writer() {
    JdbcBatchItemWriter<MovementDTO> writer = new JdbcBatchItemWriter<MovementDTO>();
    writer.setItemSqlParameterSourceProvider(
        new BeanPropertyItemSqlParameterSourceProvider<MovementDTO>());
    writer.setSql(
        "INSERT INTO movement(valueDate, category, subcategory, description, comment, image, amount, balance) VALUES (:valueDate, :category, :subcategory, :description, :comment, :image, :amount, :balance)");
    writer.setDataSource(dataSource);
    return writer;
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory
        .get("step1")
        .<MovementDTO, MovementDTO>chunk(1)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public Job importMovementJob(JobCompletionNotificationListener listener) {
    return jobBuilderFactory
        .get("importMovementJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1())
        .end()
        .build();
  }
}
