package com.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "empleados")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int idPersonal;
    @Column()
    protected String nombre;
    @Column()
    protected String correo;
    @Column()
    protected String contrasena;
    @Transient
    private LocalDate token;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "asignadorPlanta")
    private List<Jornada> jornadasJefe;
    @ManyToMany (mappedBy = "personal")
    private List<Jornada> jornadas;



    public Empleado( String nombre, String correo, String contrasena) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.contrasena = contrasena;
		this.token = null;
	}

    public Empleado() {

    }

    public int getIdPersonal() {
		return idPersonal;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public LocalDate getToken() {
		return token;
	}

	public void setToken() {
		this.token = LocalDate.now();
	}

	public void setTokenNull() {
		this.token = null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo, idPersonal, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empleado other = (Empleado) obj;
		return Objects.equals(contrasena, other.contrasena)
				&& Objects.equals(correo, other.correo) && idPersonal == other.idPersonal
				&& Objects.equals(nombre, other.nombre);
	}
}
