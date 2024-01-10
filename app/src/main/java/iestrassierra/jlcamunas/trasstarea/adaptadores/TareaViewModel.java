package iestrassierra.jlcamunas.trasstarea.adaptadores;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TareaViewModel extends ViewModel {

    //Usamos MutableLiveData para evitar problemas de ciclo de vida de los fragmentos
    private final MutableLiveData<String> titulo = new MutableLiveData<>();
    private final MutableLiveData<String> fechaCreacion = new MutableLiveData<>();
    private final MutableLiveData<String> fechaObjetivo = new MutableLiveData<>();
    private final MutableLiveData<Integer> progreso = new MutableLiveData<>();
    private final MutableLiveData<Boolean> prioritaria = new MutableLiveData<>();
    private final MutableLiveData<String> descripcion = new MutableLiveData<>();
    private final MutableLiveData<String> URL_doc = new MutableLiveData<>();
    private final MutableLiveData<String> URL_img = new MutableLiveData<>();
    private final MutableLiveData<String> URL_vid = new MutableLiveData<>();
    private final MutableLiveData<String> URL_aud = new MutableLiveData<>();

    public MutableLiveData<String> getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo.setValue(titulo);
    }

    public MutableLiveData<String> getURL_doc() {
        return URL_doc;
    }

    public void setURL_doc(String urlDoc) {
        this.URL_doc.setValue(urlDoc);
    }

    public MutableLiveData<String> getURL_img() {
        return URL_img;
    }

    public void setURL_img(String urlImg) {
        this.URL_doc.setValue(urlImg);
    }

    public MutableLiveData<String> getURL_vid() {
        return URL_vid;
    }

    public void setURL_vid(String urlVid) {
        this.URL_vid.setValue(urlVid);
    }

    public MutableLiveData<String> getURL_aud() {
        return URL_aud;
    }

    public void setURL_aud(String urlAud) {
        this.URL_aud.setValue(urlAud);
    }

    public MutableLiveData<String> getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion.setValue(fechaCreacion);
    }

    public MutableLiveData<String> getFechaObjetivo() {
        return fechaObjetivo;
    }

    public void setFechaObjetivo(String fechaObjetivo) {
        this.fechaObjetivo.setValue(fechaObjetivo);
    }

    public MutableLiveData<Integer> getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso.setValue(progreso);
    }

    public MutableLiveData<Boolean> isPrioritaria() {
        return prioritaria;
    }

    public void setPrioritaria(boolean prioritaria) {
        this.prioritaria.setValue(prioritaria);
    }

    public MutableLiveData<String> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.setValue(descripcion);
    }
}