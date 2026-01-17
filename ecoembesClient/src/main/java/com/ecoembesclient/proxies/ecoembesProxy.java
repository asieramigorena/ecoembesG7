package com.ecoembesclient.proxies;

import com.ecoembesclient.data.CapacidadPlantas;
import com.ecoembesclient.data.Contenedor;
import com.ecoembesclient.data.Empleado;
import com.ecoembesclient.data.Jornada;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDate;


@Component
public class ecoembesProxy {
    private final RestTemplate restTemplate;
    private String correoIniciado;

    @Value("${api.base.url}")
    private String apiBaseUrl;

    public ecoembesProxy(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    /*
    public Empleado login(Empleado empleado){
        String url = creadorMensaje("/login", 2, Arrays.asList("Correo", empleado.correo(), "Contraseña", empleado.contrasena()));
        try{
            return restTemplate.postForObject(url, null, Empleado.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }
     */

    // Falta por revisar TODO
    public Empleado login(Empleado empleado) {

        String url = creadorMensaje("login", 0, List.of());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("Correo", empleado.correo());
        form.add("Contraseña", empleado.contrasena());

        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<>(form, headers);
        try {
            return restTemplate.postForObject(url, request, Empleado.class);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    /*
    public void logout(Empleado empleado) {
        String url = creadorMensaje("/logout", 1, Arrays.asList("Correo", empleado.correo()));
        System.out.println(url);
        System.out.flush();
        try{
            restTemplate.postForObject(url, null, String.class);
         //   correoIniciado = null;
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    */
    public void logout(String correo) {

        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }

        String url = creadorMensaje("logout", 0, List.of());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("Correo", correo);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Logout realizado para: " + correo);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error logout backend: " + e.getResponseBodyAsString());
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
            return restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                Jornada.class
            ).getBody();
        } catch (HttpStatusCodeException e){
            throw new RuntimeException(e.getResponseBodyAsString());
        }
    }

    /*
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

     */

    public String creadorMensaje(String entidad, int elementos, List<Object> lista) {
        StringBuilder respuesta = new StringBuilder(apiBaseUrl);

        if (!apiBaseUrl.endsWith("/") && !entidad.startsWith("/")) {
            respuesta.append("/");
        }

        respuesta.append(entidad);

        if (elementos <= 0) {
            return respuesta.toString();
        }

        for (int i = 0; i < elementos; i++) {
            String key = URLEncoder.encode(
                    String.valueOf(lista.get(i * 2)),
                    StandardCharsets.UTF_8
            );
            String value = URLEncoder.encode(
                    String.valueOf(lista.get(i * 2 + 1)),
                    StandardCharsets.UTF_8
            );

            respuesta.append(i == 0 ? "?" : "&")
                    .append(key)
                    .append("=")
                    .append(value);
        }

        return respuesta.toString();
    }


    public List<Contenedor> buscarContenedoresPorFecha(int idContenedor, LocalDate fechaInicio, LocalDate fechaFin) {
        String url = creadorMensaje(
                "/contenedor/fecha",
                3,
                Arrays.asList(
                        "idContenedor", idContenedor,
                        "fechaInicio", fechaInicio.toString(),
                        "fechaFin", fechaFin.toString()
                )
        );

        try {
            Contenedor[] contenedores = restTemplate.getForObject(url, Contenedor[].class);
            return Arrays.asList(contenedores);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error al buscar contenedores: " + e.getResponseBodyAsString());
        }
    }

    public List<CapacidadPlantas> capacidadesPorFecha(LocalDate fecha){
        String url = creadorMensaje(
                "/jornada/capacidades",   // endpoint real del backend
                3,
                Arrays.asList(
                        "fecha", fecha.toString()
                )
        );

        try {
            CapacidadPlantas[] capacidades = restTemplate.getForObject(url, CapacidadPlantas[].class);
            return Arrays.asList(capacidades);
        } catch (HttpStatusCodeException e) {
            throw new RuntimeException("Error al buscar las capacidades: " + e.getResponseBodyAsString());
        }
    }




}
