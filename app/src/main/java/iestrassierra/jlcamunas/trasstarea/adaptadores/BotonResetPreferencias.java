package iestrassierra.jlcamunas.trasstarea.adaptadores;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

public class BotonResetPreferencias extends Preference {
    public BotonResetPreferencias(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onClick() {
        super.onClick();
        // Lógica a ejecutar cuando se hace clic en el botón
        onBotonClic();
    }

    // Método llamado cuando se hace clic en el botón
    private void onBotonClic() {
        // Lógica a ejecutar al hacer clic en el botón
        Toast.makeText(getContext(), "Botón Reset Clicado", Toast.LENGTH_SHORT).show();
    }

}
