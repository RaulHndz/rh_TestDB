package com.raulh.miempleo_final.models;

/**
 * Created by raulh on 05/11/2014.
 */
public class Solicitante_c {


    private long idsoli;
    private String nombresolic;
    private String apellidosolic;
    private String fechanacsolic;
    private String telsolic;
    private String generosolic;
    private String idtipo;
    private String idmun;
    private String idnivel;


    public Solicitante_c() {
    }

    public Solicitante_c(long idsoli, String nombresolic, String apellidosolic, String fechanacsolic, String telsolic, String generosolic, String idtipo, String idmun, String idnivel) {
        this.idsoli = idsoli;
        this.nombresolic = nombresolic;
        this.apellidosolic = apellidosolic;
        this.fechanacsolic = fechanacsolic;
        this.telsolic = telsolic;
        this.generosolic = generosolic;
        this.idtipo = idtipo;
        this.idmun = idmun;
        this.idnivel = idnivel;

    }

    public long getIdsoli() {
        return idsoli;
    }

    public void setIdsoli(long idsoli) {
        this.idsoli = idsoli;
    }

    public String getNombresolic() {
        return nombresolic;
    }

    public void setNombresolic(String nombresolic) {
        this.nombresolic = nombresolic;
    }

    public String getApellidosolic() {
        return apellidosolic;
    }

    public void setApellidosolic(String apellidosolic) {
        this.apellidosolic = apellidosolic;
    }

    public String getFechanacsolic() {
        return fechanacsolic;
    }

    public void setFechanacsolic(String fechanacsolic) {
        this.fechanacsolic = fechanacsolic;
    }

    public String getTelsolic() {
        return telsolic;
    }

    public void setTelsolic(String telsolic) {
        this.telsolic = telsolic;
    }

    public String getGenerosolic() {
        return generosolic;
    }

    public void setGenerosolic(String generosolic) {
        this.generosolic = generosolic;
    }

    public String getIdtipo() {
        return idtipo;
    }

    public void setIdtipo(String idtipo) {
        this.idtipo = idtipo;
    }

    public String getIdmun() {
        return idmun;
    }

    public void setIdmun(String idmun) {
        this.idmun = idmun;
    }

    public String getIdnivel() {
        return idnivel;
    }

    public void setIdnivel(String idnivel) {
        this.idnivel = idnivel;
    }

    @Override
    public String toString() {
        return this.idsoli + " | " +
                this.nombresolic + " | " +
                this.apellidosolic + " | " +
                this.fechanacsolic + " | " +
                this.telsolic + " | " +
                this.generosolic + " | " +
                this.idtipo + " | " +
                this.idmun + " | " +
                this.idnivel;
    }

}
