package com.eca.visitor.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.eca.visitor.utils.CommonUtils;
import com.eca.visitor.utils.JsonUtils;

import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class VisitorAppConfig {

    @Value("${app.openapi.dev-url}")
    private String devUrl;

    @Value("${app.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public CommonUtils commonUtils(){
        return new CommonUtils();
    }

    @Bean
    public JsonUtils jsonUtils(){
        return new JsonUtils();
    }
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("eca-visitor@gmail.com");
        contact.setName("eca visitor");
        contact.setUrl("https://www.eca-visitor.com");

        License mitLicense = new License().name("PS License").url("https://eca.com/licenses/ps/");

        Info info = new Info().title("ECA Visitor Management API").version("1.0").contact(contact)
                .description("This API exposes endpoints to manage apartment visitors.")
                .termsOfService("https://www.eca-visitor.com/terms").license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
