package com.ecoembesclient.proxies;

import com.ecoembesclient.data.Contenedor;
import com.ecoembesclient.data.Empleado;
import com.ecoembesclient.data.Jornada;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ecoembesProxy {
    private final RestTemplate restTemplate;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    public ecoembesProxy(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public Empleado login(Empleado empleado){
        String url = creadorMensaje("/login", 2, Arrays.asList("Correo", empleado.correo(), "Contraseña", empleado.contrasena()));
        try{
            return restTemplate.postForObject(url, null, Empleado.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    public String logout(Empleado empleado) {
        String url = creadorMensaje("/logout", 1, Arrays.asList("Correo", empleado.correo()));
        try{
            return restTemplate.postForObject(url, null, String.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    public Contenedor crearContenedor(Contenedor contenedor){
        String url = creadorMensaje("/contenedor", 3, Arrays.asList("ubicacion", contenedor.ubicacion(), "codPostal", contenedor.codPostal(), "capacidadMax", contenedor.capMaxima()));
        try{
            return restTemplate.postForObject(url, null, Contenedor.class);
        } catch (HttpStatusCodeException e){
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    public Jornada asignarContenedor(int idJornada, int idContenedor){
        String url = creadorMensaje("/jornada/asignacion", 2, Arrays.asList("idJornada", idJornada, "idContenedor", idContenedor));
        try{
            return restTemplate.postForObject(url, null, Jornada.class);
        } catch (HttpStatusCodeException e){
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    public String creadorMensaje(String entidad, int elementos, List<Object> lista){
        StringBuilder respuesta = new StringBuilder();
        respuesta.append(apiBaseUrl);
        respuesta.append(entidad);

        if (elementos <= 0) {
            return respuesta.toString();
        }

        for (int i = 0; i < elementos; i++) {
            Object keyObj = lista.get(i * 2);
            Object valObj = lista.get(i * 2 + 1);
            String key = keyObj == null ? "" : keyObj.toString();
            String value = valObj == null ? "" : URLEncoder.encode(valObj.toString(), StandardCharsets.UTF_8);
            respuesta.append(i == 0 ? "?" : "&").append(key).append("=").append(value);
        }

        return respuesta.toString();
    }
}
