//package ael.example.security.auth.configure;
//
//import javax.sql.DataSource;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.core.io.Resource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.DatabasePopulator;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//
//@Configuration
//@ConfigurationProperties("spring.datasource")
//public class DBConfiguration {
//	
//	private String driverClassName;
//	private String url;
//	private String username;
//	private String password;
//    @Value("classpath:schema.sql")
//    private Resource schemaScript;
//    @Value("classpath:data.sql")
//    private Resource dataScript;
//	
//	
//	
//	public String getDriverClassName() {
//		return driverClassName;
//	}
//
//	public void setDriverClassName(String driverClassName) {
//		this.driverClassName = driverClassName;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//	 @Bean
//	    public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
//	        final DataSourceInitializer initializer = new DataSourceInitializer();
//	        initializer.setDataSource(dataSource);
//	        initializer.setDatabasePopulator(databasePopulator());
//	        return initializer;
//	    }
//
//	    private DatabasePopulator databasePopulator() {
//	        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//	        populator.addScript(schemaScript);
//	        populator.addScript(dataScript);
//	        return populator;
//	    }
//
//	    @Bean
//	    public DataSource dataSource() {
//	        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	        dataSource.setDriverClassName(driverClassName);
//	        dataSource.setUrl(url);
//	        dataSource.setUsername(username);
//	        dataSource.setPassword(password);
//	        return dataSource;
//	    }
//
//}
//
