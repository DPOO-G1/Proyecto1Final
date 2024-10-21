package persistencia;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Opcion;
import actividades.PreguntaAbierta;
import actividades.PreguntaCerrada;
import actividades.Quiz;
import actividades.Recurso;
import actividades.Tarea;
import core.Consola;
import learningPath.LearningPath;
import usuarios.Profesor;
import usuarios.Usuario;

import java.util.Date;

public class PersistenciaActividades {
	 public static void guardarRecursosCSV(Actividad actividad) {
	        try (FileWriter writer = new FileWriter("actividades.csv", true)) {
	        	
	        	if (actividad.getTipo().equals("Recurso")) {
	        		
	        		Recurso recurso = (Recurso) actividad;
	        		writer.append(recurso.getTipo()).append(",")
	        		.append(recurso.getDescripcion()).append(",").append(recurso.getObjetivo()).append(",").append(recurso.getNivelDificultad())
	        		.append(",").append(String.valueOf(recurso.getDuracion())).append(",").append(convertirFechatoString(recurso.getFechaLim())).append(",")
	        		.append(Boolean.toString(recurso.isObligatoria())).append(",").append(recurso.getTipoRecurso()).append("\n");
	        	}
	        	else if (actividad.getTipo().equals("Quiz")) {
	        		Quiz quiz = (Quiz) actividad;
	        		List<PreguntaCerrada> listaPreguntas =quiz.getPreguntas();
	        		String cantidadPreguntas = Integer.toString(listaPreguntas.size());
	        		String calificacionMin = Double.toString(quiz.getCalificacionMin());
	        		
	        		writer.append(quiz.getTipo()).append(",")
	        		.append(quiz.getDescripcion()).append(",").append(quiz.getObjetivo()).append(",").append(quiz.getNivelDificultad())
	        		.append(",").append(String.valueOf(quiz.getDuracion())).append(",").append(convertirFechatoString(quiz.getFechaLim())).append(",")
	        		.append(Boolean.toString(quiz.isObligatoria())).append(",").append(calificacionMin).append(",").append(cantidadPreguntas).append(",");
	        		
	        		for(int i=0;i<listaPreguntas.size();i++) {
	        			PreguntaCerrada preguntaCerrada = listaPreguntas.get(i);
	        			List<Opcion> listaOpciones= preguntaCerrada.getOpciones();
	        			String cantidadOpciones = Integer.toString(listaPreguntas.size());
	        			String explicacionOpcionCorrecta = (preguntaCerrada.getOpcionCorrecta()).getExplicacion();
	        			String cuerpoPregunta = preguntaCerrada.getCuerpo();
	        			writer.write(explicacionOpcionCorrecta);
	        			writer.write(",");
	        			writer.write(cuerpoPregunta);
	        			writer.write(",");
	        			writer.write(cantidadOpciones);
	        			writer.write(",");
	        			
	        			for(int j=0;j<listaOpciones.size();j++) {
	        				String opcion = (listaOpciones.get(j).getExplicacion());
	        				writer.write(opcion);
	        				writer.write(",");
	        			}        		        		
	        		}
	        		writer.write("\n");
	        	}
	        	else if(actividad.getTipo().equals("Examen")) {
	        		Examen examen = (Examen) actividad;
	        		List<PreguntaAbierta> listaPreguntas =examen.getPreguntas();
	        		
	        		writer.append(examen.getTipo()).append(",")
	        		.append(examen.getDescripcion()).append(",").append(examen.getObjetivo()).append(",").append(examen.getNivelDificultad())
	        		.append(",").append(String.valueOf(examen.getDuracion())).append(",").append(convertirFechatoString(examen.getFechaLim())).append(",")
	        		.append(Boolean.toString(examen.isObligatoria())).append(",").append(Integer.toString(listaPreguntas.size()));
	        		
	        		for(int i=0;i<listaPreguntas.size();i++) {
	        			PreguntaAbierta preguntaAbierta = listaPreguntas.get(i);
	        			String cuerpoPregunta = preguntaAbierta.getCuerpo();
	        			writer.write(cuerpoPregunta);
	        			
	        		}
	        		writer.write("\n");
	        	}
	        	
	        	else if(actividad.getTipo().equals("Encuesta")) {
	        		Encuesta encuesta = (Encuesta) actividad;
	        		List<PreguntaAbierta> listaPreguntas =encuesta.getPreguntas();
	        		
	        		for(int i=0;i<listaPreguntas.size();i++) {
	        			PreguntaAbierta preguntaAbierta = listaPreguntas.get(i);
	        			String cuerpoPregunta = preguntaAbierta.getCuerpo();
	        			writer.write(cuerpoPregunta);
	        			
	        		}
	 
	        		writer.write("\n");
	        	}
	        	else if(actividad.getTipo().equals("Tarea")) {
	        		Tarea tarea = (Tarea) actividad;
	        		List<Actividad> listaActividadesPrerequisito=tarea.getActividadesPrerrequisito();
	        		List<Actividad> listaActividadesOpcionales = tarea.getActividadesOpcionales();
	        		String cantidadActividadesPrerequisito = Integer.toString(listaActividadesPrerequisito.size());
	        		String cantidadActividadesOpcionales = Integer.toString(listaActividadesOpcionales.size());
	        		
	        		writer.append(tarea.getTipo()).append(",")
	        		.append(tarea.getDescripcion()).append(",").append(tarea.getObjetivo()).append(",").append(tarea.getNivelDificultad())
	        		.append(",").append(String.valueOf(tarea.getDuracion())).append(",").append(convertirFechatoString(tarea.getFechaLim())).append(",")
	        		.append(Boolean.toString(tarea.isObligatoria())).append(",").append(cantidadActividadesPrerequisito).append(",");
	        		
	        		for(int i=0;i<listaActividadesPrerequisito.size();i++) {
	        			Actividad actividadPrerequisito = listaActividadesPrerequisito.get(i);
	        			guardarActividadesSecundarias(actividadPrerequisito, writer);
	        		
	        		}
	        		writer.write(cantidadActividadesOpcionales);
	        		
	        		for(int i =0; i<listaActividadesOpcionales.size(); i++) {
	        			Actividad actividadOpcional = listaActividadesOpcionales.get(i);
	        			guardarActividadesSecundarias(actividadOpcional, writer);
	        		}

	        		writer.write("\n");
	        	}}
	        catch (IOException e) {
	            System.out.println("Ocurrió un error al guardar el usuario.");
	            e.printStackTrace();
	        }}
	    
	 
	 public static void cargarActividades(Map<String, List<Actividad>> usuarios) {

			try (BufferedReader reader = new BufferedReader(new FileReader("learningPaths.csv"))) {
	            String linea;
	            while ((linea = reader.readLine()) != null) {
	  
	                String[] datos = linea.split(",");
	                String tipo = datos[0];
	                String descripcion = datos[1];
	                String objetivo = datos[2];
	                String nivelDificultad = datos[3];
	                String duracion = datos[4];
	                String fechaLim= datos[5];
	                String obligatoria = datos[6];
	                if(tipo == "Recurso") {
		                String tipoRecurso = datos[7];
		                
		                Date fechaFinal = Consola.convertirFecha(fechaLim);
		                double duracionFinal = Double.parseDouble(duracion);
		                boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
		                
		                Recurso recurso = new Recurso(descripcion,objetivo,nivelDificultad,duracionFinal,fechaFinal,obligatoriaFinal,tipoRecurso);
		                
		                Usuario profesor = (Profesor)usuarios.get(correo);
	                
	                ((Profesor) profesor).addLearningPath(learningPath);}
	                else if(tipo=="Quiz") {
	                
	                	double calificacionMin = Double.parseDouble( datos[7]);
	                	int cantidadPreguntas = Integer.parseInt(datos[8]);
	                	List<PreguntaCerrada> listaPreguntasCerradas = new ArrayList<>();
	                	
	                	int i =0;
	                	int posicionDatos = 9;
	                	while(i<cantidadPreguntas) {
	                		String explicacionCorrecta = datos[posicionDatos];
	                		Opcion opcionCorrecta = new Opcion(explicacionCorrecta);
	                		posicionDatos++;
	                		String cuerpoPregunta = datos[posicionDatos];
	                		posicionDatos++;
	                		int cantidadOpciones= Integer.parseInt(datos[posicionDatos]);
	                		posicionDatos++;
	                		List<Opcion> listaOpciones= new ArrayList<>();
	                		for(int j = 0;j < cantidadOpciones;j++ ) {
	                			Opcion opcion = new Opcion(datos[posicionDatos]);
	                			posicionDatos++;
	                			listaOpciones.add(opcion);
	                			
	                		}
	                		PreguntaCerrada preguntaCerrada = new PreguntaCerrada(cuerpoPregunta, listaOpciones,opcionCorrecta);
	                		listaPreguntasCerradas.add(preguntaCerrada);
	                		
	                		i++;
	                	}
	                	 Date fechaFinal = Consola.convertirFecha(fechaLim);
			             int duracionFinal = Integer.parseInt(duracion);
			             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
	                	
	                	Quiz quiz = new Quiz(descripcion, objetivo, nivelDificultad, duracionFinal, fechaFinal, calificacionMin, obligatoriaFinal);
 	
	            }	
	                else if(tipo=="Examen") {
		                int cantidadPreguntas =Integer.parseInt(datos[7]);
		                List<PreguntaAbierta> listaPreguntas= new ArrayList<>();
		                int duracionFinal = Integer.parseInt(duracion);
		                
		                for(int posicion = 8; posicion<= cantidadPreguntas; posicion++) {
		                	String cuerpoPregunta=datos[posicion];
		                	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
		                	listaPreguntas.add(preguntaAbierta);
		                	
		                } 
		                Date fechaFinal = Consola.convertirFecha(fechaLim);
			             int duracionFinal1 = Integer.parseInt(duracion);
			             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
			             
		                Examen examen = new Examen(descripcion, objetivo,nivelDificultad, duracionFinal1, fechaFinal, obligatoriaFinal, listaPreguntas);
	                }
	                else if(tipo=="Examen") {
	                	
	                	
	                	
	                	
	                }
	                
	            }
	        } 
			catch (IOException e) {
	            System.out.println("Ocurrió un error al cargar los learning paths.");
	            e.printStackTrace();
	        }
		
		}
	 public static String convertirFechatoString(Date fecha) {
	     String fechaComoString = fecha.toString();
	     return fechaComoString;
	     
	 }


public static void guardarActividadesSecundarias(Actividad actividad, FileWriter writer) {
	
	try {
	if (actividad.getTipo().equals("Recurso")) {
		
		Recurso recurso = (Recurso) actividad;
		writer.append(recurso.getTipo()).append(",")
		.append(recurso.getDescripcion()).append(",").append(recurso.getObjetivo()).append(",").append(recurso.getNivelDificultad())
		.append(",").append(String.valueOf(recurso.getDuracion())).append(",").append(convertirFechatoString(recurso.getFechaLim())).append(",")
		.append(Boolean.toString(recurso.isObligatoria())).append(",").append(recurso.getTipoRecurso()).append("Act");
	}
	else if (actividad.getTipo().equals("Quiz")) {
		Quiz quiz = (Quiz) actividad;
		List<PreguntaCerrada> listaPreguntas =quiz.getPreguntas();
		String cantidadPreguntas = Integer.toString(listaPreguntas.size());
		String calificacionMin = Double.toString(quiz.getCalificacionMin());
		
		writer.append(quiz.getTipo()).append(",")
		.append(quiz.getDescripcion()).append(",").append(quiz.getObjetivo()).append(",").append(quiz.getNivelDificultad())
		.append(",").append(String.valueOf(quiz.getDuracion())).append(",").append(convertirFechatoString(quiz.getFechaLim())).append(",")
		.append(Boolean.toString(quiz.isObligatoria())).append(",").append(calificacionMin).append(",").append(cantidadPreguntas).append(",");
		
		for(int i=0;i<listaPreguntas.size();i++) {
			PreguntaCerrada preguntaCerrada = listaPreguntas.get(i);
			List<Opcion> listaOpciones= preguntaCerrada.getOpciones();
			String cantidadOpciones = Integer.toString(listaPreguntas.size());
			String explicacionOpcionCorrecta = (preguntaCerrada.getOpcionCorrecta()).getExplicacion();
			String cuerpoPregunta = preguntaCerrada.getCuerpo();
			writer.write(explicacionOpcionCorrecta);
			writer.write(",");
			writer.write(cuerpoPregunta);
			writer.write(",");
			writer.write(cantidadOpciones);
			writer.write(",");
			
			for(int j=0;j<listaOpciones.size();j++) {
				String opcion = (listaOpciones.get(j).getExplicacion());
				writer.write(opcion);
				writer.write(",");
			}        		        		
		}
		writer.write("Act");
	}
	else if(actividad.getTipo().equals("Examen")) {
		Examen examen = (Examen) actividad;
		List<PreguntaAbierta> listaPreguntas =examen.getPreguntas();
		
		writer.append(examen.getTipo()).append(",")
		.append(examen.getDescripcion()).append(",").append(examen.getObjetivo()).append(",").append(examen.getNivelDificultad())
		.append(",").append(String.valueOf(examen.getDuracion())).append(",").append(convertirFechatoString(examen.getFechaLim())).append(",")
		.append(Boolean.toString(examen.isObligatoria())).append(",");
		
		for(int i=0;i<listaPreguntas.size();i++) {
			PreguntaAbierta preguntaAbierta = listaPreguntas.get(i);
			String cuerpoPregunta = preguntaAbierta.getCuerpo();
			writer.write(cuerpoPregunta);
			
		}
		writer.write("Act");
	}
	
	else if(actividad.getTipo().equals("Encuesta")) {
		Encuesta encuesta = (Encuesta) actividad;
		List<PreguntaAbierta> listaPreguntas =encuesta.getPreguntas();
		
		for(int i=0;i<listaPreguntas.size();i++) {
			PreguntaAbierta preguntaAbierta = listaPreguntas.get(i);
			String cuerpoPregunta = preguntaAbierta.getCuerpo();
			writer.write(cuerpoPregunta);
			
		}

		writer.write("Act");
	}
	
		
} catch (IOException e) {
	System.out.println("Ocurrió un error al guardar las actividades secundarias de una tarea");
	e.printStackTrace();
}
	
}
}
