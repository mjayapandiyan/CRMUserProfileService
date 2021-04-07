package com.crm.app.user.profile.service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Service
@ConditionalOnProperty(prefix = "crm.app.data.init.sql.script", name = "enabled", havingValue = "true")
public class PopulateDataService {
	
	@Autowired
	private DataSource dataSource;
	private DatabasePopulator databasePopulator = null;
	
	@PostConstruct
	public void initData() {
		
		//schema init
		ClassPathResource initSchema = new ClassPathResource("sql/schema.sql");
		databasePopulator = new ResourceDatabasePopulator(true, true, "UTF-8",initSchema);
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
		//schema init
		ClassPathResource[] initData = new ClassPathResource[] {new ClassPathResource("sql/data.sql"),
				new ClassPathResource("sql/country.sql"),
				new ClassPathResource("sql/state.sql"),
				new ClassPathResource("sql/city.sql")
				};
		databasePopulator = new ResourceDatabasePopulator(true, true, "UTF-8",initData);
		DatabasePopulatorUtils.execute(databasePopulator, dataSource);
	}
	
}
