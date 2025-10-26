package siga.artsoft.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public DataSource getDataSource() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/ismu");
		config.setUsername("ipca");
		config.setPassword("Ipc@2019");
		config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		config.setMaximumPoolSize(100);
		return new HikariDataSource(config);
	}
}
