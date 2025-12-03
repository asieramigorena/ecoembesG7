package com.ecoembes.external;

import com.ecoembes.dto.CapacidadPlantasDTO;
import com.ecoembes.dto.CapacidadResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PlasSBServiceProxy {

    private final static String BASE_URL = "http://localhost:9090/capacidades";
    private final RestTemplate restTemplate;

    public PlasSBServiceProxy() {
        this.restTemplate = new RestTemplate();
    }

    public CapacidadPlantasDTO getCapacidad(String fecha) {
        String url = BASE_URL + "/" + fecha;
        return restTemplate.getForObject(url, CapacidadPlantasDTO.class);
    }
    
    public CapacidadPlantasDTO getCapacidadPlasSB(String fecha) {
    	

        // Llamada a la API 
        String url = BASE_URL + "/" + fecha;
        CapacidadResponse response =
                restTemplate.getForObject(url, CapacidadResponse.class);

        // Construimos el DTO final
        CapacidadPlantasDTO capacidadPlantasDTOresultado = new CapacidadPlantasDTO(
				"PlasSB",                
				response.getCapacidad()  
		);
        
        return capacidadPlantasDTOresultado;
        
    }
    

    public CapacidadPlantasDTO updateCapacidad(String fecha, double capacidadNueva) {
        RestTemplate restTemplate = new RestTemplate();

        String url = BASE_URL + "/" + fecha;

        // Crear el DTO con los valores que quieres actualizar
        CapacidadPlantasDTO capacidadDTO = new CapacidadPlantasDTO(null, capacidadNueva);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CapacidadPlantasDTO> requestEntity =
                new HttpEntity<>(capacidadDTO, headers);

        ResponseEntity<CapacidadPlantasDTO> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                CapacidadPlantasDTO.class
        );

        return response.getBody();
    }

}
