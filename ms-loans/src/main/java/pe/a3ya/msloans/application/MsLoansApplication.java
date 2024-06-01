package pe.a3ya.msloans.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("pe.a3ya.*")
@EnableJpaRepositories("pe.a3ya")
@EntityScan("pe.a3ya.*")
@EnableFeignClients("pe.a3ya.*")
public class MsLoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLoansApplication.class, args);
	}

}
