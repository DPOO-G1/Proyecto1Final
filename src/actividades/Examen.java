package actividades;

import java.util.Date;
import java.util.List;

public class Examen extends Actividad {
    
	public List<PreguntaAbierta> preguntas; 
    public Examen(String descripcion, String objetivo, String nivelDificultad, double duracion,
                  Date fechaLim, boolean obligatoria, List<PreguntaAbierta> preguntas) {
        super("Examen", descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
        this.preguntas = preguntas;
    }
    public List<PreguntaAbierta> getPreguntas() {
        return preguntas;
    }

    @Override
    public void menu() {
    }
}