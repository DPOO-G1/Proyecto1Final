package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import actividades.Actividad;
import actividades.Recurso;
import core.Consola;
import learningPath.LearningPath;
import usuarios.Profesor;
import usuarios.Usuario;

import java.util.Date;

public class PersistenciaActividades {
	 public static void guardarRecursosCSV(Actividad actividad) {
	        try (FileWriter writer = new FileWriter("actividades.csv", true)) {
	        	
	        	if (actividad.getTipo().equals("Recurso")) {
	     
	        		writer.append(actividad.getTipo()).append(",")
	        		.append(actividad.getDescripcion()).append(",").append(actividad.getObjetivo()).append(",").append(actividad.getNivelDificultad())
	        		.append(",").append(String.valueOf(actividad.getDuracion())).append(",").append(convertirFechatoString(actividad.getFechaLim())).append(",")
	        		.append(Boolean.toString(actividad.isObligatoria())).append(",").append(((Recurso) actividad).getTipoRecurso()).append("\n");
	        	}
	        	
	            
	        } catch (IOException e) {
	            System.out.println("Ocurrió un error al guardar el usuario.");
	            e.printStackTrace();
	        }
	    }
	 
	 public static void cargarActividades(Map<String, List<Actividad>> usuarios) {
		 
			try (BufferedReader reader = new BufferedReader(new FileReader("actividades.csv"))) {
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
	                String tipoRecurso = datos[7];
	                
	                Date fechaFinal = Consola.convertirFecha(fechaLim);
	                double duracionFinal = Double.parseDouble(duracion);
	                boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
	                
	                Recurso recurso = new Recurso(descripcion,objetivo,nivelDificultad,duracionFinal,fechaFinal,obligatoriaFinal,tipoRecurso);
	                
	                Usuario profesor = (Profesor)usuarios.get(correo);
	                
	                ((Profesor) profesor).addLearningPath(learningPath);
	              
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
}
