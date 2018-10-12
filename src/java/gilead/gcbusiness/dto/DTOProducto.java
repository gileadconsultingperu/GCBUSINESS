package gilead.gcbusiness.dto;

import java.io.Serializable;

public class DTOProducto implements Serializable{
    
    private Integer idproducto;
    private Integer idmarca;
    private String descripcionmarca;
    private Integer idcategoriaproducto;
    private String descripcioncategoria;
    private Integer idfamiliaproducto;
    private String descripcionfamilia;
    private Integer idclaseproducto;
    private String descripcionclase;
    private Integer idlineaproducto;
    private String descripcionlinea;
    private Integer idmoneda;
    private String descripcionmoneda;
    private String simbolomoneda;
    private Integer idtipoproducto;
    private String descripciontipoproducto;
    private Integer idunidadmedida;
    private String descripcionunidadmedida;
    private String codigo;
    private String codigoEAN;
    private String codigosunat;
    private String descripcion;
    private String afectoIGV;
    private String afectoISC;
    private Integer idtipoISC;
    private Double baseISC;
    private Double factorISC;
    private Double valorcompra;
    private Double preciocompra;
    private String flaglote;
    private byte [] imagen;
    private String estado;
    private String codigoproveedor;
    private Double pesoproveedor;

    public DTOProducto() {
    }

    public DTOProducto(Integer idproducto, Integer idmarca, String descripcionmarca, Integer idcategoriaproducto, String descripcioncategoria, Integer idfamiliaproducto, String descripcionfamilia, Integer idclaseproducto, String descripcionclase, Integer idlineaproducto, String descripcionlinea, Integer idmoneda, String descripcionmoneda, String simbolomoneda, Integer idtipoproducto, String descripciontipoproducto, Integer idunidadmedida, String descripcionunidadmedida, String codigo, String codigoEAN, String codigosunat, String descripcion, String afectoIGV, String afectoISC, Integer idtipoISC, Double baseISC, Double factorISC, Double valorcompra, Double preciocompra, String flaglote, byte[] imagen, String estado, String codigoproveedor, Double pesoproveedor) {
        this.idproducto = idproducto;
        this.idmarca = idmarca;
        this.descripcionmarca = descripcionmarca;
        this.idcategoriaproducto = idcategoriaproducto;
        this.descripcioncategoria = descripcioncategoria;
        this.idfamiliaproducto = idfamiliaproducto;
        this.descripcionfamilia = descripcionfamilia;
        this.idclaseproducto = idclaseproducto;
        this.descripcionclase = descripcionclase;
        this.idlineaproducto = idlineaproducto;
        this.descripcionlinea = descripcionlinea;
        this.idmoneda = idmoneda;
        this.descripcionmoneda = descripcionmoneda;
        this.simbolomoneda = simbolomoneda;
        this.idtipoproducto = idtipoproducto;
        this.descripciontipoproducto = descripciontipoproducto;
        this.idunidadmedida = idunidadmedida;
        this.descripcionunidadmedida = descripcionunidadmedida;
        this.codigo = codigo;
        this.codigoEAN = codigoEAN;
        this.codigosunat = codigosunat;
        this.descripcion = descripcion;
        this.afectoIGV = afectoIGV;
        this.afectoISC = afectoISC;
        this.idtipoISC = idtipoISC;
        this.baseISC = baseISC;
        this.factorISC = factorISC;
        this.valorcompra = valorcompra;
        this.preciocompra = preciocompra;
        this.flaglote = flaglote;
        this.imagen = imagen;
        this.estado = estado;
        this.codigoproveedor = codigoproveedor;
        this.pesoproveedor = pesoproveedor;
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
     * @return the descripcionmarca
     */
    public String getDescripcionmarca() {
        return descripcionmarca;
    }

    /**
     * @param descripcionmarca the descripcionmarca to set
     */
    public void setDescripcionmarca(String descripcionmarca) {
        this.descripcionmarca = descripcionmarca;
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
     * @return the descripcioncategoria
     */
    public String getDescripcioncategoria() {
        return descripcioncategoria;
    }

    /**
     * @param descripcioncategoria the descripcioncategoria to set
     */
    public void setDescripcioncategoria(String descripcioncategoria) {
        this.descripcioncategoria = descripcioncategoria;
    }

    /**
     * @return the idfamiliaproducto
     */
    public Integer getIdfamiliaproducto() {
        return idfamiliaproducto;
    }

    /**
     * @param idfamiliaproducto the idfamiliaproducto to set
     */
    public void setIdfamiliaproducto(Integer idfamiliaproducto) {
        this.idfamiliaproducto = idfamiliaproducto;
    }

    /**
     * @return the descripcionfamilia
     */
    public String getDescripcionfamilia() {
        return descripcionfamilia;
    }

    /**
     * @param descripcionfamilia the descripcionfamilia to set
     */
    public void setDescripcionfamilia(String descripcionfamilia) {
        this.descripcionfamilia = descripcionfamilia;
    }

    /**
     * @return the idclaseproducto
     */
    public Integer getIdclaseproducto() {
        return idclaseproducto;
    }

    /**
     * @param idclaseproducto the idclaseproducto to set
     */
    public void setIdclaseproducto(Integer idclaseproducto) {
        this.idclaseproducto = idclaseproducto;
    }

    /**
     * @return the descripcionclase
     */
    public String getDescripcionclase() {
        return descripcionclase;
    }

    /**
     * @param descripcionclase the descripcionclase to set
     */
    public void setDescripcionclase(String descripcionclase) {
        this.descripcionclase = descripcionclase;
    }

    /**
     * @return the idlineaproducto
     */
    public Integer getIdlineaproducto() {
        return idlineaproducto;
    }

    /**
     * @param idlineaproducto the idlineaproducto to set
     */
    public void setIdlineaproducto(Integer idlineaproducto) {
        this.idlineaproducto = idlineaproducto;
    }

    /**
     * @return the descripcionlinea
     */
    public String getDescripcionlinea() {
        return descripcionlinea;
    }

    /**
     * @param descripcionlinea the descripcionlinea to set
     */
    public void setDescripcionlinea(String descripcionlinea) {
        this.descripcionlinea = descripcionlinea;
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
     * @return the descripcionmoneda
     */
    public String getDescripcionmoneda() {
        return descripcionmoneda;
    }

    /**
     * @param descripcionmoneda the descripcionmoneda to set
     */
    public void setDescripcionmoneda(String descripcionmoneda) {
        this.descripcionmoneda = descripcionmoneda;
    }

    /**
     * @return the simbolomoneda
     */
    public String getSimbolomoneda() {
        return simbolomoneda;
    }

    /**
     * @param simbolomoneda the simbolomoneda to set
     */
    public void setSimbolomoneda(String simbolomoneda) {
        this.simbolomoneda = simbolomoneda;
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
     * @return the descripciontipoproducto
     */
    public String getDescripciontipoproducto() {
        return descripciontipoproducto;
    }

    /**
     * @param descripciontipoproducto the descripciontipoproducto to set
     */
    public void setDescripciontipoproducto(String descripciontipoproducto) {
        this.descripciontipoproducto = descripciontipoproducto;
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
     * @return the descripcionunidadmedida
     */
    public String getDescripcionunidadmedida() {
        return descripcionunidadmedida;
    }

    /**
     * @param descripcionunidadmedida the descripcionunidadmedida to set
     */
    public void setDescripcionunidadmedida(String descripcionunidadmedida) {
        this.descripcionunidadmedida = descripcionunidadmedida;
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
     * @return the codigosunat
     */
    public String getCodigosunat() {
        return codigosunat;
    }

    /**
     * @param codigosunat the codigosunat to set
     */
    public void setCodigosunat(String codigosunat) {
        this.codigosunat = codigosunat;
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
     * @return the flaglote
     */
    public String getFlaglote() {
        return flaglote;
    }

    /**
     * @param flaglote the flaglote to set
     */
    public void setFlaglote(String flaglote) {
        this.flaglote = flaglote;
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

    
}
