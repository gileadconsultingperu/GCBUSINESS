package gilead.gcbusiness.model;

import java.sql.Timestamp;

public class BeanCuentaCobrar {
    
    private Integer idcuentacobrar;
    private Integer idcliente;
    private Integer idcomprobante;
    private Integer idtipocomprobante;
    private Double saldo;
    private String estado;
    private Timestamp fechaInsercion; 
    private String usuarioInsercion; 
    private String terminalInsercion;
    private String ipInsercion;
    private Timestamp fechaModificacion; 
    private String usuarioModificacion; 
    private String terminalModificacion;
    private String ipModificacion;

    public BeanCuentaCobrar() {
    }

    public BeanCuentaCobrar(Integer idcuentacobrar, Integer idcliente, Integer idcomprobante, Integer idtipocomprobante, Double saldo, String estado, Timestamp fechaInsercion, String usuarioInsercion, String terminalInsercion, String ipInsercion, Timestamp fechaModificacion, String usuarioModificacion, String terminalModificacion, String ipModificacion) {
        this.idcuentacobrar = idcuentacobrar;
        this.idcliente = idcliente;
        this.idcomprobante = idcomprobante;
        this.idtipocomprobante = idtipocomprobante;
        this.saldo = saldo;
        this.estado = estado;
        this.fechaInsercion = fechaInsercion;
        this.usuarioInsercion = usuarioInsercion;
        this.terminalInsercion = terminalInsercion;
        this.ipInsercion = ipInsercion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioModificacion = usuarioModificacion;
        this.terminalModificacion = terminalModificacion;
        this.ipModificacion = ipModificacion;
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
     * @return the idcomprobante
     */
    public Integer getIdcomprobante() {
        return idcomprobante;
    }

    /**
     * @param idcomprobante the idcomprobante to set
     */
    public void setIdcomprobante(Integer idcomprobante) {
        this.idcomprobante = idcomprobante;
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

    /**
     * @return the fechaInsercion
     */
    public Timestamp getFechaInsercion() {
        return fechaInsercion;
    }

    /**
     * @param fechaInsercion the fechaInsercion to set
     */
    public void setFechaInsercion(Timestamp fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    /**
     * @return the usuarioInsercion
     */
    public String getUsuarioInsercion() {
        return usuarioInsercion;
    }

    /**
     * @param usuarioInsercion the usuarioInsercion to set
     */
    public void setUsuarioInsercion(String usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    /**
     * @return the terminalInsercion
     */
    public String getTerminalInsercion() {
        return terminalInsercion;
    }

    /**
     * @param terminalInsercion the terminalInsercion to set
     */
    public void setTerminalInsercion(String terminalInsercion) {
        this.terminalInsercion = terminalInsercion;
    }

    /**
     * @return the ipInsercion
     */
    public String getIpInsercion() {
        return ipInsercion;
    }

    /**
     * @param ipInsercion the ipInsercion to set
     */
    public void setIpInsercion(String ipInsercion) {
        this.ipInsercion = ipInsercion;
    }

    /**
     * @return the fechaModificacion
     */
    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the usuarioModificacion
     */
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * @param usuarioModificacion the usuarioModificacion to set
     */
    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the terminalModificacion
     */
    public String getTerminalModificacion() {
        return terminalModificacion;
    }

    /**
     * @param terminalModificacion the terminalModificacion to set
     */
    public void setTerminalModificacion(String terminalModificacion) {
        this.terminalModificacion = terminalModificacion;
    }

    /**
     * @return the ipModificacion
     */
    public String getIpModificacion() {
        return ipModificacion;
    }

    /**
     * @param ipModificacion the ipModificacion to set
     */
    public void setIpModificacion(String ipModificacion) {
        this.ipModificacion = ipModificacion;
    }
    
    
}
