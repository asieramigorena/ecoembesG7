package com.ecoembes.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "plantas")
public class PlantaReciclaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column
	protected String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plantaAsignada")
    protected List<Jornada> jornadas;

	public PlantaReciclaje(String nombre) {
		super();
		this.nombre = nombre;
	}

    public PlantaReciclaje() {

    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlantaReciclaje other = (PlantaReciclaje) obj;
		return id == other.id
				&& Objects.equals(nombre, other.nombre);
	}
}
