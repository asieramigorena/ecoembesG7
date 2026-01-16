package com.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "jornadas")
public class Jornada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int idJornada;

    @ManyToOne()
    @JoinColumn(name = "asignadorPlanta_id")
	protected Empleado asignadorPlanta;// Empleado que asigna los contenedores a la planta en cada jornada.
    @ManyToMany
    @JoinTable(
            name = "jornada_empleado",
            joinColumns = @JoinColumn (name =  "jornada_id"),
            inverseJoinColumns = @JoinColumn (name = "empleado_id")
    )
	protected List<Empleado> personal;	// Empleados asignados a la jornada.
    @ManyToOne()
    @JoinColumn(name = "plantaAsignada_id")
	protected PlantaReciclaje plantaAsignada;
    @Column()
	protected double totalCapacidad; // Total de la capacidad que va a tener una planta en una jornada.
	@Column()
    protected LocalDate fechaJornada;
    @OneToOne(mappedBy = "jornada", cascade = CascadeType.ALL)
    private Historico_Contenedores historico;
	
	public Jornada(Empleado asignadorPlanta, List<Empleado> personal, PlantaReciclaje plantaAsignada,
			 double totalCapacidad, LocalDate fechaJornada) {
		super();
		this.asignadorPlanta = asignadorPlanta;
		this.personal = personal;
		this.plantaAsignada = plantaAsignada;
		this.totalCapacidad = totalCapacidad;
		this.fechaJornada = fechaJornada;
	}

    public Jornada() {

    }


    public int getIdJornada() {
		return idJornada;
	}

	public void setIdJornada(int idJornada) {
		this.idJornada = idJornada;
	}

	public Empleado getAsignadorPlanta() {
		return asignadorPlanta;
	}

	public void setAsignadorPlanta(Empleado asignadorPlanta) {
		this.asignadorPlanta = asignadorPlanta;
	}

	public List<Empleado> getPersonal() {
		return personal;
	}

	public void setPersonal(List<Empleado> personal) {
		this.personal = personal;
	}

	public PlantaReciclaje getPlantaAsignada() {
		return plantaAsignada;
	}

	public void setPlantaAsignada(PlantaReciclaje plantaAsignada) {
		this.plantaAsignada = plantaAsignada;
	}

	public double getTotalCapacidad() {
		return totalCapacidad;
	}

	public void setTotalCapacidad(double totalCapacidad) {
		this.totalCapacidad = totalCapacidad;
	}

	public LocalDate getFechaJornada() {
		return fechaJornada;
	}

	public void setFechaJornada(LocalDate fechaJornada) {
		this.fechaJornada = fechaJornada;
		
		
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(asignadorPlanta, fechaJornada, idJornada, personal, plantaAsignada,
				totalCapacidad);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jornada other = (Jornada) obj;
		return Objects.equals(asignadorPlanta, other.asignadorPlanta)

				&& Objects.equals(fechaJornada, other.fechaJornada) && idJornada == other.idJornada
				&& Objects.equals(plantaAsignada, other.plantaAsignada)
				&& Double.doubleToLongBits(totalCapacidad) == Double.doubleToLongBits(other.totalCapacidad);
	}

	@Override
	public String toString() {
		return "Jornada [idJornada=" + idJornada + ", asignadorPlanta=" + asignadorPlanta + ", personal=" + personal + ", plantaAsignada=" + plantaAsignada

				+ ", totalCapacidad=" + totalCapacidad + ", fechaJornada=" + fechaJornada + "]";
	}
}