package siga.artsoft.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

//	@Bean
//	public DataSource getDataSource() {
//		HikariConfig config = new HikariConfig();
//		config.setJdbcUrl("jdbc:mysql://localhost:3306/ismu");
//		config.setUsername("ipca");
//		config.setPassword("Ipc@2019");
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        config.setMaximumPoolSize(300);
//        config.setMinimumIdle(10);
//        config.setConnectionTimeout(30000);
//        config.setAutoCommit(false);
//        config.setValidationTimeout(3000);
//        config.setIdleTimeout(60000);
//        config.setLeakDetectionThreshold(8000);
//        config.setMaxLifetime(300000);
//		return new HikariDataSource(config);
//	}
}
