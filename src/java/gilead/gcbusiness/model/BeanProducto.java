package gilead.gcbusiness.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BeanProducto implements Serializable{
    
    private Integer idproducto;
    private Integer idmarca;
    private Integer idcategoriaproducto;
    private Integer idmoneda;
    private Integer idtipoproducto;
    private Integer idunidadmedida;
    private String codigo;
    private String codigoEAN;
    private String descripcion;
    private String afectoIGV;
    private String afectoISC;
    private Integer idtipoISC;
    private Double baseISC;
    private Double factorISC;
    private Double valorventa;
    private Double precioventa;
    private Double valorcompra;
    private Double preciocompra;
    private byte [] imagen;
    private String estado;
    private String codigoproveedor;
    private Double pesoproveedor;
    private Timestamp fechaInsercion; 
    private String usuarioInsercion; 
    private String terminalInsercion;
    private String ipInsercion;
    private Timestamp fechaModificacion; 
    private String usuarioModificacion; 
    private String terminalModificacion;
    private String ipModificacion;

    public BeanProducto() {
    }

    public BeanProducto(Integer idproducto, Integer idmarca, Integer idcategoriaproducto, Integer idmoneda, Integer idtipoproducto, Integer idunidadmedida, String codigo, String codigoEAN, String descripcion, String afectoIGV, String afectoISC, Integer idtipoISC, Double baseISC, Double factorISC, Double valorventa, Double precioventa, Double valorcompra, Double preciocompra, byte[] imagen, String estado, String codigoproveedor, Double pesoproveedor, Timestamp fechaInsercion, String usuarioInsercion, String terminalInsercion, String ipInsercion, Timestamp fechaModificacion, String usuarioModificacion, String terminalModificacion, String ipModificacion) {
        this.idproducto = idproducto;
        this.idmarca = idmarca;
        this.idcategoriaproducto = idcategoriaproducto;
        this.idmoneda = idmoneda;
        this.idtipoproducto = idtipoproducto;
        this.idunidadmedida = idunidadmedida;
        this.codigo = codigo;
        this.codigoEAN = codigoEAN;
        this.descripcion = descripcion;
        this.afectoIGV = afectoIGV;
        this.afectoISC = afectoISC;
        this.idtipoISC = idtipoISC;
        this.baseISC = baseISC;
        this.factorISC = factorISC;
        this.valorventa = valorventa;
        this.precioventa = precioventa;
        this.valorcompra = valorcompra;
        this.preciocompra = preciocompra;
        this.imagen = imagen;
        this.estado = estado;
        this.codigoproveedor = codigoproveedor;
        this.pesoproveedor = pesoproveedor;
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
     * @return the idmarca
     */
    public Integer getIdmarca() {
        return idmarca;
    }

    /**
     * @param idmarca the idmarca to set
     */
    public void setIdmarca(Integer idmarca) {
        this.idmarca = idmarca;
    }

    /**
     * @return the idcategoriaproducto
     */
    public Integer getIdcategoriaproducto() {
        return idcategoriaproducto;
    }

    /**
     * @param idcategoriaproducto the idcategoriaproducto to set
     */
    public void setIdcategoriaproducto(Integer idcategoriaproducto) {
        this.idcategoriaproducto = idcategoriaproducto;
    }

    /**
     * @return the idmoneda
     */
    public Integer getIdmoneda() {
        return idmoneda;
    }

    /**
     * @param idmoneda the idmoneda to set
     */
    public void setIdmoneda(Integer idmoneda) {
        this.idmoneda = idmoneda;
    }

    /**
     * @return the idtipoproducto
     */
    public Integer getIdtipoproducto() {
        return idtipoproducto;
    }

    /**
     * @param idtipoproducto the idtipoproducto to set
     */
    public void setIdtipoproducto(Integer idtipoproducto) {
        this.idtipoproducto = idtipoproducto;
    }

    /**
     * @return the idunidadmedida
     */
    public Integer getIdunidadmedida() {
        return idunidadmedida;
    }

    /**
     * @param idunidadmedida the idunidadmedida to set
     */
    public void setIdunidadmedida(Integer idunidadmedida) {
        this.idunidadmedida = idunidadmedida;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the codigoEAN
     */
    public String getCodigoEAN() {
        return codigoEAN;
    }

    /**
     * @param codigoEAN the codigoEAN to set
     */
    public void setCodigoEAN(String codigoEAN) {
        this.codigoEAN = codigoEAN;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the afectoIGV
     */
    public String getAfectoIGV() {
        return afectoIGV;
    }

    /**
     * @param afectoIGV the afectoIGV to set
     */
    public void setAfectoIGV(String afectoIGV) {
        this.afectoIGV = afectoIGV;
    }

    /**
     * @return the afectoISC
     */
    public String getAfectoISC() {
        return afectoISC;
    }

    /**
     * @param afectoISC the afectoISC to set
     */
    public void setAfectoISC(String afectoISC) {
        this.afectoISC = afectoISC;
    }

    /**
     * @return the idtipoISC
     */
    public Integer getIdtipoISC() {
        return idtipoISC;
    }

    /**
     * @param idtipoISC the idtipoISC to set
     */
    public void setIdtipoISC(Integer idtipoISC) {
        this.idtipoISC = idtipoISC;
    }

    /**
     * @return the baseISC
     */
    public Double getBaseISC() {
        return baseISC;
    }

    /**
     * @param baseISC the baseISC to set
     */
    public void setBaseISC(Double baseISC) {
        this.baseISC = baseISC;
    }

    /**
     * @return the factorISC
     */
    public Double getFactorISC() {
        return factorISC;
    }

    /**
     * @param factorISC the factorISC to set
     */
    public void setFactorISC(Double factorISC) {
        this.factorISC = factorISC;
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
     * @return the valorcompra
     */
    public Double getValorcompra() {
        return valorcompra;
    }

    /**
     * @param valorcompra the valorcompra to set
     */
    public void setValorcompra(Double valorcompra) {
        this.valorcompra = valorcompra;
    }

    /**
     * @return the preciocompra
     */
    public Double getPreciocompra() {
        return preciocompra;
    }

    /**
     * @param preciocompra the preciocompra to set
     */
    public void setPreciocompra(Double preciocompra) {
        this.preciocompra = preciocompra;
    }

    /**
     * @return the imagen
     */
    public byte[] getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
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
     * @return the codigoproveedor
     */
    public String getCodigoproveedor() {
        return codigoproveedor;
    }

    /**
     * @param codigoproveedor the codigoproveedor to set
     */
    public void setCodigoproveedor(String codigoproveedor) {
        this.codigoproveedor = codigoproveedor;
    }

    /**
     * @return the pesoproveedor
     */
    public Double getPesoproveedor() {
        return pesoproveedor;
    }

    /**
     * @param pesoproveedor the pesoproveedor to set
     */
    public void setPesoproveedor(Double pesoproveedor) {
        this.pesoproveedor = pesoproveedor;
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
