package com.ecoembes.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public class Historico_Contenedores {
    private TreeMap<LocalDate, ArrayList<Contenedor>> lista;

    public Historico_Contenedores() {
        this.lista = new TreeMap<>();
    }

    public TreeMap<LocalDate, ArrayList<Contenedor>> getLista() {
        return lista;
    }
    public void setLista(TreeMap<LocalDate, ArrayList<Contenedor>> lista) {
        this.lista = lista;
    }

}
