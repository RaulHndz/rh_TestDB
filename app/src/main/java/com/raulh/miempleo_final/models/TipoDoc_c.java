package com.raulh.miempleo_final.models;

/**
 * Created by raulh on 05/11/2014.
 */
public class TipoDoc_c {

    private long idtipo;
    private String nombretipo;

    public TipoDoc_c() {
    }

    public TipoDoc_c(long idtipo, String nombretipo) {
        this.idtipo = idtipo;
        this.nombretipo = nombretipo;
    }

    public long getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(long idtipo) {
        this.idtipo = idtipo;
    }

    public String getNombretipo() {
        return nombretipo;
    }

    public void setNombretipo(String nombretipo) {
        this.nombretipo = nombretipo;
    }

    @Override
    public String toString() {
        return this.idtipo + " | " + this.nombretipo;
    }

}
