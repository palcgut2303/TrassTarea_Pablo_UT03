package iestrassierra.jlcamunas.trasstarea.adaptadores;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import iestrassierra.jlcamunas.trasstarea.DAO.TareaDAO;
import iestrassierra.jlcamunas.trasstarea.basededatos.ControladorBaseDatos;
import iestrassierra.jlcamunas.trasstarea.modelo.Tarea;

public class TareaDAOViewModel extends ViewModel {

    private TareaDAO tareaDao;



    public TareaDAOViewModel() {
        this.tareaDao = ControladorBaseDatos
                .getInstance(new Application())
                .tareaDAO();
    }


    public LiveData<Integer> obtenerNumeroTotalDeTareas() {
        return tareaDao.numeroTareas();
    }

    public LiveData<Double> calcularPromedioDeProgreso(){
        return tareaDao.calcularPromedioDeProgreso();
    }
    public LiveData<Double> calcularPromedioDeFecha(){
        return tareaDao.calcularPromedioDeFecha();
    }

    public LiveData<List<Tarea>> buscarTareasPorNombre(String cadena){
        return tareaDao.buscarTareasPorNombre(cadena);
    }
}
