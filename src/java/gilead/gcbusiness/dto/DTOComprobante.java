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
    private Integer idalmacen;
    private Timestamp fecha_emision;
    private Integer idtipocomprobante;
    private String codigoSunatcomprobante;
    private String abreviaturacomprobante;
    private Integer idserie;
    private String serie;
    private Integer correlativoserie;
    private String numerodocumentocliente;
    private String nombrecliente;
    private String codigoSunatMoneda;
    private Double total_venta;
    private String estado;

    public DTOComprobante() {
    }

    public DTOComprobante(Integer idventa, Integer idalmacen, Timestamp fecha_emision, Integer idtipocomprobante, String codigoSunatcomprobante, String abreviaturacomprobante, Integer idserie, String serie, Integer correlativoserie, String numerodocumentocliente, String nombrecliente, String codigoSunatMoneda, Double total_venta, String estado) {
        this.idventa = idventa;
        this.idalmacen = idalmacen;
        this.fecha_emision = fecha_emision;
        this.idtipocomprobante = idtipocomprobante;
        this.codigoSunatcomprobante = codigoSunatcomprobante;
        this.abreviaturacomprobante = abreviaturacomprobante;
        this.idserie = idserie;
        this.serie = serie;
        this.correlativoserie = correlativoserie;
        this.numerodocumentocliente = numerodocumentocliente;
        this.nombrecliente = nombrecliente;
        this.codigoSunatMoneda = codigoSunatMoneda;
        this.total_venta = total_venta;
        this.estado = estado;
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
     * @return the idalmacen
     */
    public Integer getIdalmacen() {
        return idalmacen;
    }

    /**
     * @param idalmacen the idalmacen to set
     */
    public void setIdalmacen(Integer idalmacen) {
        this.idalmacen = idalmacen;
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
     * @return the idtipocomprobante
     */
    public Integer getIdtipocomprobante() {
        return idtipocomprobante;
    }

    /**
     * @param idtipocomprobante the idtipocomprobante to set
     */
    public void setIdtipocomprobante(Integer idtipocomprobante) {
        this.idtipocomprobante = idtipocomprobante;
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
     * @return the idserie
     */
    public Integer getIdserie() {
        return idserie;
    }

    /**
     * @param idserie the idserie to set
     */
    public void setIdserie(Integer idserie) {
        this.idserie = idserie;
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

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}
