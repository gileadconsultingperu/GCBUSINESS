package gilead.gcbusiness.model;

import java.sql.Timestamp;

public class BeanMovimientoCuentaPagar {
    
    private Integer idmovimientocuentapagar;
    private Integer idcuentapagar;
    private Timestamp fecha; 
    private Double monto;
    private Double saldoanterior;
    private Double saldoactual;
    private String documentoreferencia;
    private String estado;
    private String motivoanulacion;
    private Timestamp fechaInsercion; 
    private String usuarioInsercion; 
    private String terminalInsercion;
    private String ipInsercion;
    private Timestamp fechaModificacion; 
    private String usuarioModificacion; 
    private String terminalModificacion;
    private String ipModificacion;

    public BeanMovimientoCuentaPagar() {
    }

    public BeanMovimientoCuentaPagar(Integer idmovimientocuentapagar, Integer idcuentapagar, Timestamp fecha, Double monto, Double saldoanterior, Double saldoactual, String documentoreferencia, String estado, String motivoanulacion, Timestamp fechaInsercion, String usuarioInsercion, String terminalInsercion, String ipInsercion, Timestamp fechaModificacion, String usuarioModificacion, String terminalModificacion, String ipModificacion) {
        this.idmovimientocuentapagar = idmovimientocuentapagar;
        this.idcuentapagar = idcuentapagar;
        this.fecha = fecha;
        this.monto = monto;
        this.saldoanterior = saldoanterior;
        this.saldoactual = saldoactual;
        this.documentoreferencia = documentoreferencia;
        this.estado = estado;
        this.motivoanulacion = motivoanulacion;
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
     * @return the idmovimientocuentapagar
     */
    public Integer getIdmovimientocuentapagar() {
        return idmovimientocuentapagar;
    }

    /**
     * @param idmovimientocuentapagar the idmovimientocuentapagar to set
     */
    public void setIdmovimientocuentapagar(Integer idmovimientocuentapagar) {
        this.idmovimientocuentapagar = idmovimientocuentapagar;
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
     * @return the fecha
     */
    public Timestamp getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the monto
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    /**
     * @return the saldoanterior
     */
    public Double getSaldoanterior() {
        return saldoanterior;
    }

    /**
     * @param saldoanterior the saldoanterior to set
     */
    public void setSaldoanterior(Double saldoanterior) {
        this.saldoanterior = saldoanterior;
    }

    /**
     * @return the saldoactual
     */
    public Double getSaldoactual() {
        return saldoactual;
    }

    /**
     * @param saldoactual the saldoactual to set
     */
    public void setSaldoactual(Double saldoactual) {
        this.saldoactual = saldoactual;
    }

    /**
     * @return the documentoreferencia
     */
    public String getDocumentoreferencia() {
        return documentoreferencia;
    }

    /**
     * @param documentoreferencia the documentoreferencia to set
     */
    public void setDocumentoreferencia(String documentoreferencia) {
        this.documentoreferencia = documentoreferencia;
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
     * @return the motivoanulacion
     */
    public String getMotivoanulacion() {
        return motivoanulacion;
    }

    /**
     * @param motivoanulacion the motivoanulacion to set
     */
    public void setMotivoanulacion(String motivoanulacion) {
        this.motivoanulacion = motivoanulacion;
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
