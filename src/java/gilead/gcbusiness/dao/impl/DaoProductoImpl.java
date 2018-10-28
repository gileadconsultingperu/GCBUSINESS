/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoProductoImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla producto
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.dto.DTOProducto;
import gilead.gcbusiness.model.BeanProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoProductoImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanProducto producto = (BeanProducto) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.producto (id_marca,id_categoriaproducto,id_moneda,id_tipoproducto,id_unidadmedida,codigo_interno,descripcion,afecto_igv,afecto_isc,id_tipoisc,base_isc,factor_isc,valor_venta,precio_venta,valor_compra,precio_compra,codigo_proveedor,peso_proveedor,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + producto.getIdmarca() + "," + producto.getIdcategoriaproducto() + "," + producto.getIdmoneda()+ "," + producto.getIdtipoproducto() + "," + producto.getIdunidadmedida() + ",'" + producto.getCodigo().toUpperCase() + "','" + producto.getDescripcion().toUpperCase() + "','" + producto.getAfectoIGV() + "','" + producto.getAfectoISC() 
                        + "'," + producto.getIdtipoISC() + "," + producto.getBaseISC() + "," + producto.getFactorISC() + "," + producto.getValorcompra() + "," + producto.getPreciocompra() + ",'" + producto.getFlaglote() + "','" + producto.getCodigoproveedor() + "'," + producto.getPesoproveedor() + ",'" + producto.getEstado().toUpperCase() + "','" +producto.getFechaInsercion()
                        + "', '" + producto.getUsuarioInsercion()+"', '"+producto.getTerminalInsercion()+"', '"+producto.getIpInsercion()+"')";
                System.out.println("qry: "+qry);
                st = cn.createStatement();

                int n = st.executeUpdate(qry);
                
                if (n <= 0) {
                    msg = "0 filas afectadas";
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                msg = e1.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                    msg = e2.getMessage();
                }
            }
        }

        return msg;
    }

    @Override
    public DTOProducto accionObtener(Integer id) {
        DTOProducto producto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT p.id_producto, p.id_marca, m.descripcion as marca, p.id_categoriaproducto, c.descripcion as categoria, p.id_unidadmedida, "
                        + "u.descripcion as medida, p.id_moneda, o.descripcion as moneda, o.simbolo, p.id_tipoproducto, t.descripcion as tipoproducto, f.id_familiaproducto, "
                        + "f.descripcion as familia, l.id_lineaproducto, l.descripcion as linea, n.id_claseproducto, n.descripcion as clase, p.codigo_interno, "
                        + "p.codigo_ean, p.codigo_sunat, p.descripcion, p.afecto_igv, p.afecto_isc, p.id_tipoisc, p.base_isc, p.factor_isc, p.valor_compra, "
                        + "p.precio_compra, p.flag_lote, p.imagen, p.codigo_proveedor, p.peso_proveedor, p.estado FROM gcbusiness.producto p, gcbusiness.marca m, "
                        + "gcbusiness.moneda o, gcbusiness.unidadmedida u, "
                        + "gcbusiness.categoriaproducto c, gcbusiness.lineaproducto l, gcbusiness.claseproducto n, gcbusiness.familiaproducto f, gcbusiness.tipoproducto t\n"
                        + "WHERE p.id_marca = m.id_marca AND p.id_categoriaproducto = c.id_categoriaproducto AND p.id_unidadmedida = u.id_unidadmedida "
                        + "AND o.id_moneda = p.id_moneda AND p.id_tipoproducto = t.id_tipoproducto AND c.id_lineaproducto = l.id_lineaproducto "
                        + "AND n.id_claseproducto = l.id_claseproducto AND n.id_familiaproducto = f.id_familiaproducto AND P.id_producto = ?;";
 

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    producto = new DTOProducto();
                    producto.setIdproducto(rs.getInt("id_producto"));
                    producto.setIdmarca(rs.getInt("id_marca"));
                    producto.setDescripcionmarca(rs.getString("marca"));
                    producto.setIdcategoriaproducto(rs.getInt("id_categoriaproducto"));
                    producto.setDescripcioncategoria(rs.getString("categoria"));
                    producto.setIdmoneda(rs.getInt("id_moneda"));
                    producto.setDescripcionmoneda(rs.getString("moneda"));
                    producto.setSimbolomoneda(rs.getString("simbolo"));
                    producto.setIdtipoproducto(rs.getInt("id_tipoproducto"));
                    producto.setDescripciontipoproducto(rs.getString("tipoproducto"));
                    producto.setIdfamiliaproducto(rs.getInt("id_familiaproducto"));
                    producto.setDescripcionfamilia(rs.getString("familia"));
                    producto.setIdlineaproducto(rs.getInt("id_lineaproducto"));
                    producto.setDescripcionlinea(rs.getString("linea"));
                    producto.setIdclaseproducto(rs.getInt("id_claseproducto"));
                    producto.setDescripcionclase(rs.getString("clase"));
                    producto.setIdunidadmedida(rs.getInt("id_unidadmedida"));
                    producto.setDescripcionunidadmedida(rs.getString("medida"));
                    producto.setCodigo(rs.getString("codigo_interno"));  
                    producto.setCodigoEAN(rs.getString("codigo_ean"));
                    producto.setCodigosunat(rs.getString("codigo_sunat"));  
                    producto.setDescripcion(rs.getString("descripcion")); 
                    producto.setAfectoIGV(rs.getString("afecto_igv"));  
                    producto.setAfectoISC(rs.getString("afecto_isc"));
                    producto.setIdtipoISC(rs.getInt("id_tipoisc"));
                    producto.setBaseISC(rs.getDouble("base_isc"));
                    producto.setFactorISC(rs.getDouble("factor_isc"));
                    producto.setValorcompra(rs.getDouble("valor_compra"));
                    producto.setPreciocompra(rs.getDouble("precio_compra"));
                    producto.setFlaglote(rs.getString("flag_lote"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setCodigoproveedor(rs.getString("codigo_proveedor"));
                    producto.setPesoproveedor(rs.getDouble("peso_proveedor"));
                    producto.setEstado(rs.getString("estado"));          
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                producto = null;
            }
        }

        return producto;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanProducto producto = (BeanProducto) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.producto SET id_marca = " + producto.getIdmarca()
                        + ", id_categoriaproducto = " + producto.getIdcategoriaproducto()
                        + ", id_moneda = " + producto.getIdmoneda()
                        + ", id_tipoproducto = " + producto.getIdtipoproducto()
                        + ", id_unidadmedida = " + producto.getIdunidadmedida()
                        + ", codigo_interno = '" + producto.getCodigo().toUpperCase()
                        //+ "', codigo_ean = '" + producto.getCodigoEAN().toUpperCase()
                        + "', descripcion = '" + producto.getDescripcion().toUpperCase()
                        + "', afecto_igv = '" + producto.getAfectoIGV()
                        + "', afecto_isc = '" + producto.getAfectoISC()
                        + "', id_tipoisc = " + producto.getIdtipoISC()
                        + ", base_isc = " + producto.getBaseISC()
                        + ", factor_isc = " + producto.getFactorISC()
                        + ", valor_compra = " + producto.getValorcompra()
                        + ", precio_compra = " + producto.getPreciocompra()
                        + ", flag_lote = '" + producto.getFlaglote()
                        + "', codigo_proveedor = '" + producto.getCodigoproveedor()
                        + "', peso_proveedor = " + producto.getPesoproveedor()
                        + ", estado = '" + producto.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + producto.getFechaModificacion()
                        + "', usuario_modificacion = '" + producto.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + producto.getTerminalModificacion()
                        + "', ip_modificacion = '" + producto.getIpModificacion() + "' "
                        + "WHERE id_producto = " + producto.getIdproducto();
                System.out.println("qry: "+qry);
                st = cn.createStatement();

                int n = st.executeUpdate(qry);

                if (n <= 0) {
                    msg = "0 filas afectadas";
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                msg = e1.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                    msg = e2.getMessage();
                }
            }
        }

        return msg;
    }

    @Override
    public String accionEliminar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.producto SET estado = 'I' WHERE id_producto = " + id;

                st = cn.createStatement();

                int n = st.executeUpdate(qry);

                if (n <= 0) {
                    msg = "0 filas afectadas";
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());                        
                msg = e1.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                    msg = e2.getMessage();
                }
            }
        }

        return msg;
    }

    @Override
    public List<BeanProducto> accionListar() {
        BeanProducto producto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

            List<BeanProducto> listProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.producto ORDER BY id_producto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listProducto = new LinkedList<BeanProducto>();

                while (rs.next()) {
                    producto = new BeanProducto();
                    producto.setIdproducto(rs.getInt("id_producto"));
                    producto.setIdmarca(rs.getInt("id_marca"));
                    producto.setIdcategoriaproducto(rs.getInt("id_categoriaproducto"));
                    producto.setIdmoneda(rs.getInt("id_moneda"));
                    producto.setIdtipoproducto(rs.getInt("id_tipoproducto"));
                    producto.setIdunidadmedida(rs.getInt("id_unidadmedida"));
                    producto.setCodigo(rs.getString("codigo_interno"));  
                    producto.setCodigoEAN(rs.getString("codigo_ean"));  
                    producto.setDescripcion(rs.getString("descripcion")); 
                    producto.setAfectoIGV(rs.getString("afecto_igv"));  
                    producto.setAfectoISC(rs.getString("afecto_isc"));
                    producto.setIdtipoISC(rs.getInt("id_tipoisc"));
                    producto.setBaseISC(rs.getDouble("base_isc"));
                    producto.setFactorISC(rs.getDouble("factor_isc"));
                    producto.setValorcompra(rs.getDouble("valor_compra"));
                    producto.setPreciocompra(rs.getDouble("precio_compra"));
                    producto.setFlaglote(rs.getString("flag_lote"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setCodigoproveedor(rs.getString("codigo_proveedor"));
                    producto.setPesoproveedor(rs.getDouble("peso_proveedor"));
                    producto.setEstado(rs.getString("estado"));          
                    producto.setFechaInsercion(rs.getTimestamp("fecha_insercion"));
                    producto.setUsuarioInsercion(rs.getString("usuario_insercion"));
                    producto.setTerminalInsercion(rs.getString("terminal_insercion"));
                    producto.setIpInsercion(rs.getString("ip_insercion"));
                    producto.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
                    producto.setUsuarioModificacion(rs.getString("usuario_modificacion"));
                    producto.setTerminalModificacion(rs.getString("terminal_modificacion"));
                    producto.setIpModificacion(rs.getString("ip_modificacion"));
                    listProducto.add(producto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                producto = null;
            }
        }

        return listProducto;
    }
    
    public List<BeanProducto> accionListarActivo() {
        BeanProducto producto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

            List<BeanProducto> listProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.producto"
                        + "	WHERE estado = 'A' ORDER BY id_producto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listProducto = new LinkedList<BeanProducto>();

                while (rs.next()) {
                    producto = new BeanProducto();
                    producto.setIdproducto(rs.getInt("id_producto"));
                    producto.setIdmarca(rs.getInt("id_marca"));
                    producto.setIdcategoriaproducto(rs.getInt("id_categoriaproducto"));
                    producto.setIdmoneda(rs.getInt("id_moneda"));
                    producto.setIdtipoproducto(rs.getInt("id_tipoproducto"));
                    producto.setIdunidadmedida(rs.getInt("id_unidadmedida"));
                    producto.setCodigo(rs.getString("codigo_interno"));  
                    producto.setCodigoEAN(rs.getString("codigo_ean"));  
                    producto.setDescripcion(rs.getString("descripcion")); 
                    producto.setAfectoIGV(rs.getString("afecto_igv"));  
                    producto.setAfectoISC(rs.getString("afecto_isc"));
                    producto.setIdtipoISC(rs.getInt("id_tipoisc"));
                    producto.setBaseISC(rs.getDouble("base_isc"));
                    producto.setFactorISC(rs.getDouble("factor_isc"));
                    producto.setValorcompra(rs.getDouble("valor_compra"));
                    producto.setPreciocompra(rs.getDouble("precio_compra"));
                    producto.setFlaglote(rs.getString("flag_lote"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setCodigoproveedor(rs.getString("codigo_proveedor"));
                    producto.setPesoproveedor(rs.getDouble("peso_proveedor"));
                    producto.setEstado(rs.getString("estado"));          
                    producto.setFechaInsercion(rs.getTimestamp("fecha_insercion"));
                    producto.setUsuarioInsercion(rs.getString("usuario_insercion"));
                    producto.setTerminalInsercion(rs.getString("terminal_insercion"));
                    producto.setIpInsercion(rs.getString("ip_insercion"));
                    producto.setFechaModificacion(rs.getTimestamp("fecha_modificacion"));
                    producto.setUsuarioModificacion(rs.getString("usuario_modificacion"));
                    producto.setTerminalModificacion(rs.getString("terminal_modificacion"));
                    producto.setIpModificacion(rs.getString("ip_modificacion"));
                    listProducto.add(producto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                producto = null;
            }
        }

        return listProducto;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.producto SET estado = 'A' WHERE id_producto = " + id;

                st = cn.createStatement();

                int n = st.executeUpdate(qry);

                if (n <= 0) {
                    msg = "0 filas afectadas";
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                msg = e1.getMessage();
            } finally {
                try {
                    cn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                    msg = e2.getMessage();
                }
            }
        }

        return msg;
    }
    
    public List<DTOProducto> accionListarDTOProducto() {
        DTOProducto producto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<DTOProducto> listProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT p.id_producto,\n" +
                                    "p.id_marca,\n" +
                                    "m.descripcion as marca,\n" +
                                    "p.id_categoriaproducto,\n" +
                                    "c.descripcion as categoria,\n" +
                                    "p.id_unidadmedida,\n" +
                                    "u.descripcion as medida,\n" +
                                    "u.abreviatura as abreviaturamedida,\n" +
                                    "p.codigo_interno,\n" +
                                    "p.descripcion,\n" + 
                                    "p.estado\n"
                        + " FROM gcbusiness.producto p INNER JOIN gcbusiness.marca m USING (id_marca)\n"
                        + " INNER JOIN gcbusiness.categoriaproducto c USING (id_categoriaproducto)\n"
                        + " INNER JOIN gcbusiness.unidadmedida u USING (id_unidadmedida)\n"
                        + " ORDER BY p.id_producto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listProducto = new LinkedList<DTOProducto>();

                while (rs.next()) {
                    producto = new DTOProducto();
                    producto.setIdproducto(rs.getInt("id_producto"));
                    producto.setIdmarca(rs.getInt("id_marca"));
                    producto.setDescripcionmarca(rs.getString("marca"));
                    producto.setIdcategoriaproducto(rs.getInt("id_categoriaproducto"));
                    producto.setDescripcioncategoria(rs.getString("categoria"));
                    producto.setIdunidadmedida(rs.getInt("id_unidadmedida"));
                    producto.setDescripcionunidadmedida(rs.getString("medida"));
                    producto.setAbreviaturaunidadmedida(rs.getString("abreviaturamedida"));
                    producto.setCodigo(rs.getString("codigo_interno"));  
                    producto.setDescripcion(rs.getString("descripcion")); 
                    producto.setEstado(rs.getString("estado"));          
                    listProducto.add(producto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                producto = null;
            }
        }

        return listProducto;
    }
    
}
