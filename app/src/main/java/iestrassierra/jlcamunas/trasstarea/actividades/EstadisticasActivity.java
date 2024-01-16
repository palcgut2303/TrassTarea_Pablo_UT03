package iestrassierra.jlcamunas.trasstarea.actividades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import iestrassierra.jlcamunas.trasstarea.R;
import iestrassierra.jlcamunas.trasstarea.adaptadores.TareaDAORepositorio;
import iestrassierra.jlcamunas.trasstarea.modelo.Tarea;

public class EstadisticasActivity extends AppCompatActivity {

    private TareaDAORepositorio tareaDAORepositorio;

    private TextView numTareas,promedioProgeso,promedioFechaCreacion,buscarTarea,getNombreTarea;

    private Button btnBuscar,btnCerrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisiticas_tarea);

        numTareas = findViewById(R.id.etxtNumTar);
        promedioProgeso = findViewById(R.id.etxtPromedioPr);
        promedioFechaCreacion = findViewById(R.id.etxtPromedioFech);
        buscarTarea = findViewById(R.id.etxtTareaNombre);
        getNombreTarea = findViewById(R.id.etxtgetNombreTarea);
        btnBuscar = findViewById(R.id.btBuscar);
        btnCerrar = findViewById(R.id.btnCerraar);
        buscarTarea = findViewById(R.id.etxtTareaNombre);

        //TareaDAO tareaDAO = new TareaDAO();
        tareaDAORepositorio = new ViewModelProvider(this).get(TareaDAORepositorio.class);

        // Observar los cambios en el número total de tareas
        tareaDAORepositorio.obtenerNumeroTotalDeTareas().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer totalTareas) {
                // Actualizar el TextView con el número total de tareas
                numTareas.setText("Total de tareas: " + totalTareas);
            }
        });

        tareaDAORepositorio.calcularPromedioDeProgreso().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                promedioProgeso.setText(aDouble.toString());
            }
        });

        tareaDAORepositorio.calcularPromedioDeFecha().observe(this, new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                promedioFechaCreacion.setText(aDouble.toString());
            }
        });

        btnBuscar.setOnClickListener(v-> {
            String nombre = getNombreTarea.getText().toString();
            tareaDAORepositorio.buscarTareasPorNombre(nombre).observe(this, new Observer<List<Tarea>>() {
                @Override
                public void onChanged(List<Tarea> tareas) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (Tarea tarea : tareas) {
                        String fechaObjetivo = obtenerFormatoFecha(tarea.getFechaObjetivo());
                        stringBuilder.append("-Nombre: "+tarea.getTitulo()+ ", Progreso: " + tarea.getProgreso()+"%, Fecha Objetivo: " + fechaObjetivo+".").append("\n");  // Suponiendo que tienes un método getTitulo en tu entidad Tarea
                    }

                    buscarTarea.setText(stringBuilder);
                }
            });
      });

        btnCerrar.setOnClickListener(V->{
            finish();
        });

    }

    public static String obtenerFormatoFecha(Date fecha) {
        // Crear un objeto SimpleDateFormat con el formato deseado
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        // Formatear la fecha y devolver la cadena resultante
        return formato.format(fecha);
    }

}
