package gilead.gcbusiness.dto;

import java.io.Serializable;

public class DTOUsuarioOpcion implements Serializable{
    private Integer idusuario;
    private String usuario;
    private String nombres;
    private String apellidos; 
    private String estadoUsuario;
    private Integer idopcionmenu;
    private String descripcionOpcion;
    private String estadoOpcion;
    private String estadoUsuarioOpcion;

    public DTOUsuarioOpcion() {
    }

    public DTOUsuarioOpcion(Integer idusuario, String usuario, String nombres, String apellidos, String estadoUsuario, Integer idopcionmenu, String descripcionOpcion, String estadoOpcion, String estadoUsuarioOpcion) {
        this.idusuario = idusuario;
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.estadoUsuario = estadoUsuario;
        this.idopcionmenu = idopcionmenu;
        this.descripcionOpcion = descripcionOpcion;
        this.estadoOpcion = estadoOpcion;
        this.estadoUsuarioOpcion = estadoUsuarioOpcion;
    }

    /**
     * @return the idusuario
     */
    public Integer getIdusuario() {
        return idusuario;
    }

    /**
     * @param idusuario the idusuario to set
     */
    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the estadoUsuario
     */
    public String getEstadoUsuario() {
        return estadoUsuario;
    }

    /**
     * @param estadoUsuario the estadoUsuario to set
     */
    public void setEstadoUsuario(String estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    /**
     * @return the idopcionmenu
     */
    public Integer getIdopcionmenu() {
        return idopcionmenu;
    }

    /**
     * @param idopcionmenu the idopcionmenu to set
     */
    public void setIdopcionmenu(Integer idopcionmenu) {
        this.idopcionmenu = idopcionmenu;
    }

    /**
     * @return the descripcionOpcion
     */
    public String getDescripcionOpcion() {
        return descripcionOpcion;
    }

    /**
     * @param descripcionOpcion the descripcionOpcion to set
     */
    public void setDescripcionOpcion(String descripcionOpcion) {
        this.descripcionOpcion = descripcionOpcion;
    }

    /**
     * @return the estadoOpcion
     */
    public String getEstadoOpcion() {
        return estadoOpcion;
    }

    /**
     * @param estadoOpcion the estadoOpcion to set
     */
    public void setEstadoOpcion(String estadoOpcion) {
        this.estadoOpcion = estadoOpcion;
    }

    /**
     * @return the estadoUsuarioOpcion
     */
    public String getEstadoUsuarioOpcion() {
        return estadoUsuarioOpcion;
    }

    /**
     * @param estadoUsuarioOpcion the estadoUsuarioOpcion to set
     */
    public void setEstadoUsuarioOpcion(String estadoUsuarioOpcion) {
        this.estadoUsuarioOpcion = estadoUsuarioOpcion;
    }

    
    
}
