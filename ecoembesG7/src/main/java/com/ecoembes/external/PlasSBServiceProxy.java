package com.ecoembes.external;

import com.ecoembes.dto.capacidadPlantasDTO;
import com.ecoembes.dto.CapacidadResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PlasSBServiceProxy {

    private final String BASE_URL = "http://localhost:9090/capacidades";
    private final RestTemplate restTemplate;

    public PlasSBServiceProxy() {
        this.restTemplate = new RestTemplate();
    }

    public capacidadPlantasDTO getCapacidad(String fecha) {
        String url = BASE_URL + "/" + fecha;
        return restTemplate.getForObject(url, capacidadPlantasDTO.class);
    }
    
    public capacidadPlantasDTO getCapacidadPlasSB(String fecha) {
    	

        // Llamada a la API 
        String url = BASE_URL + "/" + fecha;
        CapacidadResponse response =
                restTemplate.getForObject(url, CapacidadResponse.class);

        // Construimos el DTO final
        capacidadPlantasDTO capacidadPlantasDTOresultado = new capacidadPlantasDTO(
				"PlasSB",                
				response.getCapacidad()  
		);
        
        return capacidadPlantasDTOresultado;
        
    }
}
