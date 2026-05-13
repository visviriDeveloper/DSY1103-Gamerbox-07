package com.melisoft.ms_resenias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsReseniasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsReseniasApplication.class, args);
	}

}
