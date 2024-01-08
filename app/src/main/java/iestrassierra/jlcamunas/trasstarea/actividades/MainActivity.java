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
       /* if(theme){
            setDayNight(1);
        }else{
            setDayNight(0);
        }*/

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

        String tamanoLetra = sharedPreferences.getString("fuente","Mediana");
        Resources resources = getResources();
        Configuration conf = resources.getConfiguration();
        DisplayMetrics display =resources.getDisplayMetrics();

        switch (tamanoLetra){
            case "1":
                conf.fontScale = 0.8f;
                break;
            case "2":
                conf.fontScale = 1.2f;
                break;
            default:
                conf.fontScale = 1.5f;
                break;
        }
        resources.updateConfiguration(conf,display);

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