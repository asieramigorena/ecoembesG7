package com.ecoembes.external;

import com.ecoembes.dto.CapacidadPlantasDTO;
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
}
