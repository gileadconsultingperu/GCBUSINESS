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
                        + "'," + producto.getIdtipoISC() + "," + producto.getBaseISC() + "," + producto.getFactorISC() + "," + producto.getValorventa() + "," + producto.getPrecioventa() + "," + producto.getValorcompra() + "," + producto.getPreciocompra() + ",'" + producto.getCodigoproveedor() + "'," + producto.getPesoproveedor() + ",'" + producto.getEstado().toUpperCase() + "','" +producto.getFechaInsercion()
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
    public BeanProducto accionObtener(Integer id) {
        BeanProducto producto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.producto\n"
                        + "    WHERE id_producto = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    producto = new BeanProducto();
                    producto.setIdproducto(rs.getInt(1));
                    producto.setIdmarca(rs.getInt(2));
                    producto.setIdcategoriaproducto(rs.getInt(3));
                    producto.setIdmoneda(rs.getInt(4));
                    producto.setIdtipoproducto(rs.getInt(5));
                    producto.setIdunidadmedida(rs.getInt(6));
                    producto.setCodigo(rs.getString(7));  
                    producto.setCodigoEAN(rs.getString(8));  
                    producto.setDescripcion(rs.getString(9)); 
                    producto.setAfectoIGV(rs.getString(10));  
                    producto.setAfectoISC(rs.getString(11));
                    producto.setIdtipoISC(rs.getInt(12));
                    producto.setBaseISC(rs.getDouble(13));
                    producto.setFactorISC(rs.getDouble(14));
                    producto.setValorventa(rs.getDouble(15));
                    producto.setPrecioventa(rs.getDouble(16));
                    producto.setValorcompra(rs.getDouble(17));
                    producto.setPreciocompra(rs.getDouble(18));
                    producto.setImagen(rs.getBytes(19));
                    producto.setCodigoproveedor(rs.getString(20));
                    producto.setPesoproveedor(rs.getDouble(21));
                    producto.setEstado(rs.getString(22));          
                    producto.setFechaInsercion(rs.getTimestamp(23));
                    producto.setUsuarioInsercion(rs.getString(24));
                    producto.setTerminalInsercion(rs.getString(25));
                    producto.setIpInsercion(rs.getString(26));
                    producto.setFechaModificacion(rs.getTimestamp(27));
                    producto.setUsuarioModificacion(rs.getString(28));
                    producto.setTerminalModificacion(rs.getString(29));
                    producto.setIpModificacion(rs.getString(30));
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
                        + ", valor_venta = " + producto.getValorventa()
                        + ", precio_venta = " + producto.getPrecioventa()
                        + ", valor_compra = " + producto.getValorcompra()
                        + ", precio_compra = " + producto.getPreciocompra()
                        + ", codigo_proveedor = '" + producto.getCodigoproveedor()
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
                    producto.setIdproducto(rs.getInt(1));
                    producto.setIdmarca(rs.getInt(2));
                    producto.setIdcategoriaproducto(rs.getInt(3));
                    producto.setIdmoneda(rs.getInt(4));
                    producto.setIdtipoproducto(rs.getInt(5));
                    producto.setIdunidadmedida(rs.getInt(6));
                    producto.setCodigo(rs.getString(7));  
                    producto.setCodigoEAN(rs.getString(8));  
                    producto.setDescripcion(rs.getString(9)); 
                    producto.setAfectoIGV(rs.getString(10));  
                    producto.setAfectoISC(rs.getString(11));
                    producto.setIdtipoISC(rs.getInt(12));
                    producto.setBaseISC(rs.getDouble(13));
                    producto.setFactorISC(rs.getDouble(14));
                    producto.setValorventa(rs.getDouble(15));
                    producto.setPrecioventa(rs.getDouble(16));
                    producto.setValorcompra(rs.getDouble(17));
                    producto.setPreciocompra(rs.getDouble(18));
                    producto.setImagen(rs.getBytes(19));
                    producto.setCodigoproveedor(rs.getString(20));
                    producto.setPesoproveedor(rs.getDouble(21));
                    producto.setEstado(rs.getString(22));          
                    producto.setFechaInsercion(rs.getTimestamp(23));
                    producto.setUsuarioInsercion(rs.getString(24));
                    producto.setTerminalInsercion(rs.getString(25));
                    producto.setIpInsercion(rs.getString(26));
                    producto.setFechaModificacion(rs.getTimestamp(27));
                    producto.setUsuarioModificacion(rs.getString(28));
                    producto.setTerminalModificacion(rs.getString(29));
                    producto.setIpModificacion(rs.getString(30));
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
    
}
