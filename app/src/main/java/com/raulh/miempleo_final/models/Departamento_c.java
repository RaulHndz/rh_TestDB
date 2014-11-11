package com.raulh.miempleo_final.models;

/**
 * Created by raulh on 05/11/2014.
 */
public class Departamento_c {

    private long iddep;
    private String nombredep;

    public Departamento_c(long iddep, String nombredep) {

        this.iddep = iddep;
        this.nombredep = nombredep;

    }


    public Departamento_c() {
    }

    public long getIddep() {
        return iddep;
    }

    public void setIddep(long iddep) {
        this.iddep = iddep;
    }

    public String getNombredep() {
        return nombredep;
    }

    public void setNombredep(String nombredep) {
        this.nombredep = nombredep;
    }

    @Override
    public String toString() {
        return this.iddep + " | " + this.nombredep;
    }

}
