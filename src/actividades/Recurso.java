package actividades;

import java.util.Date;
import java.util.List;

public class Recurso extends Actividad {
	
	String tipoRecurso;
	
	public Recurso(String descripcion, String objetivo, String nivelDificultad, double duracion,
			 Date fechaLim,  boolean obligatoria, String tipoRecurso) {
		super("Recurso",descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
		this.tipoRecurso = tipoRecurso;
	}

	@Override
	public void menu() {
		// TODO Auto-generated method stub
		
	}

	public String getTipoRecurso() {
		return tipoRecurso;
	}

	public void setTipoRecurso(String tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}

	
	

	
	
}


