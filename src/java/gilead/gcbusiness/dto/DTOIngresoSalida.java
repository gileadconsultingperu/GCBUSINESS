package gilead.gcbusiness.dto;

import java.io.Serializable;
import java.sql.Date;

public class DTOIngresoSalida implements Serializable {

    private Integer idmovimientoinventario;
    private Date fecha;
    private String almacen;
    private String tipomovimiento;
    private String motivomovimiento;
    private String observacion;

    public DTOIngresoSalida() {
    }

    public DTOIngresoSalida(Integer idmovimientoinventario, Date fecha, String almacen, String tipomovimiento, String motivomovimiento, String observacion) {
        this.idmovimientoinventario = idmovimientoinventario;
        this.fecha = fecha;
        this.almacen = almacen;
        this.tipomovimiento = tipomovimiento;
        this.motivomovimiento = motivomovimiento;
        this.observacion = observacion;
    }

    /**
     * @return the idmovimientoinventario
     */
    public Integer getIdmovimientoinventario() {
        return idmovimientoinventario;
    }

    /**
     * @param idmovimientoinventario the idmovimientoinventario to set
     */
    public void setIdmovimientoinventario(Integer idmovimientoinventario) {
        this.idmovimientoinventario = idmovimientoinventario;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
     * @return the tipomovimiento
     */
    public String getTipomovimiento() {
        return tipomovimiento;
    }

    /**
     * @param tipomovimiento the tipomovimiento to set
     */
    public void setTipomovimiento(String tipomovimiento) {
        this.tipomovimiento = tipomovimiento;
    }

    /**
     * @return the motivomovimiento
     */
    public String getMotivomovimiento() {
        return motivomovimiento;
    }

    /**
     * @param motivomovimiento the motivomovimiento to set
     */
    public void setMotivomovimiento(String motivomovimiento) {
        this.motivomovimiento = motivomovimiento;
    }

    /**
     * @return the observacion
     */
    public String getObservacion() {
        return observacion;
    }

    /**
     * @param observacion the observacion to set
     */
    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

}
