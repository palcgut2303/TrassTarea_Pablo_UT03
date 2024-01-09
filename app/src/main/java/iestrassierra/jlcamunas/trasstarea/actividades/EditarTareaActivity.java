package iestrassierra.jlcamunas.trasstarea.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Objects;

import iestrassierra.jlcamunas.trasstarea.R;
import iestrassierra.jlcamunas.trasstarea.modelo.Tarea;
import iestrassierra.jlcamunas.trasstarea.fragmentos.FragmentoUno;
import iestrassierra.jlcamunas.trasstarea.fragmentos.FragmentoDos;
import iestrassierra.jlcamunas.trasstarea.adaptadores.TareaViewModel;

public class EditarTareaActivity extends AppCompatActivity implements
        //Interfaces de comunicación con los fragmentos
        FragmentoUno.ComunicacionPrimerFragmento,
        FragmentoDos.ComunicacionSegundoFragmento{
    private Tarea tareaEditable;
    private TareaViewModel tareaViewModel;
    private String titulo, descripcion;
    private String fechaCreacion, fechaObjetivo;
    private Integer progreso;
    private String rutaArchivo;
    private Boolean prioritaria;
    private FragmentManager fragmentManager;
    private final Fragment fragmento1 = new FragmentoUno();
    private final Fragment fragmento2 = new FragmentoDos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);

        //Recibimos la tarea que va a ser editada
        Bundle bundle = getIntent().getExtras();
        try {
            if (bundle != null) {
                this.tareaEditable = bundle.getParcelable("TareaEditable");
            }
        }catch (NullPointerException e){
            Log.e("Bundle recibido nulo", e.toString());
        }

        //Instanciamos el ViewModel
        tareaViewModel = new ViewModelProvider(this).get(TareaViewModel.class);

        //Instanciamos el gestor de fragmentos
        fragmentManager = getSupportFragmentManager();

        //Si hay estado guardado
        if (savedInstanceState != null) {
            // Recuperar el ID o información del fragmento
            int fragmentId = savedInstanceState.getInt("fragmentoId", -1);

            if (fragmentId != -1) {
                // Usar el ID o información para encontrar y restaurar el fragmento
                cambiarFragmento(Objects.requireNonNull(fragmentManager.findFragmentById(fragmentId)));
            }else{
                //Si no tenemos ID de fragmento cargado, cargamos el primer fragmento
                cambiarFragmento(fragmento1);
            }
        }else{
            //Si no hay estado guardado, cargamos el primer fragmento
            cambiarFragmento(fragmento1);
            //Escribimos valores en el ViewModel
            tareaViewModel.setTitulo(tareaEditable.getTitulo());
            tareaViewModel.setFechaCreacion(tareaEditable.getFechaCreacion());
            tareaViewModel.setFechaObjetivo(tareaEditable.getFechaObjetivo());
            tareaViewModel.setProgreso(tareaEditable.getProgreso());
            tareaViewModel.setPrioritaria(tareaEditable.isPrioritaria());
            tareaViewModel.setDescripcion(tareaEditable.getDescripcion());

            tareaViewModel.setRutaArchivo(tareaEditable.getRutaArchivo());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        int fragmentID = Objects.requireNonNull(getSupportFragmentManager().
                findFragmentById(R.id.contenedor_frag)).getId();
        outState.putInt("fragmentoId", fragmentID);
    }

    //Implementamos los métodos de la interfaz de comunicación con el primer fragmento
    @Override
    public void onBotonSiguienteClicked() {

        //Leemos los valores del formulario del fragmento 1
        titulo = tareaViewModel.getTitulo().getValue();
        fechaCreacion = tareaViewModel.getFechaCreacion().getValue();
        fechaObjetivo = tareaViewModel.getFechaObjetivo().getValue();
        progreso = tareaViewModel.getProgreso().getValue();
        prioritaria = tareaViewModel.isPrioritaria().getValue();

        //Cambiamos el fragmento
        cambiarFragmento(fragmento2);
    }

    @Override
    public void onBotonCancelarClicked() {
        Intent aListado = new Intent();
        //Indicamos en el resultado que ha sido cancelada la actividad
        setResult(RESULT_CANCELED, aListado);
        //Volvemos a la actividad Listado
        finish();
    }

    //Implementamos los métodos de la interfaz de comunicación con el segundo fragmento
    @Override
    public void onBotonGuardarClicked() {
        //Leemos los valores del formulario del fragmento 2
        descripcion = tareaViewModel.getDescripcion().getValue();
        rutaArchivo = tareaViewModel.getRutaArchivo().getValue();
        //Creamos un nuevo objeto tarea con los campos editados
        Tarea tareaEditada = new Tarea(titulo, fechaCreacion,fechaObjetivo, progreso,prioritaria, descripcion,rutaArchivo);

        //Creamos un intent de vuelta para la actividad Listado
        Intent aListado = new Intent();
        //Creamos un Bundle para introducir la tarea editada
        Bundle bundle = new Bundle();
        bundle.putParcelable("TareaEditada", tareaEditada);
        aListado.putExtras(bundle);
        //Indicamos que el resultado ha sido OK
        setResult(RESULT_OK, aListado);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        boolean valorSD = sharedPreferences.getBoolean("sd", false);

        if(valorSD){
            escribirSD(rutaArchivo, tareaEditada.getTitulo());
        }else{
            escribirInterno(rutaArchivo,tareaEditada.getTitulo());
        }
        //Volvemos a la actividad Listado
        finish();
    }


    private void escribirInterno(String nombreArchivo,String tituloTarea) {
        OutputStreamWriter escritor;
        if(nombreArchivo.length() <= 0){
            Toast.makeText(this, "No se ha encontrado el archivo", Toast.LENGTH_SHORT).show();
        }else{
            try {
                // Obtén el directorio de almacenamiento interno específico para tu aplicación
                File directorioCarpeta = new File(getFilesDir(), tituloTarea);

                // Asegúrate de que la carpeta exista o créala si no existe
                if (!directorioCarpeta.exists()) {
                    directorioCarpeta.mkdirs();
                }
                File archivo = new File(directorioCarpeta, nombreArchivo);
                //String nombreRuta = "archivos_adjuntos" + File.separator + nombreArchivo;
                escritor = new OutputStreamWriter(new FileOutputStream(archivo));
                escritor.close();
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }

    }

    /*private String leerInterno(String tituloTarea, String nombreArchivo) {
        InputStreamReader lector;
        StringBuilder contenido = new StringBuilder();

        try {
            // Obtén el directorio de almacenamiento interno específico para tu aplicación
            File directorioCarpeta = new File(getFilesDir(), nombreCarpeta);

            // Concatena el nombre del archivo a la ruta de la carpeta
            File archivo = new File(directorioCarpeta, nombreArchivo);

            // Abre el archivo para lectura
            lector = new InputStreamReader(new FileInputStream(archivo));

            // Lee el contenido del archivo
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = lector.read(buffer)) != -1) {
                contenido.append(buffer, 0, bytesRead);
            }

            // Cierra el lector
            lector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contenido.toString();
    }*/

    public void escribirSD(String nombreArchivo,String tituloTarea){



        if(nombreArchivo.length() <= 0){
            Toast.makeText(this, "No se ha encontrado el archivo", Toast.LENGTH_SHORT).show();
        }else{

           /* try {
                File directorioSD = new File(Environment.getExternalStorageDirectory(), tituloTarea);
                if (!directorioSD.exists()) {
                    if (!directorioSD.mkdirs()) {
                        // No se pudo crear el directorio, manejar el error según sea necesario
                        Toast.makeText(this, "Error al crear el directorio", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                File archivoSD = new File(directorioSD, nombreArchivo);

                FileOutputStream outputStream = new FileOutputStream(archivoSD);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                writer.write("Archivo de la tarea: " + tituloTarea);
                writer.close();
                Toast.makeText(this, "OK SD", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al escribir en la tarjeta SD", Toast.LENGTH_LONG).show();
            }

            */
            File file = new File(this.getExternalFilesDir(null), nombreArchivo);


            OutputStreamWriter osw = null;
            try {
                osw = new OutputStreamWriter(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                osw.write("Archivo de la tarea: " + tituloTarea);
                osw.flush();
                osw.close();
            } catch (IOException | NullPointerException e) {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "OK SD", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBotonVolverClicked() {
        //Leemos los valores del formulario del fragmento 2
        descripcion = tareaViewModel.getDescripcion().getValue();

        //Cambiamos el fragmento2 por el 1
        cambiarFragmento(fragmento1);
    }

    public void cambiarFragmento(Fragment fragment){
        if (!fragment.isAdded()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.contenedor_frag, fragment)
                    .commit();
        }
    }
}