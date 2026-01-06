package com.ecoembesclient.proxies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


// NO ES NECESARIO PERO LO PONGO POR SI EN ALGÚN MOMENTO PUDIERA HACER FALTA. (En un futuro lo podemos borrar.)
@Configuration
public class ecoembesRestTemplate {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
