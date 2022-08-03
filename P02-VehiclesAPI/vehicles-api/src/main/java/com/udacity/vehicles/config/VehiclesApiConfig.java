package com.udacity.vehicles.config;

import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class VehiclesApiConfig {

  private ApiInfo apiInfo(){
    return new ApiInfo(
        "Vehicles API",
        "API to manage Database system",
        "1.0",
        "",
        new Contact("Vehicle API", "github.com/pablinchapin", ""),
        "",
        "",
        Collections.emptyList()
    );
  }

  @Bean
  public Docket api(){
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  /**
   * Initializes the car manufacturers available to the Vehicle API.
   * @param repository where the manufacturer information persists.
   * @return the car manufacturers to add to the related repository
   */
  @Bean
  CommandLineRunner initDatabase(ManufacturerRepository repository) {
    return args -> {
      repository.save(new Manufacturer(100, "Audi"));
      repository.save(new Manufacturer(101, "Chevrolet"));
      repository.save(new Manufacturer(102, "Ford"));
      repository.save(new Manufacturer(103, "BMW"));
      repository.save(new Manufacturer(104, "Dodge"));
    };
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  /**
   * Web Client for the maps (location) API
   * @param endpoint where to communicate for the maps API
   * @return created maps endpoint
   */
  @Bean(name="maps")
  public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
    return WebClient.create(endpoint);
  }

  /**
   * Web Client for the pricing API
   * @param endpoint where to communicate for the pricing API
   * @return created pricing endpoint
   */
  @Bean(name="pricing")
  public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint) {
    return WebClient.create(endpoint);
  }

}
