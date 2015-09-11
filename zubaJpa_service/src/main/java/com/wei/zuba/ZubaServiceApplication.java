package com.wei.zuba;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@SpringBootApplication
@ComponentScan
//@ImportResource("classpath:/applicationContext.xml")
public class ZubaServiceApplication {


	public static void main(String[] args) throws Exception {
		SpringApplication.run(ZubaServiceApplication.class, args);
	}
	
	@Bean
	public DataSource dataSource() {
//		        spring.datasource.name=com.alibaba.druid.pool.DruidDataSource
//				spring.datasource.driver-class-name=com.mysql.jdbc.Driver
//				spring.datasource.url=jdbc:mysql://localhost:3306/test
//				spring.datasource.username=root 
//				spring.datasource.password=1qaz2wsxE
//				spring.datasource.max-idle=10
//				spring.datasource.max-active=40
//				spring.datasource.min-idle=10
//				spring.datasource.validation-query:select 1 from dual
//				spring.datasource.initial-siz=10
//				spring.datasource.test-while-idle:true
//				spring.datasource.test-on-borrow:false
//				spring.datasource.test-on-return:false
//				spring.datasource.time-between-eviction-runs-millis=3600000
//				spring.datasource.min-evictable-idle-time-millis=3600000
//				spring.datasource.max-wait=60000
		DruidDataSource ds = new DruidDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test");
		ds.setUsername("root");
		ds.setPassword("1qaz2wsxE");
		ds.setInitialSize(10);
		ds.setValidationQuery("select now()");
		ds.setMaxActive(50);
		return ds;
	}

}
