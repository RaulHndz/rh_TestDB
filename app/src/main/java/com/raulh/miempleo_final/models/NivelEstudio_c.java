package com.raulh.miempleo_final.models;

/**
 * Created by raulh on 05/11/2014.
 */
public class NivelEstudio_c {

    private long idnivel;
    private String nombrenivel;


    public NivelEstudio_c() {
    }

    public NivelEstudio_c(long idnivel, String nombrenivel) {
        this.idnivel = idnivel;
        this.nombrenivel = nombrenivel;
    }

    public long getIdnivel() {
        return idnivel;
    }

    public void setIdnivel(long idnivel) {
        this.idnivel = idnivel;
    }

    public String getNombrenivel() {
        return nombrenivel;
    }

    public void setNombrenivel(String nombrenivel) {
        this.nombrenivel = nombrenivel;
    }

    @Override
    public String toString() {
        return this.idnivel + " | " + this.nombrenivel;
    }

}
