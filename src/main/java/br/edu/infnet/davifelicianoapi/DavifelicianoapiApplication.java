package br.edu.infnet.davifelicianoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DavifelicianoapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DavifelicianoapiApplication.class, args);
	}

}
