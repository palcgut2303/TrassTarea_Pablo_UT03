package iestrassierra.jlcamunas.trasstarea.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import iestrassierra.jlcamunas.trasstarea.modelo.Tarea;

@Dao
public interface TareaDAO {

    @Insert
    void insertAll(Tarea... tarea);

    //Anotación que permite realizar el borrado de una tarea
    @Delete
    void delete(Tarea tarea);

    @Query("SELECT * FROM tarea")
        //En este caso haremos que esta consulta se regenere cada vez que se produzcan cambios
        //en la base de datos mediante un objeto LiveData.
    LiveData<List<Tarea>> getAll();



}
