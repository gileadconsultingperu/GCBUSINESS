package gilead.gcbusiness.dto;

import java.io.Serializable;
import java.sql.Date;

public class DTOMovimientoAlmacen implements Serializable {

    private Integer idmovimientoalmacen;
    private Date fecha;
    private String almacenorigen;
    private String almacendestino;
    private String observacion;

    public DTOMovimientoAlmacen() {
    }

    public DTOMovimientoAlmacen(Integer idmovimientoalmacen, Date fecha, String almacenorigen, String almacendestino, String observacion) {
        this.idmovimientoalmacen = idmovimientoalmacen;
        this.fecha = fecha;
        this.almacenorigen = almacenorigen;
        this.almacendestino = almacendestino;
        this.observacion = observacion;
    }

    /**
     * @return the idmovimientoalmacen
     */
    public Integer getIdmovimientoalmacen() {
        return idmovimientoalmacen;
    }

    /**
     * @param idmovimientoalmacen the idmovimientoalmacen to set
     */
    public void setIdmovimientoalmacen(Integer idmovimientoalmacen) {
        this.idmovimientoalmacen = idmovimientoalmacen;
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
     * @return the almacenorigen
     */
    public String getAlmacenorigen() {
        return almacenorigen;
    }

    /**
     * @param almacenorigen the almacenorigen to set
     */
    public void setAlmacenorigen(String almacenorigen) {
        this.almacenorigen = almacenorigen;
    }

    /**
     * @return the almacendestino
     */
    public String getAlmacendestino() {
        return almacendestino;
    }

    /**
     * @param almacendestino the almacendestino to set
     */
    public void setAlmacendestino(String almacendestino) {
        this.almacendestino = almacendestino;
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
