package gilead.gcbusiness.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BeanUbigeo implements Serializable{
    
    private String codigo_ubidepartamento; 
    private String codigo_ubiprovincia; 
    private String codigo_ubidistrito;
    private String descripcionUbigeo;
    private String estado;
    private Timestamp fechaInsercion; 
    private String usuarioInsercion; 
    private String terminalInsercion;
    private String ipInsercion;
    private Timestamp fechaModificacion; 
    private String usuarioModificacion; 
    private String terminalModificacion;
    private String ipModificacion;

    public BeanUbigeo() {
    }

    public BeanUbigeo(String codigo_ubidepartamento, String codigo_ubiprovincia, String codigo_ubidistrito, String descripcionUbigeo, String estado, Timestamp fechaInsercion, String usuarioInsercion, String terminalInsercion, String ipInsercion, Timestamp fechaModificacion, String usuarioModificacion, String terminalModificacion, String ipModificacion) {
        this.codigo_ubidepartamento = codigo_ubidepartamento;
        this.codigo_ubiprovincia = codigo_ubiprovincia;
        this.codigo_ubidistrito = codigo_ubidistrito;
        this.descripcionUbigeo = descripcionUbigeo;
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
     * @return the codigo_ubidepartamento
     */
    public String getCodigo_ubidepartamento() {
        return codigo_ubidepartamento;
    }

    /**
     * @param codigo_ubidepartamento the codigo_ubidepartamento to set
     */
    public void setCodigo_ubidepartamento(String codigo_ubidepartamento) {
        this.codigo_ubidepartamento = codigo_ubidepartamento;
    }

    /**
     * @return the codigo_ubiprovincia
     */
    public String getCodigo_ubiprovincia() {
        return codigo_ubiprovincia;
    }

    /**
     * @param codigo_ubiprovincia the codigo_ubiprovincia to set
     */
    public void setCodigo_ubiprovincia(String codigo_ubiprovincia) {
        this.codigo_ubiprovincia = codigo_ubiprovincia;
    }

    /**
     * @return the codigo_ubidistrito
     */
    public String getCodigo_ubidistrito() {
        return codigo_ubidistrito;
    }

    /**
     * @param codigo_ubidistrito the codigo_ubidistrito to set
     */
    public void setCodigo_ubidistrito(String codigo_ubidistrito) {
        this.codigo_ubidistrito = codigo_ubidistrito;
    }

    /**
     * @return the descripcionUbigeo
     */
    public String getDescripcionUbigeo() {
        return descripcionUbigeo;
    }

    /**
     * @param descripcionUbigeo the descripcionUbigeo to set
     */
    public void setDescripcionUbigeo(String descripcionUbigeo) {
        this.descripcionUbigeo = descripcionUbigeo;
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
