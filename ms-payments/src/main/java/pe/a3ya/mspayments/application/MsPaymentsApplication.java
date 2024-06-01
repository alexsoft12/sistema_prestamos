package pe.a3ya.mspayments.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("pe.a3ya.*")
@EnableJpaRepositories("pe.a3ya")
@EntityScan("pe.a3ya.*")
//@EnableFeignClients("pe.a3ya.*")
public class MsPaymentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPaymentsApplication.class, args);
    }

}
