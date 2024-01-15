package iestrassierra.jlcamunas.trasstarea.actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import iestrassierra.jlcamunas.trasstarea.R;

public class MainActivity extends AppCompatActivity {

    private boolean theme = true;
    private boolean esCreate = false;

    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getBoolean("tema",true);
        Button btEmpezar = findViewById(R.id.main_bt_empezar);
        btEmpezar.setOnClickListener(this::empezar);
        getTheme();
        establecerFuente();


    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getBoolean("tema",true);


        if(esCreate){
            recreate();
        }

        esCreate = true;

    }

    private void establecerFuente(){

      /*  String tamanoLetra = sharedPreferences.getString("fuente","Mediana");
        Resources resources = getResources();
        Configuration conf = resources.getConfiguration();
        DisplayMetrics display =resources.getDisplayMetrics();

        switch (tamanoLetra){
            case "Pequeña":
                conf.fontScale = 0.8f;
                break;
            case "Mediana":
                conf.fontScale = 1.2f;
                break;
            default:
                conf.fontScale = 1.5f;
                break;
        }
        resources.updateConfiguration(conf,display);*/
        String fontSize = sharedPreferences.getString("tamañoLetra", "Mediana");
        // float size = getResources().getConfiguration().fontScale;
        if(fontSize.equalsIgnoreCase("1")){
            ajustarTamanoLetraEnTodaLaApp(getResources(),0.8f);
        }else if (fontSize.equalsIgnoreCase("2") ){
            ajustarTamanoLetraEnTodaLaApp(getResources(),1.2f);
        }else if (fontSize.equalsIgnoreCase("3") ){
            ajustarTamanoLetraEnTodaLaApp(getResources(),1.5f);
        }


    }

    public static void ajustarTamanoLetraEnTodaLaApp(Resources resources, float nuevoTamano) {
        Configuration configuration = resources.getConfiguration();

        // Crear una nueva configuración basada en la configuración actual
        Configuration newConfig = new Configuration(configuration);

        // Modificar la escala de fuente en la nueva configuración
        newConfig.fontScale = nuevoTamano;

        // Aplicar la nueva configuración al recurso
        resources.updateConfiguration(newConfig, null);

        // Actualizar la densidad de píxeles en función de la nueva configuración
        DisplayMetrics metrics = resources.getDisplayMetrics();
        resources.updateConfiguration(newConfig, metrics);
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("tema",false)){
            theme.applyStyle(R.style.Theme_TrassTarea,true);
        }
        return theme;
    }

    private void empezar(View v){
        Intent aEmpezar = new Intent(this, ListadoTareasActivity.class);
        startActivity(aEmpezar);
    }


}