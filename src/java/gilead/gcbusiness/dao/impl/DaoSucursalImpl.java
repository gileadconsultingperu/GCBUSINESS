/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoSucursalImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla sucursal
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanSucursal;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoSucursalImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanSucursal sucursal = (BeanSucursal) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.sucursal (id_empresa,descripcion,direccion,telefono,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + sucursal.getIdempresa()+ ",'" + sucursal.getDescripcion().toUpperCase() + "','" + sucursal.getDireccion().toUpperCase() + "','" + sucursal.getTelefono() + "','"+sucursal.getEstado().toUpperCase() + "','"+sucursal.getFechaInsercion()
                        + "', '" + sucursal.getUsuarioInsercion()+"', '"+sucursal.getTerminalInsercion()+"', '"+sucursal.getIpInsercion()+"')";
                
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
    public BeanSucursal accionObtener(Integer id) {
        BeanSucursal sucursal = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.sucursal\n"
                        + "    WHERE id_sucursal = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    sucursal = new BeanSucursal();
                    sucursal.setIdsucursal(rs.getInt(1));
                    sucursal.setIdempresa(rs.getInt(2));
                    sucursal.setDescripcion(rs.getString(3));
                    sucursal.setDireccion(rs.getString(4));
                    sucursal.setTelefono(rs.getString(5));
                    sucursal.setEstado(rs.getString(6));     
                    sucursal.setFechaInsercion(rs.getTimestamp(7));
                    sucursal.setUsuarioInsercion(rs.getString(8));
                    sucursal.setTerminalInsercion(rs.getString(9));
                    sucursal.setIpInsercion(rs.getString(10));
                    sucursal.setFechaModificacion(rs.getTimestamp(11));
                    sucursal.setUsuarioModificacion(rs.getString(12));
                    sucursal.setTerminalModificacion(rs.getString(13));
                    sucursal.setIpModificacion(rs.getString(14));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                sucursal = null;
            }
        }

        return sucursal;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanSucursal sucursal = (BeanSucursal) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.sucursal SET descripcion = '" + sucursal.getDescripcion().toUpperCase()
                        + "', direccion = '" + sucursal.getDireccion().toUpperCase()
                        + "', telefono = '" + sucursal.getTelefono().toUpperCase()
                        + "', estado = '" + sucursal.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + sucursal.getFechaModificacion()
                        + "', usuario_modificacion = '" + sucursal.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + sucursal.getTerminalModificacion()
                        + "', ip_modificacion = '" + sucursal.getIpModificacion() + "' "
                        + "WHERE id_sucursal = " + sucursal.getIdsucursal();

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
                String qry = "UPDATE gcbusiness.sucursal SET estado = 'I' WHERE id_sucursal = " + id;

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
    public List<BeanSucursal> accionListar() {
        BeanSucursal sucursal = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanSucursal> listSucursal = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.sucursal ORDER BY id_sucursal";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listSucursal = new LinkedList<BeanSucursal>();

                while (rs.next()) {
                    sucursal = new BeanSucursal();
                    sucursal.setIdsucursal(rs.getInt(1));
                    sucursal.setIdempresa(rs.getInt(2));
                    sucursal.setDescripcion(rs.getString(3));
                    sucursal.setDireccion(rs.getString(4));
                    sucursal.setTelefono(rs.getString(5));
                    sucursal.setEstado(rs.getString(6));     
                    sucursal.setFechaInsercion(rs.getTimestamp(7));
                    sucursal.setUsuarioInsercion(rs.getString(8));
                    sucursal.setTerminalInsercion(rs.getString(9));
                    sucursal.setIpInsercion(rs.getString(10));
                    sucursal.setFechaModificacion(rs.getTimestamp(11));
                    sucursal.setUsuarioModificacion(rs.getString(12));
                    sucursal.setTerminalModificacion(rs.getString(13));
                    sucursal.setIpModificacion(rs.getString(14));

                    listSucursal.add(sucursal);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                sucursal = null;
            }
        }

        return listSucursal;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.sucursal SET estado = 'A' WHERE id_sucursal = " + id;

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
