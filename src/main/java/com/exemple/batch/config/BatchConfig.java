package com.exemple.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.exemple.batch.dto.Acteur;
import com.exemple.batch.entities.ActeurEntity;
import com.exemple.batch.listener.CustomItemReaderListener;
import com.exemple.batch.listener.CustomItemWriterListener;
import com.exemple.batch.listener.JobCompletionListener;
import com.exemple.batch.processor.ActeurProcessor;

@EnableAutoConfiguration
@EnableBatchProcessing
@Configuration
public class BatchConfig extends DefaultBatchConfigurer  {
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Value("${batch.path-file}")
    private String pathFile;
    
    @Bean
    public FlatFileItemReader<Acteur> reader(){
        FlatFileItemReader<Acteur> reader = new FlatFileItemReader<Acteur>();
        reader.setResource(new ClassPathResource(pathFile));
        reader.setLinesToSkip(2);
        reader.setLineMapper(new DefaultLineMapper<Acteur>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "id", "age", "nomComplet", "film" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Acteur>() {{
                setTargetType(Acteur.class);
            }});
        }});
        return reader;
    }


	@Bean
	ItemProcessor<Acteur, ActeurEntity> processor() {
		return new ActeurProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<ActeurEntity> writer() {
		 JdbcBatchItemWriter<ActeurEntity> writer = new JdbcBatchItemWriter<ActeurEntity>();
		 writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<ActeurEntity>());
		 writer.setSql("INSERT INTO ACTEUR (id, nom, prenom, age, film) VALUES (:id, :nom, :prenom, :age, :film)");
		 writer.setDataSource(dataSource);
		 return writer;
	}

	@Bean
	public Step stepOne() {
		return stepBuilderFactory.get("stepOne")
				.<Acteur, ActeurEntity>chunk(1)
				.listener(new CustomItemReaderListener())
				.reader(reader())
				.processor(processor())
				.listener(new CustomItemWriterListener())
				.writer(writer())
				.build();
	}

	@Bean
	Job theJob(JobCompletionListener listener) {
		return jobBuilderFactory.get("theJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(stepOne())
				.end()
				.build();
	}
	
	
	@Override
    public void setDataSource(DataSource dataSource) {
        //https://stackoverflow.com/questions/33249942/spring-batch-framework-auto-create-batch-table
    }
}
