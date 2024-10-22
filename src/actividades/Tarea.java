package actividades;

import java.util.Date;
import java.util.List;

public class Tarea extends Actividad {

    public Tarea(String descripcion, String objetivo, String nivelDificultad, double duracionFinal,
                 Date fechaLim, boolean obligatoria, List<Actividad> actividadesPrerrequisito, List<Actividad> actividadesOpcionales) {
        super("Tarea", descripcion, objetivo, nivelDificultad, duracionFinal, fechaLim, obligatoria);
        this.setActividadesPrerrequisito(actividadesPrerrequisito);
        this.setActividadesOpcionales(actividadesOpcionales);
    }

	@Override
	public void menu() {
		// TODO Auto-generated method stub
		
	}
}