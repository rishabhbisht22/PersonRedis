package com.person.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Value("${person.app.url}")
	private String url;
	
	@Bean
	public OpenAPI getOpenAPI()
	{
		Server server = new Server();
		server.setUrl(url);
		server.setDescription("These api for local environment");
		
		Contact contact = new Contact();
		contact.setName("Person");
		contact.setEmail("person.appwork@gmail.com");
		contact.setUrl("https://www.person.com");
		
		License license = new License().name("Person License").url("https://www.person.com/license");
		
		Info info = new Info()
				   .title("Person Management Api")
				   .version("1.0")
				   .contact(contact)
				   .license(license)
				   .description("These api manages Person data")
				   .termsOfService("https://www.person.com/terms");
		
		return new OpenAPI().info(info).servers(List.of(server));
				   
	}
}
