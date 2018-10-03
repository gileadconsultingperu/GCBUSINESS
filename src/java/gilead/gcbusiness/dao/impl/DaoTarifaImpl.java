package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanTarifa;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoTarifaImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTarifa tarifa = (BeanTarifa) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.tarifa (descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + tarifa.getDescripcion().toUpperCase() + "','" + tarifa.getAbreviatura().toUpperCase() + "','" + tarifa.getEstado().toUpperCase() + "','"+tarifa.getFechaInsercion()
                        + "', '" + tarifa.getUsuarioInsercion()+"', '"+tarifa.getTerminalInsercion()+"', '"+tarifa.getIpInsercion()+"')";
                
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
    public BeanTarifa accionObtener(Integer id) {
        BeanTarifa tarifa = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tarifa\n"
                        + "    WHERE id_tarifa = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    tarifa = new BeanTarifa();
                    tarifa.setIdtarifa(rs.getInt(1));
                    tarifa.setDescripcion(rs.getString(2));
                    tarifa.setAbreviatura(rs.getString(3));  
                    tarifa.setEstado(rs.getString(4));     
                    tarifa.setFechaInsercion(rs.getTimestamp(5));
                    tarifa.setUsuarioInsercion(rs.getString(6));
                    tarifa.setTerminalInsercion(rs.getString(7));
                    tarifa.setIpInsercion(rs.getString(8));
                    tarifa.setFechaModificacion(rs.getTimestamp(9));
                    tarifa.setUsuarioModificacion(rs.getString(10));
                    tarifa.setTerminalModificacion(rs.getString(11));
                    tarifa.setIpModificacion(rs.getString(12));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tarifa = null;
            }
        }

        return tarifa;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTarifa tarifa = (BeanTarifa) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tarifa SET descripcion = '" + tarifa.getDescripcion().toUpperCase()
                        + "', abreviatura = '" + tarifa.getAbreviatura().toUpperCase()
                        + "', estado = '" + tarifa.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + tarifa.getFechaModificacion()
                        + "', usuario_modificacion = '" + tarifa.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + tarifa.getTerminalModificacion()
                        + "', ip_modificacion = '" + tarifa.getIpModificacion() + "' "
                        + "WHERE id_tarifa = " + tarifa.getIdtarifa();

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
                String qry = "UPDATE gcbusiness.tarifa SET estado = 'I' WHERE id_tarifa = " + id;

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
    public List<BeanTarifa> accionListar() {
        BeanTarifa tarifa = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanTarifa> listTarifa = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tarifa ORDER BY id_tarifa";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listTarifa = new LinkedList<BeanTarifa>();

                while (rs.next()) {
                    tarifa = new BeanTarifa();
                    tarifa.setIdtarifa(rs.getInt(1));
                    tarifa.setDescripcion(rs.getString(2));
                    tarifa.setAbreviatura(rs.getString(3));  
                    tarifa.setEstado(rs.getString(4));     
                    tarifa.setFechaInsercion(rs.getTimestamp(5));
                    tarifa.setUsuarioInsercion(rs.getString(6));
                    tarifa.setTerminalInsercion(rs.getString(7));
                    tarifa.setIpInsercion(rs.getString(8));
                    tarifa.setFechaModificacion(rs.getTimestamp(9));
                    tarifa.setUsuarioModificacion(rs.getString(10));
                    tarifa.setTerminalModificacion(rs.getString(11));
                    tarifa.setIpModificacion(rs.getString(12));

                    listTarifa.add(tarifa);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tarifa = null;
            }
        }

        return listTarifa;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tarifa SET estado = 'A' WHERE id_tarifa = " + id;

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
