/** 
    Compañia            : Gilead Consulting S.A.C.
    Sistema             : GC-Business
    Módulo              : Seguridad
    Nombre              : DaoAlmacenImpl.java
    Versión             : 1.0
    Fecha Creación      : 21-08-2018
    Autor Creación      : Pablo Jimenez Aguado
    Uso                 : Clase que accede a los datos de la tabla almacen
*/
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanAlmacen;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoAlmacenImpl implements DaoAccion{

    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanAlmacen almacen = (BeanAlmacen) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.almacen (id_sucursal,descripcion,estado,flag_transito,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + almacen.getIdsucursal()+ ",'" + almacen.getDescripcion().toUpperCase() + "','" + almacen.getEstado().toUpperCase() + "','" + almacen.getFlagtransito().toUpperCase()+ "','"+almacen.getFechaInsercion()
                        + "', '" + almacen.getUsuarioInsercion()+"', '"+almacen.getTerminalInsercion()+"', '"+almacen.getIpInsercion()+"')";
                
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
    public BeanAlmacen accionObtener(Integer id) {
        BeanAlmacen almacen = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.almacen\n"
                        + "    WHERE id_almacen = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    almacen = new BeanAlmacen();
                    almacen.setIdalmacen(rs.getInt(1));
                    almacen.setIdsucursal(rs.getInt(2));
                    almacen.setDescripcion(rs.getString(3));
                    almacen.setEstado(rs.getString(4));     
                    almacen.setFlagtransito(rs.getString(5));
                    almacen.setFechaInsercion(rs.getTimestamp(6));
                    almacen.setUsuarioInsercion(rs.getString(7));
                    almacen.setTerminalInsercion(rs.getString(8));
                    almacen.setIpInsercion(rs.getString(9));
                    almacen.setFechaModificacion(rs.getTimestamp(10));
                    almacen.setUsuarioModificacion(rs.getString(11));
                    almacen.setTerminalModificacion(rs.getString(12));
                    almacen.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                almacen = null;
            }
        }

        return almacen;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanAlmacen almacen = (BeanAlmacen) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.almacen SET descripcion = '" + almacen.getDescripcion().toUpperCase()
                        + "', id_sucursal = " + almacen.getIdsucursal()
                        + ", estado = '" + almacen.getEstado().toUpperCase()
                        + "', flag_transito = '" + almacen.getFlagtransito().toUpperCase()
                        + "', fecha_modificacion = '" + almacen.getFechaModificacion()
                        + "', usuario_modificacion = '" + almacen.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + almacen.getTerminalModificacion()
                        + "', ip_modificacion = '" + almacen.getIpModificacion() + "' "
                        + "WHERE id_almacen = " + almacen.getIdalmacen();

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
                String qry = "UPDATE gcbusiness.almacen SET estado = 'I' WHERE id_almacen = " + id;

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
    public List<BeanAlmacen> accionListar() {
        BeanAlmacen almacen = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

            List<BeanAlmacen> listAlmacen = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.almacen ORDER BY id_almacen";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listAlmacen = new LinkedList<BeanAlmacen>();

                while (rs.next()) {
                    almacen = new BeanAlmacen();
                    almacen.setIdalmacen(rs.getInt(1));
                    almacen.setIdsucursal(rs.getInt(2));
                    almacen.setDescripcion(rs.getString(3));
                    almacen.setEstado(rs.getString(4));     
                    almacen.setFlagtransito(rs.getString(5));  
                    almacen.setFechaInsercion(rs.getTimestamp(6));
                    almacen.setUsuarioInsercion(rs.getString(7));
                    almacen.setTerminalInsercion(rs.getString(8));
                    almacen.setIpInsercion(rs.getString(9));
                    almacen.setFechaModificacion(rs.getTimestamp(10));
                    almacen.setUsuarioModificacion(rs.getString(11));
                    almacen.setTerminalModificacion(rs.getString(12));
                    almacen.setIpModificacion(rs.getString(13));

                    listAlmacen.add(almacen);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                almacen = null;
            }
        }

        return listAlmacen;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.almacen SET estado = 'A' WHERE id_almacen = " + id;

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
