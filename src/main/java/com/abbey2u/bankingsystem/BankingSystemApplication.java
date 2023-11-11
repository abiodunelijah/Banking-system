package com.abbey2u.bankingsystem;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Abbey Banking System",
				description = "Backend REST APIs for Abbey Bank ",
				version = "v1.0",
				contact = @Contact(
						name = "Abiodun Elijah Siyanbola",
						email ="kaybie4u@gmail.com",
						url = "www"
				),
				license = @License(
						name ="Abiodun E. Siyanbola",
						url = "github"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Abbey Banking System App Documentation",
				url = "github"
		)
)
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

}
