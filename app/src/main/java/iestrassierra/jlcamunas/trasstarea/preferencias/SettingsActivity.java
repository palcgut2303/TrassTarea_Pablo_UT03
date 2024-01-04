package iestrassierra.jlcamunas.trasstarea.preferencias;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import iestrassierra.jlcamunas.trasstarea.R;

public class SettingsActivity extends AppCompatActivity {
    private boolean theme = true;
    String tamañoLetraIndex = "";
    String tamañoLetra = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        theme = sharedPreferences.getBoolean("tema",true);

        if(theme){
            setDayNight(1);
        }else{
            setDayNight(0);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void setDayNight(int mode){
        if(mode==0){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //setTheme(R.style.AppTheme_Dark);
        }else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == android.R.id.home){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            tamañoLetraIndex = sharedPreferences.getString("tamañoLetra","Mediana");

            if(tamañoLetraIndex.equalsIgnoreCase("1")){
                    tamañoLetra = "Pequeña";
            }else if (tamañoLetraIndex.equalsIgnoreCase("2")){
                tamañoLetra = "Mediana";
            }else{
                tamañoLetra = "Grande";
            }



            Intent intentVolver = new Intent();
            intentVolver.putExtra("tamañoLetra",tamañoLetra);
            setResult(RESULT_OK, intentVolver);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}