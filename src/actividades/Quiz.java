package actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Quiz extends Actividad {
	
	List<PreguntaCerrada> preguntas;
	double calificacionMin;
	
	public Quiz(String descripcion, String objetivo, String nivelDificultad, int duracion,
			 Date fechaLim,double calificacionMin, boolean obligatoria) {
		super("Quiz",descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
		
		this.preguntas = new ArrayList<>();
		this.calificacionMin = calificacionMin;
	}
	
	public void menu() {
		
	}

	public void addPregunta(PreguntaCerrada pregunta) {
		this.preguntas.add(pregunta);
	}

	public List<PreguntaCerrada> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaCerrada> preguntas) {
		this.preguntas = preguntas;
	}

	public double getCalificacionMin() {
		return calificacionMin;
	}

	public void setCalificacionMin(double calificacionMin) {
		this.calificacionMin = calificacionMin;
	}
	
	

}

