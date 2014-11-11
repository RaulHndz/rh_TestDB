package com.raulh.miempleo_final.models;

/**
 * Created by raulh on 05/11/2014.
 */
public class Municipio_c {

    private long idmun;
    private String nombremun;
    private String iddep;


    public Municipio_c() {
    }


    public Municipio_c(long idmun, String nombremun, String iddep) {
        this.idmun = idmun;
        this.nombremun = nombremun;
        this.iddep = iddep;
    }

    public String getIddep() {
        return iddep;
    }

    public void setIddep(String iddep) {
        this.iddep = iddep;
    }

    public String getNombremun() {
        return nombremun;
    }

    public void setNombremun(String nombremun) {
        this.nombremun = nombremun;
    }

    public long getIdmun() {
        return idmun;
    }

    public void setIdmun(long idmun) {
        this.idmun = idmun;
    }

    @Override
    public String toString() {
        return this.idmun + " | " + this.nombremun + " | " + this.iddep;
    }

}
