package gilead.gcbusiness.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class DTOCompra implements Serializable{
    
    private Integer idcompra;
    private Integer idproveedor;
    private String numerodocumentoproveedor;
    private String nombreproveedor;
    private Integer idtipocomprobante;
    private String codigoSunatcomprobante;
    private String descripcioncomprobante;
    private String abreviaturacomprobante;
    private Integer idsucursal;
    private String descripcionsucursal;
    private Integer idalmacen;
    private String descripcionalmacen;
    private String serie;
    private Integer correlativoserie;
    private Timestamp fecha_emision;
    private Timestamp fecha_recepcion;
    private String flag_negociable;
    private Date fecha_vencimiento;
    private Integer idmoneda;
    private String codigoSunatMoneda;
    private String descripcionMoneda;
    private String simboloMoneda;
    private String formapago;
    private String estadopago;
    private String tipo_descuentoglobal;
    private Double monto_descuentoglobal;
    private Double pcto_descuentoglobal;
    private String flag_gravada;
    private Double total_gravada;
    private Double total_inafecta;
    private Double total_exonerada;
    private Double total_gratuita;
    private Double total_impuesto;
    private Double total_igv;
    private Double total_isc;
    private Double total_otrotributo;
    private Double total_valorcompra;
    private Double total_preciocompra;
    private Double total_descuento;
    private Double total_otrocargo;
    private Double total_anticipo;
    private Double total_compra;
    private Double importe_pago;
    private Double monto_efectivo;
    private Double monto_otro;
    private String referencia_otro;
    private Double cambio_pago; //vuelto
    private String estado;
    private String motivo_anulacion;

    public DTOCompra() {
    }

    public DTOCompra(Integer idcompra, Integer idproveedor, String numerodocumentoproveedor, String nombreproveedor, Integer idtipocomprobante, String codigoSunatcomprobante, String descripcioncomprobante, String abreviaturacomprobante, Integer idsucursal, String descripcionsucursal, Integer idalmacen, String descripcionalmacen, String serie, Integer correlativoserie, Timestamp fecha_emision, Timestamp fecha_recepcion, String flag_negociable, Date fecha_vencimiento, Integer idmoneda, String codigoSunatMoneda, String descripcionMoneda, String simboloMoneda, String formapago, String estadopago, String tipo_descuentoglobal, Double monto_descuentoglobal, Double pcto_descuentoglobal, String flag_gravada, Double total_gravada, Double total_inafecta, Double total_exonerada, Double total_gratuita, Double total_impuesto, Double total_igv, Double total_isc, Double total_otrotributo, Double total_valorcompra, Double total_preciocompra, Double total_descuento, Double total_otrocargo, Double total_anticipo, Double total_compra, Double importe_pago, Double monto_efectivo, Double monto_otro, String referencia_otro, Double cambio_pago, String estado, String motivo_anulacion) {
        this.idcompra = idcompra;
        this.idproveedor = idproveedor;
        this.numerodocumentoproveedor = numerodocumentoproveedor;
        this.nombreproveedor = nombreproveedor;
        this.idtipocomprobante = idtipocomprobante;
        this.codigoSunatcomprobante = codigoSunatcomprobante;
        this.descripcioncomprobante = descripcioncomprobante;
        this.abreviaturacomprobante = abreviaturacomprobante;
        this.idsucursal = idsucursal;
        this.descripcionsucursal = descripcionsucursal;
        this.idalmacen = idalmacen;
        this.descripcionalmacen = descripcionalmacen;
        this.serie = serie;
        this.correlativoserie = correlativoserie;
        this.fecha_emision = fecha_emision;
        this.fecha_recepcion = fecha_recepcion;
        this.flag_negociable = flag_negociable;
        this.fecha_vencimiento = fecha_vencimiento;
        this.idmoneda = idmoneda;
        this.codigoSunatMoneda = codigoSunatMoneda;
        this.descripcionMoneda = descripcionMoneda;
        this.simboloMoneda = simboloMoneda;
        this.formapago = formapago;
        this.estadopago = estadopago;
        this.tipo_descuentoglobal = tipo_descuentoglobal;
        this.monto_descuentoglobal = monto_descuentoglobal;
        this.pcto_descuentoglobal = pcto_descuentoglobal;
        this.flag_gravada = flag_gravada;
        this.total_gravada = total_gravada;
        this.total_inafecta = total_inafecta;
        this.total_exonerada = total_exonerada;
        this.total_gratuita = total_gratuita;
        this.total_impuesto = total_impuesto;
        this.total_igv = total_igv;
        this.total_isc = total_isc;
        this.total_otrotributo = total_otrotributo;
        this.total_valorcompra = total_valorcompra;
        this.total_preciocompra = total_preciocompra;
        this.total_descuento = total_descuento;
        this.total_otrocargo = total_otrocargo;
        this.total_anticipo = total_anticipo;
        this.total_compra = total_compra;
        this.importe_pago = importe_pago;
        this.monto_efectivo = monto_efectivo;
        this.monto_otro = monto_otro;
        this.referencia_otro = referencia_otro;
        this.cambio_pago = cambio_pago;
        this.estado = estado;
        this.motivo_anulacion = motivo_anulacion;
    }

    /**
     * @return the idcompra
     */
    public Integer getIdcompra() {
        return idcompra;
    }

    /**
     * @param idcompra the idcompra to set
     */
    public void setIdcompra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    /**
     * @return the idproveedor
     */
    public Integer getIdproveedor() {
        return idproveedor;
    }

    /**
     * @param idproveedor the idproveedor to set
     */
    public void setIdproveedor(Integer idproveedor) {
        this.idproveedor = idproveedor;
    }

    /**
     * @return the numerodocumentoproveedor
     */
    public String getNumerodocumentoproveedor() {
        return numerodocumentoproveedor;
    }

    /**
     * @param numerodocumentoproveedor the numerodocumentoproveedor to set
     */
    public void setNumerodocumentoproveedor(String numerodocumentoproveedor) {
        this.numerodocumentoproveedor = numerodocumentoproveedor;
    }

    /**
     * @return the nombreproveedor
     */
    public String getNombreproveedor() {
        return nombreproveedor;
    }

    /**
     * @param nombreproveedor the nombreproveedor to set
     */
    public void setNombreproveedor(String nombreproveedor) {
        this.nombreproveedor = nombreproveedor;
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
     * @return the descripcioncomprobante
     */
    public String getDescripcioncomprobante() {
        return descripcioncomprobante;
    }

    /**
     * @param descripcioncomprobante the descripcioncomprobante to set
     */
    public void setDescripcioncomprobante(String descripcioncomprobante) {
        this.descripcioncomprobante = descripcioncomprobante;
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
     * @return the descripcionsucursal
     */
    public String getDescripcionsucursal() {
        return descripcionsucursal;
    }

    /**
     * @param descripcionsucursal the descripcionsucursal to set
     */
    public void setDescripcionsucursal(String descripcionsucursal) {
        this.descripcionsucursal = descripcionsucursal;
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
     * @return the descripcionalmacen
     */
    public String getDescripcionalmacen() {
        return descripcionalmacen;
    }

    /**
     * @param descripcionalmacen the descripcionalmacen to set
     */
    public void setDescripcionalmacen(String descripcionalmacen) {
        this.descripcionalmacen = descripcionalmacen;
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
     * @return the fecha_recepcion
     */
    public Timestamp getFecha_recepcion() {
        return fecha_recepcion;
    }

    /**
     * @param fecha_recepcion the fecha_recepcion to set
     */
    public void setFecha_recepcion(Timestamp fecha_recepcion) {
        this.fecha_recepcion = fecha_recepcion;
    }

    /**
     * @return the flag_negociable
     */
    public String getFlag_negociable() {
        return flag_negociable;
    }

    /**
     * @param flag_negociable the flag_negociable to set
     */
    public void setFlag_negociable(String flag_negociable) {
        this.flag_negociable = flag_negociable;
    }

    /**
     * @return the fecha_vencimiento
     */
    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    /**
     * @param fecha_vencimiento the fecha_vencimiento to set
     */
    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
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
     * @return the descripcionMoneda
     */
    public String getDescripcionMoneda() {
        return descripcionMoneda;
    }

    /**
     * @param descripcionMoneda the descripcionMoneda to set
     */
    public void setDescripcionMoneda(String descripcionMoneda) {
        this.descripcionMoneda = descripcionMoneda;
    }

    /**
     * @return the simboloMoneda
     */
    public String getSimboloMoneda() {
        return simboloMoneda;
    }

    /**
     * @param simboloMoneda the simboloMoneda to set
     */
    public void setSimboloMoneda(String simboloMoneda) {
        this.simboloMoneda = simboloMoneda;
    }

    /**
     * @return the formapago
     */
    public String getFormapago() {
        return formapago;
    }

    /**
     * @param formapago the formapago to set
     */
    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    /**
     * @return the estadopago
     */
    public String getEstadopago() {
        return estadopago;
    }

    /**
     * @param estadopago the estadopago to set
     */
    public void setEstadopago(String estadopago) {
        this.estadopago = estadopago;
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
     * @return the flag_gravada
     */
    public String getFlag_gravada() {
        return flag_gravada;
    }

    /**
     * @param flag_gravada the flag_gravada to set
     */
    public void setFlag_gravada(String flag_gravada) {
        this.flag_gravada = flag_gravada;
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
     * @return the total_valorcompra
     */
    public Double getTotal_valorcompra() {
        return total_valorcompra;
    }

    /**
     * @param total_valorcompra the total_valorcompra to set
     */
    public void setTotal_valorcompra(Double total_valorcompra) {
        this.total_valorcompra = total_valorcompra;
    }

    /**
     * @return the total_preciocompra
     */
    public Double getTotal_preciocompra() {
        return total_preciocompra;
    }

    /**
     * @param total_preciocompra the total_preciocompra to set
     */
    public void setTotal_preciocompra(Double total_preciocompra) {
        this.total_preciocompra = total_preciocompra;
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
     * @return the total_anticipo
     */
    public Double getTotal_anticipo() {
        return total_anticipo;
    }

    /**
     * @param total_anticipo the total_anticipo to set
     */
    public void setTotal_anticipo(Double total_anticipo) {
        this.total_anticipo = total_anticipo;
    }

    /**
     * @return the total_compra
     */
    public Double getTotal_compra() {
        return total_compra;
    }

    /**
     * @param total_compra the total_compra to set
     */
    public void setTotal_compra(Double total_compra) {
        this.total_compra = total_compra;
    }

    /**
     * @return the importe_pago
     */
    public Double getImporte_pago() {
        return importe_pago;
    }

    /**
     * @param importe_pago the importe_pago to set
     */
    public void setImporte_pago(Double importe_pago) {
        this.importe_pago = importe_pago;
    }

    /**
     * @return the monto_efectivo
     */
    public Double getMonto_efectivo() {
        return monto_efectivo;
    }

    /**
     * @param monto_efectivo the monto_efectivo to set
     */
    public void setMonto_efectivo(Double monto_efectivo) {
        this.monto_efectivo = monto_efectivo;
    }

    /**
     * @return the monto_otro
     */
    public Double getMonto_otro() {
        return monto_otro;
    }

    /**
     * @param monto_otro the monto_otro to set
     */
    public void setMonto_otro(Double monto_otro) {
        this.monto_otro = monto_otro;
    }

    /**
     * @return the referencia_otro
     */
    public String getReferencia_otro() {
        return referencia_otro;
    }

    /**
     * @param referencia_otro the referencia_otro to set
     */
    public void setReferencia_otro(String referencia_otro) {
        this.referencia_otro = referencia_otro;
    }

    /**
     * @return the cambio_pago
     */
    public Double getCambio_pago() {
        return cambio_pago;
    }

    /**
     * @param cambio_pago the cambio_pago to set
     */
    public void setCambio_pago(Double cambio_pago) {
        this.cambio_pago = cambio_pago;
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

    /**
     * @return the motivo_anulacion
     */
    public String getMotivo_anulacion() {
        return motivo_anulacion;
    }

    /**
     * @param motivo_anulacion the motivo_anulacion to set
     */
    public void setMotivo_anulacion(String motivo_anulacion) {
        this.motivo_anulacion = motivo_anulacion;
    }
    
    
}
