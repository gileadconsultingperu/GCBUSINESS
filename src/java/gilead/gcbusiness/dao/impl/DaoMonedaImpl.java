package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanMoneda;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoMonedaImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanMoneda moneda = (BeanMoneda) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.moneda (codigo_sunat,descripcion,simbolo,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + moneda.getCodigoSunat().toUpperCase() + moneda.getDescripcion().toUpperCase() + "','" + "','" + moneda.getSimbolo().toUpperCase() + "','" + moneda.getEstado().toUpperCase() + "','"+moneda.getFechaInsercion()
                        + "', '" + moneda.getUsuarioInsercion()+"', '"+moneda.getTerminalInsercion()+"', '"+moneda.getIpInsercion()+"')";
                
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
    public BeanMoneda accionObtener(Integer id) {
        BeanMoneda moneda = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.moneda\n"
                        + "    WHERE id_moneda = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    moneda = new BeanMoneda();
                    moneda.setIdmoneda(rs.getInt(1));
                    moneda.setCodigoSunat(rs.getString(2));
                    moneda.setDescripcion(rs.getString(3));
                    moneda.setSimbolo(rs.getString(4));  
                    moneda.setEstado(rs.getString(5));     
                    moneda.setFechaInsercion(rs.getTimestamp(6));
                    moneda.setUsuarioInsercion(rs.getString(7));
                    moneda.setTerminalInsercion(rs.getString(8));
                    moneda.setIpInsercion(rs.getString(9));
                    moneda.setFechaModificacion(rs.getTimestamp(10));
                    moneda.setUsuarioModificacion(rs.getString(11));
                    moneda.setTerminalModificacion(rs.getString(12));
                    moneda.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                moneda = null;
            }
        }

        return moneda;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanMoneda moneda = (BeanMoneda) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.moneda SET descripcion = '" + moneda.getDescripcion().toUpperCase()
                        + "', codigo_sunat = '" + moneda.getCodigoSunat().toUpperCase()
                        + "', simbolo = '" + moneda.getSimbolo().toUpperCase()
                        + "', estado = '" + moneda.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + moneda.getFechaModificacion()
                        + "', usuario_modificacion = '" + moneda.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + moneda.getTerminalModificacion()
                        + "', ip_modificacion = '" + moneda.getIpModificacion() + "' "
                        + "WHERE id_moneda = " + moneda.getIdmoneda();

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
                String qry = "UPDATE gcbusiness.moneda SET estado = 'I' WHERE id_moneda = " + id;

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
    public List<BeanMoneda> accionListar() {
        BeanMoneda moneda = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanMoneda> listMoneda = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.moneda ORDER BY id_moneda";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listMoneda = new LinkedList<BeanMoneda>();

                while (rs.next()) {
                    moneda = new BeanMoneda();
                    moneda.setIdmoneda(rs.getInt(1));
                    moneda.setCodigoSunat(rs.getString(2));
                    moneda.setDescripcion(rs.getString(3));
                    moneda.setSimbolo(rs.getString(4));  
                    moneda.setEstado(rs.getString(5));     
                    moneda.setFechaInsercion(rs.getTimestamp(6));
                    moneda.setUsuarioInsercion(rs.getString(7));
                    moneda.setTerminalInsercion(rs.getString(8));
                    moneda.setIpInsercion(rs.getString(9));
                    moneda.setFechaModificacion(rs.getTimestamp(10));
                    moneda.setUsuarioModificacion(rs.getString(11));
                    moneda.setTerminalModificacion(rs.getString(12));
                    moneda.setIpModificacion(rs.getString(13));

                    listMoneda.add(moneda);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                moneda = null;
            }
        }

        return listMoneda;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.moneda SET estado = 'A' WHERE id_moneda = " + id;

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
