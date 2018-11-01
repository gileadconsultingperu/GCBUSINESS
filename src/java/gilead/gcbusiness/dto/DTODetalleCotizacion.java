package gilead.gcbusiness.dto;

import java.io.Serializable;

public class DTODetalleCotizacion implements Serializable {

    private Integer iddetallecotizacion;
    private Integer idcotizacion;
    private Integer idproducto;
    private Integer idlote;
    private String codigoproducto;
    private String nombreproducto;
    private String unidadmedida;
    private Double cantidad;
    private String tipoigv;
    private String afectoigv;
    private Double pctoigv;
    private Double valorunitarioventa;
    private Double preciounitarioventa;
    private Double valorventa;
    private Double precioventa;
    private Double montoigv;
    private String tipoisc;
    private Double montoisc;
    private String flagbonificacion;
    private String tipodescuento;
    private Double montodescuento;
    private Double pctodescuento;
    private Double valorunitariodescuento;
    private Double preciounitariodescuento;
    private Double valorventadescuento;
    private Double precioventadescuento;
    private Double montoigvdescuento;

    public DTODetalleCotizacion() {
    }

    public DTODetalleCotizacion(Integer iddetallecotizacion, Integer idcotizacion, Integer idproducto, Integer idlote, String codigoproducto, String nombreproducto, String unidadmedida, Double cantidad, String tipoigv, String afectoigv, Double pctoigv, Double valorunitarioventa, Double preciounitarioventa, Double valorventa, Double precioventa, Double montoigv, String tipoisc, Double montoisc, String flagbonificacion, String tipodescuento, Double montodescuento, Double pctodescuento, Double valorunitariodescuento, Double preciounitariodescuento, Double valorventadescuento, Double precioventadescuento, Double montoigvdescuento) {
        this.iddetallecotizacion = iddetallecotizacion;
        this.idcotizacion = idcotizacion;
        this.idproducto = idproducto;
        this.idlote = idlote;
        this.codigoproducto = codigoproducto;
        this.nombreproducto = nombreproducto;
        this.unidadmedida = unidadmedida;
        this.cantidad = cantidad;
        this.tipoigv = tipoigv;
        this.afectoigv = afectoigv;
        this.pctoigv = pctoigv;
        this.valorunitarioventa = valorunitarioventa;
        this.preciounitarioventa = preciounitarioventa;
        this.valorventa = valorventa;
        this.precioventa = precioventa;
        this.montoigv = montoigv;
        this.tipoisc = tipoisc;
        this.montoisc = montoisc;
        this.flagbonificacion = flagbonificacion;
        this.tipodescuento = tipodescuento;
        this.montodescuento = montodescuento;
        this.pctodescuento = pctodescuento;
        this.valorunitariodescuento = valorunitariodescuento;
        this.preciounitariodescuento = preciounitariodescuento;
        this.valorventadescuento = valorventadescuento;
        this.precioventadescuento = precioventadescuento;
        this.montoigvdescuento = montoigvdescuento;
    }

    /**
     * @return the iddetallecotizacion
     */
    public Integer getIddetallecotizacion() {
        return iddetallecotizacion;
    }

    /**
     * @param iddetallecotizacion the iddetallecotizacion to set
     */
    public void setIddetallecotizacion(Integer iddetallecotizacion) {
        this.iddetallecotizacion = iddetallecotizacion;
    }

    /**
     * @return the idcotizacion
     */
    public Integer getIdcotizacion() {
        return idcotizacion;
    }

    /**
     * @param idcotizacion the idcotizacion to set
     */
    public void setIdcotizacion(Integer idcotizacion) {
        this.idcotizacion = idcotizacion;
    }

    /**
     * @return the idproducto
     */
    public Integer getIdproducto() {
        return idproducto;
    }

    /**
     * @param idproducto the idproducto to set
     */
    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    /**
     * @return the idlote
     */
    public Integer getIdlote() {
        return idlote;
    }

    /**
     * @param idlote the idlote to set
     */
    public void setIdlote(Integer idlote) {
        this.idlote = idlote;
    }

    /**
     * @return the codigoproducto
     */
    public String getCodigoproducto() {
        return codigoproducto;
    }

    /**
     * @param codigoproducto the codigoproducto to set
     */
    public void setCodigoproducto(String codigoproducto) {
        this.codigoproducto = codigoproducto;
    }

    /**
     * @return the nombreproducto
     */
    public String getNombreproducto() {
        return nombreproducto;
    }

    /**
     * @param nombreproducto the nombreproducto to set
     */
    public void setNombreproducto(String nombreproducto) {
        this.nombreproducto = nombreproducto;
    }

    /**
     * @return the unidadmedida
     */
    public String getUnidadmedida() {
        return unidadmedida;
    }

    /**
     * @param unidadmedida the unidadmedida to set
     */
    public void setUnidadmedida(String unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    /**
     * @return the cantidad
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the tipoigv
     */
    public String getTipoigv() {
        return tipoigv;
    }

    /**
     * @param tipoigv the tipoigv to set
     */
    public void setTipoigv(String tipoigv) {
        this.tipoigv = tipoigv;
    }

    /**
     * @return the afectoigv
     */
    public String getAfectoigv() {
        return afectoigv;
    }

    /**
     * @param afectoigv the afectoigv to set
     */
    public void setAfectoigv(String afectoigv) {
        this.afectoigv = afectoigv;
    }

    /**
     * @return the pctoigv
     */
    public Double getPctoigv() {
        return pctoigv;
    }

    /**
     * @param pctoigv the pctoigv to set
     */
    public void setPctoigv(Double pctoigv) {
        this.pctoigv = pctoigv;
    }

    /**
     * @return the valorunitarioventa
     */
    public Double getValorunitarioventa() {
        return valorunitarioventa;
    }

    /**
     * @param valorunitarioventa the valorunitarioventa to set
     */
    public void setValorunitarioventa(Double valorunitarioventa) {
        this.valorunitarioventa = valorunitarioventa;
    }

    /**
     * @return the preciounitarioventa
     */
    public Double getPreciounitarioventa() {
        return preciounitarioventa;
    }

    /**
     * @param preciounitarioventa the preciounitarioventa to set
     */
    public void setPreciounitarioventa(Double preciounitarioventa) {
        this.preciounitarioventa = preciounitarioventa;
    }

    /**
     * @return the valorventa
     */
    public Double getValorventa() {
        return valorventa;
    }

    /**
     * @param valorventa the valorventa to set
     */
    public void setValorventa(Double valorventa) {
        this.valorventa = valorventa;
    }

    /**
     * @return the precioventa
     */
    public Double getPrecioventa() {
        return precioventa;
    }

    /**
     * @param precioventa the precioventa to set
     */
    public void setPrecioventa(Double precioventa) {
        this.precioventa = precioventa;
    }

    /**
     * @return the montoigv
     */
    public Double getMontoigv() {
        return montoigv;
    }

    /**
     * @param montoigv the montoigv to set
     */
    public void setMontoigv(Double montoigv) {
        this.montoigv = montoigv;
    }

    /**
     * @return the tipoisc
     */
    public String getTipoisc() {
        return tipoisc;
    }

    /**
     * @param tipoisc the tipoisc to set
     */
    public void setTipoisc(String tipoisc) {
        this.tipoisc = tipoisc;
    }

    /**
     * @return the montoisc
     */
    public Double getMontoisc() {
        return montoisc;
    }

    /**
     * @param montoisc the montoisc to set
     */
    public void setMontoisc(Double montoisc) {
        this.montoisc = montoisc;
    }

    /**
     * @return the flagbonificacion
     */
    public String getFlagbonificacion() {
        return flagbonificacion;
    }

    /**
     * @param flagbonificacion the flagbonificacion to set
     */
    public void setFlagbonificacion(String flagbonificacion) {
        this.flagbonificacion = flagbonificacion;
    }

    /**
     * @return the tipodescuento
     */
    public String getTipodescuento() {
        return tipodescuento;
    }

    /**
     * @param tipodescuento the tipodescuento to set
     */
    public void setTipodescuento(String tipodescuento) {
        this.tipodescuento = tipodescuento;
    }

    /**
     * @return the montodescuento
     */
    public Double getMontodescuento() {
        return montodescuento;
    }

    /**
     * @param montodescuento the montodescuento to set
     */
    public void setMontodescuento(Double montodescuento) {
        this.montodescuento = montodescuento;
    }

    /**
     * @return the pctodescuento
     */
    public Double getPctodescuento() {
        return pctodescuento;
    }

    /**
     * @param pctodescuento the pctodescuento to set
     */
    public void setPctodescuento(Double pctodescuento) {
        this.pctodescuento = pctodescuento;
    }

    /**
     * @return the valorunitariodescuento
     */
    public Double getValorunitariodescuento() {
        return valorunitariodescuento;
    }

    /**
     * @param valorunitariodescuento the valorunitariodescuento to set
     */
    public void setValorunitariodescuento(Double valorunitariodescuento) {
        this.valorunitariodescuento = valorunitariodescuento;
    }

    /**
     * @return the preciounitariodescuento
     */
    public Double getPreciounitariodescuento() {
        return preciounitariodescuento;
    }

    /**
     * @param preciounitariodescuento the preciounitariodescuento to set
     */
    public void setPreciounitariodescuento(Double preciounitariodescuento) {
        this.preciounitariodescuento = preciounitariodescuento;
    }

    /**
     * @return the valorventadescuento
     */
    public Double getValorventadescuento() {
        return valorventadescuento;
    }

    /**
     * @param valorventadescuento the valorventadescuento to set
     */
    public void setValorventadescuento(Double valorventadescuento) {
        this.valorventadescuento = valorventadescuento;
    }

    /**
     * @return the precioventadescuento
     */
    public Double getPrecioventadescuento() {
        return precioventadescuento;
    }

    /**
     * @param precioventadescuento the precioventadescuento to set
     */
    public void setPrecioventadescuento(Double precioventadescuento) {
        this.precioventadescuento = precioventadescuento;
    }

    /**
     * @return the montoigvdescuento
     */
    public Double getMontoigvdescuento() {
        return montoigvdescuento;
    }

    /**
     * @param montoigvdescuento the montoigvdescuento to set
     */
    public void setMontoigvdescuento(Double montoigvdescuento) {
        this.montoigvdescuento = montoigvdescuento;
    }

}
