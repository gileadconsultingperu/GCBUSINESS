package gilead.gcbusiness.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOCuentaCobrar implements Serializable{
    
    private Integer idventa;
    private Timestamp fechaemision;
    private Integer idtipocomprobante;
    private String abreviaturacomprobante;
    private Integer idserie;
    private String serie;
    private Integer correlativoserie;
    private Double total_venta;
    private Integer idcuentacobrar;
    private Integer idcliente;
    private String numerodocumentocliente;
    private String nombrecliente;
    private Double saldo;
    private String estado;

    public DTOCuentaCobrar() {
    }

    public DTOCuentaCobrar(Integer idventa, Timestamp fechaemision, Integer idtipocomprobante, String abreviaturacomprobante, Integer idserie, String serie, Integer correlativoserie, Double total_venta, Integer idcuentacobrar, Integer idcliente, String numerodocumentocliente, String nombrecliente, Double saldo, String estado) {
        this.idventa = idventa;
        this.fechaemision = fechaemision;
        this.idtipocomprobante = idtipocomprobante;
        this.abreviaturacomprobante = abreviaturacomprobante;
        this.idserie = idserie;
        this.serie = serie;
        this.correlativoserie = correlativoserie;
        this.total_venta = total_venta;
        this.idcuentacobrar = idcuentacobrar;
        this.idcliente = idcliente;
        this.numerodocumentocliente = numerodocumentocliente;
        this.nombrecliente = nombrecliente;
        this.saldo = saldo;
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
     * @return the fechaemision
     */
    public Timestamp getFechaemision() {
        return fechaemision;
    }

    /**
     * @param fechaemision the fechaemision to set
     */
    public void setFechaemision(Timestamp fechaemision) {
        this.fechaemision = fechaemision;
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
     * @return the idcuentacobrar
     */
    public Integer getIdcuentacobrar() {
        return idcuentacobrar;
    }

    /**
     * @param idcuentacobrar the idcuentacobrar to set
     */
    public void setIdcuentacobrar(Integer idcuentacobrar) {
        this.idcuentacobrar = idcuentacobrar;
    }

    /**
     * @return the idcliente
     */
    public Integer getIdcliente() {
        return idcliente;
    }

    /**
     * @param idcliente the idcliente to set
     */
    public void setIdcliente(Integer idcliente) {
        this.idcliente = idcliente;
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
     * @return the saldo
     */
    public Double getSaldo() {
        return saldo;
    }

    /**
     * @param saldo the saldo to set
     */
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
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
