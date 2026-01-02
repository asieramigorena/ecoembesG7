// java
package com.ecoembes.external;

import com.ecoembes.dto.CapacidadPlantasDTO;
import com.ecoembes.dto.CapacidadResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PlasSBServiceProxy implements Gateway {

    private static PlasSBServiceProxy instance;
    private static final String BASE_URL = "http://localhost:9090/capacidades";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public static PlasSBServiceProxy getInstance() {
        if (instance == null) {
            instance = new PlasSBServiceProxy();
        }
        return instance;
    }

    private PlasSBServiceProxy() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public CapacidadPlantasDTO getCapacidadPlasSB(String fecha) {
        String recurso = (fecha != null && fecha.startsWith("/")) ? fecha : "/" + fecha;
        String respuesta = enviarGet(recurso);
        try {
            CapacidadResponse cr = objectMapper.readValue(respuesta, CapacidadResponse.class);
            return new CapacidadPlantasDTO("PlasSB", cr.getCapacidad());
        } catch (Exception e) {
            throw new RuntimeException("Error parseando respuesta de getCapacidadPlasSB: " + e.getMessage(), e);
        }
    }

    public CapacidadPlantasDTO updateCapacidad(String fecha, double capacidadNueva) {
        String recurso = (fecha != null && fecha.startsWith("/")) ? fecha : "/" + fecha;
        try {
            CapacidadPlantasDTO payload = new CapacidadPlantasDTO(null, capacidadNueva);
            String body = objectMapper.writeValueAsString(payload);
            String respuesta = enviarPut(recurso, body);
            return objectMapper.readValue(respuesta, CapacidadPlantasDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando body de updateCapacidad: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error en updateCapacidad: " + e.getMessage(), e);
        }
    }

    // Implementación de la capa de mensajería: recibe mensajes ya preparados por prepararMensaje()
    @Override
    public String enviar(String mensaje) {
        try {
            //Solución de ChatGPT para parsear mensajes
            String msg = prepararMensaje(mensaje);
            String[] parts = msg.split(" ", 3); // METHOD, recurso, body (opcional)
            if (parts.length < 2) {
                throw new IllegalArgumentException("Mensaje inválido, formato: METHOD recurso [body]");
            }
            String method = parts[0].toUpperCase();
            String recurso = parts[1];
            String body = parts.length == 3 ? parts[2] : null;

            String url = BASE_URL + recurso;

            if ("GET".equals(method)) {
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
                return response.getBody();
            } else if ("PUT".equals(method)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<String> entity = new HttpEntity<>(body != null ? body : "", headers);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
                return response.getBody();
            } else {
                throw new UnsupportedOperationException("Método no soportado: " + method);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error en PlasSBServiceProxy.enviar: " + e.getMessage(), e);
        }
    }
}
