package iestrassierra.jlcamunas.trasstarea.actividades;

import static android.app.ProgressDialog.show;

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
import android.os.Environment;
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


public class CrearTareaActivity extends AppCompatActivity implements
        //Interfaces de comunicación con los fragmentos
        FragmentoUno.ComunicacionPrimerFragmento,
        FragmentoDos.ComunicacionSegundoFragmento {
    private TareaViewModel tareaViewModel;
    private String titulo, descripcion;
    private String fechaCreacion, fechaObjetivo;
    private Integer progreso;
    private Boolean prioritaria = false;
    private String URL_doc;
    private String URL_img;
    private String URL_aud;
    private String URL_vid;
    private FragmentManager fragmentManager;

    private final Fragment fragmento1 = new FragmentoUno();
    private final Fragment fragmento2 = new FragmentoDos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

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
        URL_doc = tareaViewModel.getURL_doc().getValue();
        URL_aud = tareaViewModel.getURL_aud().getValue();
        URL_img = tareaViewModel.getURL_img().getValue();
        URL_vid = tareaViewModel.getURL_vid().getValue();
        //Creamos la nueva tarea
        Tarea nuevaTarea = new Tarea(titulo, fechaCreacion,fechaObjetivo, progreso, prioritaria, descripcion,URL_doc,URL_aud,URL_img,URL_vid);
        //Creamos un intent de vuelta para la actividad Listado
        Intent aListado = new Intent();
        //Creamos un Bundle para introducir la nueva tarea
        Bundle bundle = new Bundle();
        bundle.putParcelable("NuevaTarea", nuevaTarea);
        aListado.putExtras(bundle);

        //Indicamos que el resultado ha sido OK
        setResult(RESULT_OK, aListado);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        boolean valorSD = sharedPreferences.getBoolean("sd", false);

       /* if(valorSD){
            escribirSD(rutaArchivo, nuevaTarea.getTitulo());
        }else{
            escribirInterno(rutaArchivo);
        }*/

        //Volvemos a la actividad Listado
        finish();
    }

    private void escribirInterno(String nombreArchivo) {
        OutputStreamWriter escritor;
        if(nombreArchivo.length() <= 0){
            Toast.makeText(this, "No se ha encontrado el archivo", Toast.LENGTH_SHORT).show();
        }else{
            try {
                escritor = new OutputStreamWriter(openFileOutput(nombreArchivo,
                        Context.MODE_PRIVATE));
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