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
public class DTOCotizacion implements Serializable {

    private Integer idcotizacion;
    private Integer idsucursal;
    private Integer idalmacen;
    private String almacen;
    private Timestamp fecha_emision;
    private Integer idmoneda;
    private String codigoSunatMoneda;
    private String tipo_descuentoglobal;
    private Double monto_descuentoglobal;
    private Double pcto_descuentoglobal;
    private Double total_gravada;
    private Double total_inafecta;
    private Double total_exonerada;
    private Double total_gratuita;
    private Double total_impuesto;
    private Double total_igv;
    private Double total_isc;
    private Double total_otrotributo;
    private Double total_valorventa;
    private Double total_precioventa;
    private Double total_descuento;
    private Double total_otrocargo;
    private Double total_venta;
    private String estado;

    public DTOCotizacion() {
    }

    public DTOCotizacion(Integer idcotizacion, Integer idsucursal, Integer idalmacen, String almacen, Timestamp fecha_emision, Integer idmoneda, String codigoSunatMoneda, String tipo_descuentoglobal, Double monto_descuentoglobal, Double pcto_descuentoglobal, Double total_gravada, Double total_inafecta, Double total_exonerada, Double total_gratuita, Double total_impuesto, Double total_igv, Double total_isc, Double total_otrotributo, Double total_valorventa, Double total_precioventa, Double total_descuento, Double total_otrocargo, Double total_venta, String estado) {
        this.idcotizacion = idcotizacion;
        this.idsucursal = idsucursal;
        this.idalmacen = idalmacen;
        this.almacen = almacen;
        this.fecha_emision = fecha_emision;
        this.idmoneda = idmoneda;
        this.codigoSunatMoneda = codigoSunatMoneda;
        this.tipo_descuentoglobal = tipo_descuentoglobal;
        this.monto_descuentoglobal = monto_descuentoglobal;
        this.pcto_descuentoglobal = pcto_descuentoglobal;
        this.total_gravada = total_gravada;
        this.total_inafecta = total_inafecta;
        this.total_exonerada = total_exonerada;
        this.total_gratuita = total_gratuita;
        this.total_impuesto = total_impuesto;
        this.total_igv = total_igv;
        this.total_isc = total_isc;
        this.total_otrotributo = total_otrotributo;
        this.total_valorventa = total_valorventa;
        this.total_precioventa = total_precioventa;
        this.total_descuento = total_descuento;
        this.total_otrocargo = total_otrocargo;
        this.total_venta = total_venta;
        this.estado = estado;
    }

    /**
     * @return the idcotizacion
     */
    public Integer getIdcotizacion() {
        return idcotizacion;
    }

    /**
     * @param idcotizacion the idcotizacion to set
     */
    public void setIdcotizacion(Integer idcotizacion) {
        this.idcotizacion = idcotizacion;
    }

    /**
     * @return the idsucursal
     */
    public Integer getIdsucursal() {
        return idsucursal;
    }

    /**
     * @param idsucursal the idsucursal to set
     */
    public void setIdsucursal(Integer idsucursal) {
        this.idsucursal = idsucursal;
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
     * @return the almacen
     */
    public String getAlmacen() {
        return almacen;
    }

    /**
     * @param almacen the almacen to set
     */
    public void setAlmacen(String almacen) {
        this.almacen = almacen;
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
     * @return the idmoneda
     */
    public Integer getIdmoneda() {
        return idmoneda;
    }

    /**
     * @param idmoneda the idmoneda to set
     */
    public void setIdmoneda(Integer idmoneda) {
        this.idmoneda = idmoneda;
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
     * @return the tipo_descuentoglobal
     */
    public String getTipo_descuentoglobal() {
        return tipo_descuentoglobal;
    }

    /**
     * @param tipo_descuentoglobal the tipo_descuentoglobal to set
     */
    public void setTipo_descuentoglobal(String tipo_descuentoglobal) {
        this.tipo_descuentoglobal = tipo_descuentoglobal;
    }

    /**
     * @return the monto_descuentoglobal
     */
    public Double getMonto_descuentoglobal() {
        return monto_descuentoglobal;
    }

    /**
     * @param monto_descuentoglobal the monto_descuentoglobal to set
     */
    public void setMonto_descuentoglobal(Double monto_descuentoglobal) {
        this.monto_descuentoglobal = monto_descuentoglobal;
    }

    /**
     * @return the pcto_descuentoglobal
     */
    public Double getPcto_descuentoglobal() {
        return pcto_descuentoglobal;
    }

    /**
     * @param pcto_descuentoglobal the pcto_descuentoglobal to set
     */
    public void setPcto_descuentoglobal(Double pcto_descuentoglobal) {
        this.pcto_descuentoglobal = pcto_descuentoglobal;
    }

    /**
     * @return the total_gravada
     */
    public Double getTotal_gravada() {
        return total_gravada;
    }

    /**
     * @param total_gravada the total_gravada to set
     */
    public void setTotal_gravada(Double total_gravada) {
        this.total_gravada = total_gravada;
    }

    /**
     * @return the total_inafecta
     */
    public Double getTotal_inafecta() {
        return total_inafecta;
    }

    /**
     * @param total_inafecta the total_inafecta to set
     */
    public void setTotal_inafecta(Double total_inafecta) {
        this.total_inafecta = total_inafecta;
    }

    /**
     * @return the total_exonerada
     */
    public Double getTotal_exonerada() {
        return total_exonerada;
    }

    /**
     * @param total_exonerada the total_exonerada to set
     */
    public void setTotal_exonerada(Double total_exonerada) {
        this.total_exonerada = total_exonerada;
    }

    /**
     * @return the total_gratuita
     */
    public Double getTotal_gratuita() {
        return total_gratuita;
    }

    /**
     * @param total_gratuita the total_gratuita to set
     */
    public void setTotal_gratuita(Double total_gratuita) {
        this.total_gratuita = total_gratuita;
    }

    /**
     * @return the total_impuesto
     */
    public Double getTotal_impuesto() {
        return total_impuesto;
    }

    /**
     * @param total_impuesto the total_impuesto to set
     */
    public void setTotal_impuesto(Double total_impuesto) {
        this.total_impuesto = total_impuesto;
    }

    /**
     * @return the total_igv
     */
    public Double getTotal_igv() {
        return total_igv;
    }

    /**
     * @param total_igv the total_igv to set
     */
    public void setTotal_igv(Double total_igv) {
        this.total_igv = total_igv;
    }

    /**
     * @return the total_isc
     */
    public Double getTotal_isc() {
        return total_isc;
    }

    /**
     * @param total_isc the total_isc to set
     */
    public void setTotal_isc(Double total_isc) {
        this.total_isc = total_isc;
    }

    /**
     * @return the total_otrotributo
     */
    public Double getTotal_otrotributo() {
        return total_otrotributo;
    }

    /**
     * @param total_otrotributo the total_otrotributo to set
     */
    public void setTotal_otrotributo(Double total_otrotributo) {
        this.total_otrotributo = total_otrotributo;
    }

    /**
     * @return the total_valorventa
     */
    public Double getTotal_valorventa() {
        return total_valorventa;
    }

    /**
     * @param total_valorventa the total_valorventa to set
     */
    public void setTotal_valorventa(Double total_valorventa) {
        this.total_valorventa = total_valorventa;
    }

    /**
     * @return the total_precioventa
     */
    public Double getTotal_precioventa() {
        return total_precioventa;
    }

    /**
     * @param total_precioventa the total_precioventa to set
     */
    public void setTotal_precioventa(Double total_precioventa) {
        this.total_precioventa = total_precioventa;
    }

    /**
     * @return the total_descuento
     */
    public Double getTotal_descuento() {
        return total_descuento;
    }

    /**
     * @param total_descuento the total_descuento to set
     */
    public void setTotal_descuento(Double total_descuento) {
        this.total_descuento = total_descuento;
    }

    /**
     * @return the total_otrocargo
     */
    public Double getTotal_otrocargo() {
        return total_otrocargo;
    }

    /**
     * @param total_otrocargo the total_otrocargo to set
     */
    public void setTotal_otrocargo(Double total_otrocargo) {
        this.total_otrocargo = total_otrocargo;
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
