package com.ecoembes.excepciones;

public class Excepciones {
    public static class EmpleadoNoEncontradoException extends Exception {
        public EmpleadoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }
    public static class CredencialesInvalidasException extends Exception {
        public CredencialesInvalidasException(String mensaje) {
            super(mensaje);
        }
    }
    public static class ErrorTokenException extends Exception {
        public ErrorTokenException(String mensaje) {
            super(mensaje);
        }
    }
    public static class SesionNoIniciadaException extends Exception {
        public SesionNoIniciadaException(String mensaje) {
            super(mensaje);
        }
    }
}
