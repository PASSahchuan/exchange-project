package com.example.exchange_demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    final RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(jacksonSupportsMoreTypes());
    return restTemplate;
  }

  private HttpMessageConverter jacksonSupportsMoreTypes() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(
        Arrays.asList(
            MediaType.parseMediaType("text/plain;charset=utf-8"),
            MediaType.APPLICATION_OCTET_STREAM
        )
    );
    return converter;
  }

}
