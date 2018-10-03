/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Ventas
    Nombre              : BeanCliente.java
    Versión             : 1.0
    Fecha Creación      : 22-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que representa la tabla Cliente de la BD
*/
package gilead.gcbusiness.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BeanCliente implements Serializable{
    
    private Integer idcliente;
    private Integer idtipodocumento;
    private String numerodocumento;
    private String nombre;
    private Integer idtipopersona; 
    private Integer idvendedor;
    private String direccion;
    private String telefono;
    private String correo;
    private String codigoubidistrito;
    private String estado;
    private Timestamp fechaInsercion; 
    private String usuarioInsercion; 
    private String terminalInsercion;
    private String ipInsercion;
    private Timestamp fechaModificacion; 
    private String usuarioModificacion; 
    private String terminalModificacion;
    private String ipModificacion;

    public BeanCliente() {
    }

    public BeanCliente(Integer idcliente, Integer idtipodocumento, String numerodocumento, String nombre, Integer idtipopersona, Integer idvendedor, String direccion, String telefono, String correo, String codigoubidistrito, String estado, Timestamp fechaInsercion, String usuarioInsercion, String terminalInsercion, String ipInsercion, Timestamp fechaModificacion, String usuarioModificacion, String terminalModificacion, String ipModificacion) {
        this.idcliente = idcliente;
        this.idtipodocumento = idtipodocumento;
        this.numerodocumento = numerodocumento;
        this.nombre = nombre;
        this.idtipopersona = idtipopersona;
        this.idvendedor = idvendedor;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.codigoubidistrito = codigoubidistrito;
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
     * @return the idtipodocumento
     */
    public Integer getIdtipodocumento() {
        return idtipodocumento;
    }

    /**
     * @param idtipodocumento the idtipodocumento to set
     */
    public void setIdtipodocumento(Integer idtipodocumento) {
        this.idtipodocumento = idtipodocumento;
    }

    /**
     * @return the numerodocumento
     */
    public String getNumerodocumento() {
        return numerodocumento;
    }

    /**
     * @param numerodocumento the numerodocumento to set
     */
    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the idtipopersona
     */
    public Integer getIdtipopersona() {
        return idtipopersona;
    }

    /**
     * @param idtipopersona the idtipopersona to set
     */
    public void setIdtipopersona(Integer idtipopersona) {
        this.idtipopersona = idtipopersona;
    }

    /**
     * @return the idvendedor
     */
    public Integer getIdvendedor() {
        return idvendedor;
    }

    /**
     * @param idvendedor the idvendedor to set
     */
    public void setIdvendedor(Integer idvendedor) {
        this.idvendedor = idvendedor;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the codigoubidistrito
     */
    public String getCodigoubidistrito() {
        return codigoubidistrito;
    }

    /**
     * @param codigoubidistrito the codigoubidistrito to set
     */
    public void setCodigoubidistrito(String codigoubidistrito) {
        this.codigoubidistrito = codigoubidistrito;
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
