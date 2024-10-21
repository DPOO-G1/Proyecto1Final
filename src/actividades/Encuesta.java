package actividades;

import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad{

	List<PreguntaAbierta> listaPreguntas;
	
	public Encuesta(String tipo, String descripcion, String objetivo, String nivelDificultad, double duracion,
			Date fechaLim, boolean obligatoria) {
		super(tipo, descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
		// TODO Auto-generated constructor stub
	}

	public List<PreguntaAbierta> getListaPreguntas() {
		return listaPreguntas;
	}

	public void setListaPreguntas(List<PreguntaAbierta> listaPreguntas) {
		this.listaPreguntas = listaPreguntas;
	}

	
}
