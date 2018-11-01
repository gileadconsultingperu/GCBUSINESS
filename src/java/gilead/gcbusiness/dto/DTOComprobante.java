/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Luis
 */
public class DTOComprobante implements Serializable {

    private Integer idventa;
    private Timestamp fecha_emision;
    private String codigoSunatcomprobante;
    private String abreviaturacomprobante;
    private String serie;
    private Integer correlativoserie;
    private String numerodocumentocliente;
    private String nombrecliente;
    private String codigoSunatMoneda;
    private Double total_venta;

    public DTOComprobante() {
    }

    public DTOComprobante(Integer idventa, Timestamp fecha_emision, String codigoSunatcomprobante, String abreviaturacomprobante, String serie, Integer correlativoserie, String numerodocumentocliente, String nombrecliente, String codigoSunatMoneda, Double total_venta) {
        this.idventa = idventa;
        this.fecha_emision = fecha_emision;
        this.codigoSunatcomprobante = codigoSunatcomprobante;
        this.abreviaturacomprobante = abreviaturacomprobante;
        this.serie = serie;
        this.correlativoserie = correlativoserie;
        this.numerodocumentocliente = numerodocumentocliente;
        this.nombrecliente = nombrecliente;
        this.codigoSunatMoneda = codigoSunatMoneda;
        this.total_venta = total_venta;
    }

    /**
     * @return the idventa
     */
    public Integer getIdventa() {
        return idventa;
    }

    /**
     * @param idventa the idventa to set
     */
    public void setIdventa(Integer idventa) {
        this.idventa = idventa;
    }

    /**
     * @return the fecha_emision
     */
    public Timestamp getFecha_emision() {
        return fecha_emision;
    }

    /**
     * @param fecha_emision the fecha_emision to set
     */
    public void setFecha_emision(Timestamp fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    /**
     * @return the codigoSunatcomprobante
     */
    public String getCodigoSunatcomprobante() {
        return codigoSunatcomprobante;
    }

    /**
     * @param codigoSunatcomprobante the codigoSunatcomprobante to set
     */
    public void setCodigoSunatcomprobante(String codigoSunatcomprobante) {
        this.codigoSunatcomprobante = codigoSunatcomprobante;
    }

    /**
     * @return the abreviaturacomprobante
     */
    public String getAbreviaturacomprobante() {
        return abreviaturacomprobante;
    }

    /**
     * @param abreviaturacomprobante the abreviaturacomprobante to set
     */
    public void setAbreviaturacomprobante(String abreviaturacomprobante) {
        this.abreviaturacomprobante = abreviaturacomprobante;
    }

    /**
     * @return the serie
     */
    public String getSerie() {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(String serie) {
        this.serie = serie;
    }

    /**
     * @return the correlativoserie
     */
    public Integer getCorrelativoserie() {
        return correlativoserie;
    }

    /**
     * @param correlativoserie the correlativoserie to set
     */
    public void setCorrelativoserie(Integer correlativoserie) {
        this.correlativoserie = correlativoserie;
    }

    /**
     * @return the numerodocumentocliente
     */
    public String getNumerodocumentocliente() {
        return numerodocumentocliente;
    }

    /**
     * @param numerodocumentocliente the numerodocumentocliente to set
     */
    public void setNumerodocumentocliente(String numerodocumentocliente) {
        this.numerodocumentocliente = numerodocumentocliente;
    }

    /**
     * @return the nombrecliente
     */
    public String getNombrecliente() {
        return nombrecliente;
    }

    /**
     * @param nombrecliente the nombrecliente to set
     */
    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    /**
     * @return the codigoSunatMoneda
     */
    public String getCodigoSunatMoneda() {
        return codigoSunatMoneda;
    }

    /**
     * @param codigoSunatMoneda the codigoSunatMoneda to set
     */
    public void setCodigoSunatMoneda(String codigoSunatMoneda) {
        this.codigoSunatMoneda = codigoSunatMoneda;
    }

    /**
     * @return the total_venta
     */
    public Double getTotal_venta() {
        return total_venta;
    }

    /**
     * @param total_venta the total_venta to set
     */
    public void setTotal_venta(Double total_venta) {
        this.total_venta = total_venta;
    }

    
}
