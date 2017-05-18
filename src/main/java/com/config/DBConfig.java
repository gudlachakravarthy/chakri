package com.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.model.BlogComment;
import com.model.BlogPost;
import com.model.Friend;
import com.model.Job;
import com.model.ProfilePicture;
import com.model.User;

@Configuration
@EnableTransactionManagement
public class DBConfig {	
	@Bean
	public SessionFactory getSessionFactory(){
		LocalSessionFactoryBuilder lsf = new LocalSessionFactoryBuilder(getDataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		lsf.addProperties(hibernateProperties);
		Class classes[] = new Class[]{User.class,ProfilePicture.class,Job.class,BlogPost.class,BlogComment.class,Friend.class};
		lsf.addAnnotatedClasses(classes);
		return lsf.buildSessionFactory();
	}
    @Bean
    public DataSource getDataSource(){
    	BasicDataSource dataSource = new BasicDataSource();
    	dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
    	dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
    	dataSource.setUsername("DTPROJECT2");
    	dataSource.setPassword("chakri");
    	return dataSource;
    }	
}