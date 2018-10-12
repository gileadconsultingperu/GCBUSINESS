/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoProveedorImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla proveedor
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanProveedor;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoProveedorImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanProveedor proveedor = (BeanProveedor) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.proveedor (id_tipodocumento,numero_documento,nombre,id_tipopersona,direccion,telefono,correo,codigo_ubidistrito,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + proveedor.getIdtipodocumento() + ",'" + proveedor.getNumerodocumento().toUpperCase() + "','" + proveedor.getNombre().toUpperCase() + "'," + proveedor.getIdtipopersona()
                        + ",'" + proveedor.getDireccion().toUpperCase() + "','" + proveedor.getTelefono().toUpperCase() + "','" + proveedor.getCorreo().toUpperCase() + "','" + proveedor.getCodigoubidistrito().toUpperCase() + "','" + proveedor.getEstado().toUpperCase() + "','" +proveedor.getFechaInsercion()
                        + "', '" + proveedor.getUsuarioInsercion()+"', '"+proveedor.getTerminalInsercion()+"', '"+proveedor.getIpInsercion()+"')";
                
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
    public BeanProveedor accionObtener(Integer id) {
        BeanProveedor proveedor = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.proveedor\n"
                        + "    WHERE id_proveedor = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    proveedor = new BeanProveedor();
                    proveedor.setIdproveedor(rs.getInt(1));
                    proveedor.setIdtipodocumento(rs.getInt(2));
                    proveedor.setNumerodocumento(rs.getString(3));
                    proveedor.setNombre(rs.getString(4));
                    proveedor.setIdtipopersona(rs.getInt(5));
                    proveedor.setDireccion(rs.getString(6));  
                    proveedor.setTelefono(rs.getString(7));  
                    proveedor.setCorreo(rs.getString(8)); 
                    proveedor.setCodigoubidistrito(rs.getString(9));  
                    proveedor.setEstado(rs.getString(10));          
                    proveedor.setFechaInsercion(rs.getTimestamp(11));
                    proveedor.setUsuarioInsercion(rs.getString(12));
                    proveedor.setTerminalInsercion(rs.getString(13));
                    proveedor.setIpInsercion(rs.getString(14));
                    proveedor.setFechaModificacion(rs.getTimestamp(15));
                    proveedor.setUsuarioModificacion(rs.getString(16));
                    proveedor.setTerminalModificacion(rs.getString(17));
                    proveedor.setIpModificacion(rs.getString(18));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                proveedor = null;
            }
        }

        return proveedor;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanProveedor proveedor = (BeanProveedor) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.proveedor SET nombre = '" + proveedor.getNombre().toUpperCase()
                        + "', id_tipodocumento = " + proveedor.getIdtipodocumento()
                        + ", numero_documento = '" + proveedor.getNumerodocumento().toUpperCase()
                        + "', id_tipopersona = " + proveedor.getIdtipopersona()
                        + ", direccion = '" + proveedor.getDireccion().toUpperCase()
                        + "', telefono = '" + proveedor.getTelefono().toUpperCase()
                        + "', correo = '" + proveedor.getCorreo().toUpperCase()
                        + "', codigo_ubidistrito = '" + proveedor.getCodigoubidistrito().toUpperCase()
                        + "', estado = '" + proveedor.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + proveedor.getFechaModificacion()
                        + "', usuario_modificacion = '" + proveedor.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + proveedor.getTerminalModificacion()
                        + "', ip_modificacion = '" + proveedor.getIpModificacion() + "' "
                        + "WHERE id_proveedor = " + proveedor.getIdproveedor();

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
                String qry = "UPDATE gcbusiness.proveedor SET estado = 'I' WHERE id_proveedor = " + id;

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
    public List<BeanProveedor> accionListar() {
        BeanProveedor proveedor = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

            List<BeanProveedor> listProveedor = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.proveedor ORDER BY id_proveedor";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listProveedor = new LinkedList<BeanProveedor>();

                while (rs.next()) {
                    proveedor = new BeanProveedor();
                    proveedor.setIdproveedor(rs.getInt(1));
                    proveedor.setIdtipodocumento(rs.getInt(2));
                    proveedor.setNumerodocumento(rs.getString(3));
                    proveedor.setNombre(rs.getString(4));
                    proveedor.setIdtipopersona(rs.getInt(5));
                    proveedor.setDireccion(rs.getString(6));  
                    proveedor.setTelefono(rs.getString(7));  
                    proveedor.setCorreo(rs.getString(8)); 
                    proveedor.setCodigoubidistrito(rs.getString(9));  
                    proveedor.setEstado(rs.getString(10));          
                    proveedor.setFechaInsercion(rs.getTimestamp(11));
                    proveedor.setUsuarioInsercion(rs.getString(12));
                    proveedor.setTerminalInsercion(rs.getString(13));
                    proveedor.setIpInsercion(rs.getString(14));
                    proveedor.setFechaModificacion(rs.getTimestamp(15));
                    proveedor.setUsuarioModificacion(rs.getString(16));
                    proveedor.setTerminalModificacion(rs.getString(17));
                    proveedor.setIpModificacion(rs.getString(18));

                    listProveedor.add(proveedor);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                proveedor = null;
            }
        }

        return listProveedor;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.proveedor SET estado = 'A' WHERE id_proveedor = " + id;

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
