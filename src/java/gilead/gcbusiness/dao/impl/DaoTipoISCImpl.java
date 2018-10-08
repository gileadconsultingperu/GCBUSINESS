package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanTipoISC;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoTipoISCImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoISC tipoisc = (BeanTipoISC) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.tipoisc (codigo_sunat,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + tipoisc.getCodigoSunat().toUpperCase() + "','" + tipoisc.getDescripcion().toUpperCase() + "','"  + tipoisc.getAbreviatura().toUpperCase() + "','" + tipoisc.getEstado().toUpperCase() + "','"+tipoisc.getFechaInsercion()
                        + "', '" + tipoisc.getUsuarioInsercion()+"', '"+tipoisc.getTerminalInsercion()+"', '"+tipoisc.getIpInsercion()+"')";
                
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
    public BeanTipoISC accionObtener(Integer id) {
        BeanTipoISC tipoisc = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipoisc\n"
                        + "    WHERE id_tipoisc = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    tipoisc = new BeanTipoISC();
                    tipoisc.setIdtipoisc(rs.getInt(1));
                    tipoisc.setCodigoSunat(rs.getString(2));
                    tipoisc.setDescripcion(rs.getString(3));
                    tipoisc.setAbreviatura(rs.getString(4));  
                    tipoisc.setEstado(rs.getString(5));     
                    tipoisc.setFechaInsercion(rs.getTimestamp(6));
                    tipoisc.setUsuarioInsercion(rs.getString(7));
                    tipoisc.setTerminalInsercion(rs.getString(8));
                    tipoisc.setIpInsercion(rs.getString(9));
                    tipoisc.setFechaModificacion(rs.getTimestamp(10));
                    tipoisc.setUsuarioModificacion(rs.getString(11));
                    tipoisc.setTerminalModificacion(rs.getString(12));
                    tipoisc.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipoisc = null;
            }
        }

        return tipoisc;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoISC tipoisc = (BeanTipoISC) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipoisc SET descripcion = '" + tipoisc.getDescripcion().toUpperCase()
                        + "', codigo_sunat = '" + tipoisc.getCodigoSunat().toUpperCase()
                        + "', abreviatura = '" + tipoisc.getAbreviatura().toUpperCase()
                        + "', estado = '" + tipoisc.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + tipoisc.getFechaModificacion()
                        + "', usuario_modificacion = '" + tipoisc.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + tipoisc.getTerminalModificacion()
                        + "', ip_modificacion = '" + tipoisc.getIpModificacion() + "' "
                        + "WHERE id_tipoisc = " + tipoisc.getIdtipoisc();

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
                String qry = "UPDATE gcbusiness.tipoisc SET estado = 'I' WHERE id_tipoisc = " + id;

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
    public List<BeanTipoISC> accionListar() {
        BeanTipoISC tipoisc = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanTipoISC> listTipoISC = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipoisc ORDER BY id_tipoisc";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listTipoISC = new LinkedList<BeanTipoISC>();

                while (rs.next()) {
                    tipoisc = new BeanTipoISC();
                    tipoisc.setIdtipoisc(rs.getInt(1));
                    tipoisc.setCodigoSunat(rs.getString(2));
                    tipoisc.setDescripcion(rs.getString(3));
                    tipoisc.setAbreviatura(rs.getString(4));  
                    tipoisc.setEstado(rs.getString(5));     
                    tipoisc.setFechaInsercion(rs.getTimestamp(6));
                    tipoisc.setUsuarioInsercion(rs.getString(7));
                    tipoisc.setTerminalInsercion(rs.getString(8));
                    tipoisc.setIpInsercion(rs.getString(9));
                    tipoisc.setFechaModificacion(rs.getTimestamp(10));
                    tipoisc.setUsuarioModificacion(rs.getString(11));
                    tipoisc.setTerminalModificacion(rs.getString(12));
                    tipoisc.setIpModificacion(rs.getString(13));

                    listTipoISC.add(tipoisc);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipoisc = null;
            }
        }

        return listTipoISC;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipoisc SET estado = 'A' WHERE id_tipoisc = " + id;

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
