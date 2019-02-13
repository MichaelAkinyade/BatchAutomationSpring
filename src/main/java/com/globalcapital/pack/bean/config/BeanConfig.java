/**
 * @Author Michael Akinyade Administrator
 */
/*
 * package com.globalcapital.pack.bean.config;
 * 
 * import java.util.Properties;
 * 
 * import javax.sql.DataSource;
 * 
 * import org.apache.commons.dbcp.BasicDataSource; import
 * org.springframework.context.annotation.Bean;
 * 
 *//**
	 * @author Administrator
	 *
	 *//*
		 * 
		 * import org.springframework.context.annotation.ComponentScan; import
		 * org.springframework.context.annotation.Configuration; import
		 * org.springframework.orm.hibernate5.LocalSessionFactoryBean; import
		 * org.springframework.web.servlet.config.annotation.EnableWebMvc;
		 * 
		 * @Configuration
		 * 
		 * @EnableWebMvc
		 * 
		 * @ComponentScan(basePackages = "com.globalcapital.pack") public class
		 * BeanConfig {
		 * 
		 * @Bean public LocalSessionFactoryBean sessionFactory() {
		 * LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		 * sessionFactory.setDataSource(dataSource());
		 * sessionFactory.setPackagesToScan(new String[] { "com.globalcapital.pack" });
		 * sessionFactory.setHibernateProperties(hibernateProperties()); return
		 * sessionFactory; }
		 * 
		 * @Bean public DataSource dataSource() { BasicDataSource dataSource = new
		 * BasicDataSource(); dataSource.setDriverClassName("org.h2.Driver"); //
		 * org.h2.Driver dataSource.setUrl("jdbc:h2:tcp://localhost/~/test"); //
		 * jdbc:h2:tcp://localhost/~/test dataSource.setUsername("sa"); // sa
		 * dataSource.setPassword(""); // abc
		 * 
		 * return dataSource;
		 * 
		 * }
		 * 
		 * Properties hibernateProperties() {
		 * 
		 * return new Properties() { { setProperty("hibernate.hbm2ddl.auto", "update");
		 * setProperty("hibernate.dialect",
		 * "org.hibernate.dialect.MySQL5InnoDBDialect");
		 * setProperty("hibernate.show_sql", "true"); } };
		 * 
		 * Properties properties = new Properties(); properties.put("hibernate.dialect",
		 * "org.hibernate.dialect.H2Dialect"); properties.put("hibernate.show_sql",
		 * "true"); properties.put("hibernate.format_sql", "true");
		 * properties.put("hibernate.hbm2ddl.auto", "update");
		 * properties.put("hibernate.lazy", "false"); return properties;
		 * 
		 * }
		 * 
		 * }
		 */