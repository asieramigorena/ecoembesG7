package com.ecoembes.external;

import com.ecoembes.dto.capacidadPlantasDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PlasSBServiceGateway {

    private final String BASE_URL = "http://localhost:9090/capacidades";
    private final RestTemplate restTemplate;

    public PlasSBServiceGateway() {
        this.restTemplate = new RestTemplate();
    }

    public capacidadPlantasDTO getCapacidad(String fecha) {
        String url = BASE_URL + "/" + fecha;
        return restTemplate.getForObject(url, capacidadPlantasDTO.class);
    }
}
