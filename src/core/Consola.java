package core;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Opcion;
import actividades.PreguntaAbierta;
import actividades.PreguntaCerrada;
import actividades.Quiz;
import actividades.Recurso;
import learningPath.LearningPath;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaLearningPaths;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

public class Consola {
	private static Map<String, Usuario> usuarios = new HashMap<>();
	private static Map<String, List<Actividad>> mapaLearnigPaths = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    
    
	public static void main(String[] args) {
		usuarios = PersistenciaUsuarios.cargarUsuariosDeCSV();
		PersistenciaLearningPaths.cargarLearningPaths(usuarios);
		menu();
	}
	
	public static void menu() {
		
        while (true) {
    		usuarios = PersistenciaUsuarios.cargarUsuariosDeCSV();
    		PersistenciaLearningPaths.cargarLearningPaths(usuarios);
        	System.out.println("");
        	System.out.println("");
            System.out.println("Bienvenido a la consola de Learning Paths");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Ingresar");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    ingresar();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
	}
    
    private static void registrarUsuario() {
        System.out.print("Ingrese nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nombre el correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Es usted (1) Estudiante o (2) Profesor?: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();  
        
        if (tipo == 1) {
        	if(!usuarios.containsKey(correo)) {
        		usuarios.put(correo, new Estudiante(nombre, correo, password));
                PersistenciaUsuarios.guardarUsuarioEnCSV(nombre, correo, password, "Estudiante");
                System.out.println("Estudiante registrado con éxito.");
        	}
        	else {
        		System.out.print("El correo ya se encuentra registrado!");
        	}
            
        } else if (tipo == 2) {
        	if(!usuarios.containsKey(correo)) {
        		usuarios.put(correo, new Profesor(nombre,correo, password));
                PersistenciaUsuarios.guardarUsuarioEnCSV(nombre, correo, password, "Profesor");
                System.out.println("Profesor registrado con éxito.");
        	}
        	else {
        		System.out.print("El correo ya se encuentra registrado!");
        	}
        } else {
            System.out.println("Opción inválida, registro fallido.");
        }
    }
    
    private static void ingresar() {
        System.out.print("Ingrese el correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        
        if (usuarios.containsKey(correo)) {
        	Usuario usuario = usuarios.get(correo);
            if (usuario.getPassword().equals(password)) {
                if (usuario instanceof Estudiante) {
                    ((Estudiante) usuario).menu();
                    int opcion = scanner.nextInt();
                    }
                    
                 else if (usuario instanceof Profesor) {
                    ((Profesor) usuario).menu();
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); 
                    if (opcion==1) {
                    	System.out.print("Ingrese el título: ");
                    	String titulo = scanner.nextLine();
                    	System.out.print("Ingrese la descripción: ");
                    	String descripcion = scanner.nextLine();
                    	System.out.print("Ingrese los objetivos: ");
                    	String objetivos = scanner.nextLine();
                    	System.out.print("Ingrese el nivel de dificultad: ");
                    	String nivelDificultad = scanner.nextLine();
                    	
                    	LearningPath learningpath = new LearningPath(titulo, descripcion, objetivos,nivelDificultad);
                    	
                    	((Profesor) usuario).addLearningPath(learningpath);
                    	persistencia.PersistenciaLearningPaths.guardarLearningPath(learningpath,usuario.getCorreo());
                	}
                    else if(opcion == 2) {
                    	((Profesor) usuario).mostrarLearningPaths();
                    	int learningPathSeleccionado = scanner.nextInt();
                    	scanner.nextLine();
                    	LearningPath learningpath = ((Profesor) usuario).getLearningpaths().get(learningPathSeleccionado);
                    	learningpath.menu();
                    	int opcion2 = scanner.nextInt();
                    	scanner.nextLine(); 
                    	if (opcion2 == 1) {
                    		learningpath.mostrarActividades();
                    	}
                    	else if (opcion2 == 2) {
                    		System.out.println("Tipo de actividad: ");
                    		System.out.println("1. Recurso");
                            System.out.println("2. Tarea");
                            System.out.println("3. Quiz");
                            System.out.println("4. Examen");
                    		int tipoActividad = scanner.nextInt();
                    		scanner.nextLine(); 
                    		if (tipoActividad == 1) {
                            	Recurso recurso = (Recurso) crearRecurso();
	                    		persistencia.PersistenciaActividades.guardarRecursosCSV(recurso);
                            	learningpath.addActividad(recurso);

	       		
	                    		for(int i = 0; i<learningpath.getActividades().size();i++) {
	                    			
	                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
	                				System.out.println(learningpath.getActividades().size());
	                    		}
	                    	}
	                    	else if (tipoActividad == 3) {
	                            	Quiz quiz = (Quiz) crearQuiz();
		                    		persistencia.PersistenciaActividades.guardarRecursosCSV(quiz);
	                            	learningpath.addActividad(quiz);

		       		
		                    		for(int i = 0; i<learningpath.getActividades().size();i++) {
		                    			
		                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
		                				System.out.println(learningpath.getActividades().size());
		                    		}
		                    		}
		                    else if (tipoActividad == 3) {
		                            	Examen examen = (Examen) crearExamen();
			                    		persistencia.PersistenciaActividades.guardarRecursosCSV(examen);
		                            	learningpath.addActividad(examen);

			       		
			                    		for(int i = 0; i<learningpath.getActividades().size();i++) {
			                    			
			                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
			                				System.out.println(learningpath.getActividades().size());
			                    		}
                    		}
                    	}
                    }
                    else if(opcion ==3) {
                    	menu();
                    }
                 }
            
                }
             else {
                System.out.println("Contraseña incorrecta.");
            }
        }else {
            System.out.println("Usuario no encontrado.");
        }
    

    }
    
    public static Actividad crearRecurso() {
    	
    	System.out.print("Ingrese la descripción: ");
    	String descripcion = scanner.nextLine();
    	System.out.print("Ingrese el objetivo: ");
    	String objetivo = scanner.nextLine();
    	System.out.print("Ingrese el nivel de dificultad: ");
    	String nivelDificultad = scanner.nextLine();
    	System.out.print("Ingrese la duración esperada en minutos: ");
    	int duracion = scanner.nextInt();
    	scanner.nextLine();
    	System.out.print("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
    	String fechaLim = scanner.nextLine();
    	System.out.print("Es obligatoria (1:Si | 0:NO): ");
    	int obligatoria = scanner.nextInt();
    	scanner.nextLine();
    	System.out.print("Ingrese el tipo de recurso: ");
    	String tipoRecurso = scanner.nextLine();
		
    	boolean esObligatoria = opcional(obligatoria);
    	Date fechaLimite = convertirFecha(fechaLim);
    	
    	Actividad recurso = new Recurso(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, esObligatoria,tipoRecurso);
    	
    	return recurso;
    	
    }

    
    
    
    public static Actividad crearQuiz() {
    	System.out.println("Ingrese la descripción: ");
    	String descripcion = scanner.nextLine();
    	System.out.println("Ingrese el objetivo: ");
    	String objetivo = scanner.nextLine();
    	System.out.println("Ingrese el nivel de dificultad: ");
    	String nivelDificultad = scanner.nextLine();
    	System.out.println("Ingrese la duración esperada en minutos: ");
    	int duracion = scanner.nextInt();
    	scanner.nextLine();
    	System.out.println("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
    	String fechaLim = scanner.nextLine();
    	System.out.println("Es obligatoria (1:Si | 0:NO): ");
    	int obligatoria = scanner.nextInt();
    	scanner.nextLine();
    	boolean esObligatoria = opcional(obligatoria);
    	Date fechaLimite = convertirFecha(fechaLim);
    	System.out.println("Ingrese la cantidad de preguntas que va a tener el quiz: ");
		int cantidadPreguntas = scanner.nextInt();
		System.out.println("Ingrese el minimo para pasar: ");
		int minimoQuiz = scanner.nextInt();
		Quiz quiz = new Quiz(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, minimoQuiz, esObligatoria);
		for(int i = 1; i<= cantidadPreguntas; i++) {
		
			System.out.println("Ingrese la explicacion de la pregunta: ");
			String explicacion = scanner.nextLine();
			System.out.println("Ingrese el numero opciones de la pregunta (4 max): ");
			int cantidadOpciones = scanner.nextInt();
			try {
				Map<String, List<Opcion>> mapaOpciones =crearOpcionesPregunta(cantidadOpciones);
				List<Opcion> listaOpciones =mapaOpciones.get("lista");
				List<Opcion> listaCorrecta =mapaOpciones.get("correcta");
				Opcion opcionCorrecta =listaCorrecta.get(0);
				PreguntaCerrada pregunta = new PreguntaCerrada(explicacion, listaOpciones,opcionCorrecta);
				quiz.addPregunta(pregunta);
			
			}
			catch (IllegalArgumentException e) {
				System.out.print("Error: Numero de opciones mayor a 4");
				menu();
			}
			
			
		}
		
		
		return quiz;
		
    
    }
    
    public static Actividad crearExamen() {
    	System.out.println("Ingrese la descripción: ");
    	String descripcion = scanner.nextLine();
    	System.out.println("Ingrese el objetivo: ");
    	String objetivo = scanner.nextLine();
    	System.out.println("Ingrese el nivel de dificultad: ");
    	String nivelDificultad = scanner.nextLine();
    	System.out.println("Ingrese la duración esperada en minutos: ");
    	int duracion = scanner.nextInt();
    	scanner.nextLine();
    	System.out.println("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
    	String fechaLimStr = scanner.nextLine();
    	System.out.println("Es obligatoria (1:Si | 0:NO): ");
    	int obligatoria = scanner.nextInt();
    	scanner.nextLine();
    	boolean esObligatoria = opcional(obligatoria);
    	System.out.println("Ingrese la cantidad de preguntas: ");
    	int cantidadPreguntas = scanner.nextInt();
    	List<PreguntaAbierta> preguntas = new ArrayList<>();
    	for(int i =0;i<cantidadPreguntas;i++) {
    		System.out.println("Escriba la pregunta abierta: ");
        	String cuerpoPregunta = scanner.nextLine();
        	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
        	preguntas.add(preguntaAbierta);
    	}
    	Date fechaLim = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaLim = sdf.parse(fechaLimStr);
        } catch (ParseException e) {
            System.out.println("Formato de fecha incorrecto.");
            return null; 
        }
    	Examen examen = new Examen(descripcion, objetivo, nivelDificultad, duracion, fechaLim, esObligatoria, preguntas);
    	return examen;
    }
    
    public static Actividad crearEncuesta() {
    	System.out.println("Ingrese la descripción: ");
    	String descripcion = scanner.nextLine();
    	System.out.println("Ingrese el objetivo: ");
    	String objetivo = scanner.nextLine();
    	System.out.println("Ingrese el nivel de dificultad: ");
    	String nivelDificultad = scanner.nextLine();
    	System.out.println("Ingrese la duración esperada en minutos: ");
    	int duracion = scanner.nextInt();
    	scanner.nextLine();
    	System.out.println("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
    	String fechaLimStr = scanner.nextLine();
    	System.out.println("Es obligatoria (1:Si | 0:NO): ");
    	int obligatoria = scanner.nextInt();
    	scanner.nextLine();
    	boolean esObligatoria = opcional(obligatoria);
    	System.out.println("Ingrese la cantidad de preguntas: ");
    	int cantidadPreguntas = scanner.nextInt();
    	List<PreguntaAbierta> preguntas = new ArrayList<>();
    	for(int i =0;i<cantidadPreguntas;i++) {
    		System.out.println("Escriba la pregunta abierta: ");
        	String cuerpoPregunta = scanner.nextLine();
        	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
        	preguntas.add(preguntaAbierta);
    	
    	}
    	Date fechaLim = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fechaLim = sdf.parse(fechaLimStr);
        } catch (ParseException e) {
            System.out.println("Formato de fecha incorrecto.");
            return null; 
        }
    	Encuesta encuesta = new Encuesta(descripcion, objetivo, nivelDificultad, duracion, fechaLim, esObligatoria, preguntas);
    	return encuesta;
    }
    
    public static Map<String, List<Opcion>> crearOpcionesPregunta(int cantidadOpciones) { 
    	if (cantidadOpciones >5 ) {
    		throw new IllegalArgumentException();
    		
    	}
    	else {
    		
    		Map<String, List<Opcion>> mapaRetorno= new  HashMap<>();
    		List<Opcion> listaOpciones= new ArrayList<Opcion>();
    		for(int i= 1;i<=cantidadOpciones;i++) {
    			System.out.print("De la explicación de la opción");
    			String explicacionOpcion = scanner.nextLine();
    			Opcion opcion = new Opcion(explicacionOpcion);
    			System.out.print("Es una opcion correcta ?(1:Si | 0:NO)");
    			int correcta = scanner.nextInt();
    			if(correcta == 1) {
    				Opcion opcionCorrecta=opcion;
    				List<Opcion> opcionCorrectaLista= new ArrayList<Opcion>();
    				opcionCorrectaLista.add(opcionCorrecta);
    				mapaRetorno.put("correcta", listaOpciones);
    			}
    			listaOpciones.add(opcion);
    			
    		}
    		mapaRetorno.put("lista", listaOpciones);
    		
    		
    		
    		return mapaRetorno ;
    	}
    	
    	
    	
    }
	public static boolean opcional(int opcion) {
		if (opcion == 1) {
			return true;
		}
		return false;
	}
	public static Date convertirFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaConvertida = formato.parse(fecha);
            return fechaConvertida;
        } catch (ParseException e) {
            System.out.println("Error al convertir la fecha: " + e.getMessage());
            return null;
        }
		
		
	}
//prueba commit 3

}
