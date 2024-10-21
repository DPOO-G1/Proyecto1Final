package learningPath;

import java.util.Date;
import actividades.Actividad;
import actividades.Recurso;

import java.util.ArrayList;
import java.util.List;

public class LearningPath {
	String titulo;
	String descripcion;
	String objetivos;
	String nivelDificultad;
	int duracion;
	Double rating;
	List<Actividad> actividades;
	Date fechaCreacion; 
	Date fechaUltModificacion;
	
	
	
	public LearningPath(String titulo, String descripcion, String objetivos, String nivelDificultad) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivos = objetivos;
		this.nivelDificultad = nivelDificultad;
		this.duracion = 0;
		this.rating = 0.0;
		this.actividades = new ArrayList<>();
		this.fechaCreacion = new Date();
		this.fechaUltModificacion = new Date();
	}



	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getObjetivos() {
		return objetivos;
	}



	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}



	public String getNivelDificultad() {
		return nivelDificultad;
	}



	public void setNivelDificultad(String nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}



	public int getDuracion() {
		return duracion;
	}



	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}



	public Double getRating() {
		return rating;
	}



	public void setRating(Double rating) {
		this.rating = rating;
	}



	public List<Actividad> getActividades() {
		return actividades;
	}



	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}



	public Date getFechaCreacion() {
		return fechaCreacion;
	}



	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}



	public Date getFechaUltModificacion() {
		return fechaUltModificacion;
	}



	public void setFechaUltModificacion(Date fechaUltModificacion) {
		this.fechaUltModificacion = fechaUltModificacion;
	}
	
	public void addActividad(Actividad actividad) {
		actividades.add(actividad);
		actualizarFechaUltModificacion();
		actualizarDuracion(actividad.getDuracion());
	}
	public void eliminarActivdad(Actividad actividad) {
		this.actividades.remove(actividad);
		actualizarFechaUltModificacion();
		actualizarDuracion(actividad.getDuracion());
	}
	
	private void actualizarFechaUltModificacion() {
		this.setFechaUltModificacion(new Date());
	}
	
	private void actualizarDuracion(double duracionIni) {
		duracion = this.getDuracion();
		duracion += duracionIni;
		this.setDuracion(duracion);
	}
	
	public void eliminarActividad(Actividad actividad, LearningPath learningPath) {
		learningPath.eliminarActivdad(actividad);
	}
	public void mostrarActividades() {
		if(this.actividades.size()>0) {
			System.out.println("\nEstos son las actividades de "+ this.titulo);
			for(int i = 0; i<this.actividades.size();i++) {
				Actividad actividad = this.actividades.get(i);
	
				System.out.println(i + "." + actividad.getDescripcion());
				System.out.println("Tipo: "+ actividad.getTipo());
				System.out.println("Nivel: "+ actividad.getNivelDificultad());
				System.out.println("Objetivo: "+ actividad.getObjetivo());
				System.out.println("Fecha límite sugerida: "+ actividad.getFechaLim());
				System.out.println("");
				
			}
		}
		else {
			System.out.println("Todavía no hay actividades asociadas.");
		}
	}
	
	public void menu() {
        System.out.println("Menú de "+ this.titulo);
        System.out.println("");
        System.out.println("1. Ver actividades en "+this.titulo);
        System.out.println("2. Añadir actividad");
        System.out.println("3. Eliminar Actividad");
	}
	
}
