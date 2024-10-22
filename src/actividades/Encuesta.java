package actividades;

import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad {

    List<PreguntaAbierta> preguntas;

    public Encuesta(String descripcion, String objetivo, String nivelDificultad, double duracion,
                    Date fechaLim, boolean obligatoria, List<PreguntaAbierta> preguntas) {
        super("Encuesta", descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
        this.preguntas = preguntas;  
    }


    public List<PreguntaAbierta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<PreguntaAbierta> preguntas) {
        this.preguntas = preguntas;
    }

    public void addPregunta(PreguntaAbierta pregunta) {
        this.preguntas.add(pregunta);
    }

    // Override the abstract menu method
    @Override
    public void menu() {
        System.out.println("Menu for Encuesta");
    
    }}
