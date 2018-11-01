package gilead.gcbusiness.dto;

import java.sql.Timestamp;

public class DTOCuentaPagar {
    
    private Integer idcompra;
    private Timestamp fechaemision;
    private Integer idtipocomprobante;
    private String abreviaturacomprobante;
    private String serie;
    private Integer correlativoserie;
    private Double total_compra;
    private Integer idcuentapagar;
    private Integer idproveedor;
    private String numerodocumentoproveedor;
    private String nombreproveedor;
    private Double saldo;
    private String estado;

    public DTOCuentaPagar() {
    }

    public DTOCuentaPagar(Integer idcompra, Timestamp fechaemision, Integer idtipocomprobante, String abreviaturacomprobante, String serie, Integer correlativoserie, Double total_compra, Integer idcuentapagar, Integer idproveedor, String numerodocumentoproveedor, String nombreproveedor, Double saldo, String estado) {
        this.idcompra = idcompra;
        this.fechaemision = fechaemision;
        this.idtipocomprobante = idtipocomprobante;
        this.abreviaturacomprobante = abreviaturacomprobante;
        this.serie = serie;
        this.correlativoserie = correlativoserie;
        this.total_compra = total_compra;
        this.idcuentapagar = idcuentapagar;
        this.idproveedor = idproveedor;
        this.numerodocumentoproveedor = numerodocumentoproveedor;
        this.nombreproveedor = nombreproveedor;
        this.saldo = saldo;
        this.estado = estado;
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
     * @return the idcuentapagar
     */
    public Integer getIdcuentapagar() {
        return idcuentapagar;
    }

    /**
     * @param idcuentapagar the idcuentapagar to set
     */
    public void setIdcuentapagar(Integer idcuentapagar) {
        this.idcuentapagar = idcuentapagar;
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
