package com.ecoembes.external;

public interface Gateway {
    String enviar(String mensaje);

    default String prepararMensaje(String mensaje) {
        //Solución de ChatGPT para parsear mensajes
        if (mensaje == null) return "";
        String m = mensaje.trim();
        String[] parts = m.split(" ", 3);
        if (parts.length < 2) return m;
        String method = parts[0].toUpperCase();
        String recurso = parts[1];
        if (!recurso.startsWith("/")) recurso = "/" + recurso;
        String body = parts.length == 3 ? parts[2].trim() : null;
        return body != null && !body.isEmpty() ? method + " " + recurso + " " + body : method + " " + recurso;
    }

    default String enviarGet(String recurso, String queryParams) {
        String path = recurso != null && recurso.startsWith("/") ? recurso : (recurso == null ? "/" : "/" + recurso);
        String message = "GET " + path;
        if (queryParams != null && !queryParams.isEmpty()) {
            message += "?" + queryParams;
        }
        return enviar(prepararMensaje(message));
    }

    default String enviarGet(String recurso) {
        return enviarGet(recurso, null);
    }

    default String enviarPut(String recurso, String body) {
        String path = recurso != null && recurso.startsWith("/") ? recurso : (recurso == null ? "/" : "/" + recurso);
        String message = "PUT " + path + (body != null && !body.isEmpty() ? " " + body : "");
        return enviar(prepararMensaje(message));
    }
}

