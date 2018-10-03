package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanTipoPersona;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoTipoPersonaImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoPersona tipopersona = (BeanTipoPersona) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.tipopersona (descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + tipopersona.getDescripcion().toUpperCase() + "','" + tipopersona.getAbreviatura().toUpperCase() + "','" + tipopersona.getEstado().toUpperCase() + "','"+tipopersona.getFechaInsercion()
                        + "', '" + tipopersona.getUsuarioInsercion()+"', '"+tipopersona.getTerminalInsercion()+"', '"+tipopersona.getIpInsercion()+"')";
                
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
    public BeanTipoPersona accionObtener(Integer id) {
        BeanTipoPersona tipopersona = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipopersona\n"
                        + "    WHERE id_tipopersona = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    tipopersona = new BeanTipoPersona();
                    tipopersona.setIdtipopersona(rs.getInt(1));
                    tipopersona.setDescripcion(rs.getString(2));
                    tipopersona.setAbreviatura(rs.getString(3));  
                    tipopersona.setEstado(rs.getString(4));     
                    tipopersona.setFechaInsercion(rs.getTimestamp(5));
                    tipopersona.setUsuarioInsercion(rs.getString(6));
                    tipopersona.setTerminalInsercion(rs.getString(7));
                    tipopersona.setIpInsercion(rs.getString(8));
                    tipopersona.setFechaModificacion(rs.getTimestamp(9));
                    tipopersona.setUsuarioModificacion(rs.getString(10));
                    tipopersona.setTerminalModificacion(rs.getString(11));
                    tipopersona.setIpModificacion(rs.getString(12));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipopersona = null;
            }
        }

        return tipopersona;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoPersona tipopersona = (BeanTipoPersona) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipopersona SET descripcion = '" + tipopersona.getDescripcion().toUpperCase()
                        + "', abreviatura = '" + tipopersona.getAbreviatura().toUpperCase()
                        + "', estado = '" + tipopersona.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + tipopersona.getFechaModificacion()
                        + "', usuario_modificacion = '" + tipopersona.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + tipopersona.getTerminalModificacion()
                        + "', ip_modificacion = '" + tipopersona.getIpModificacion() + "' "
                        + "WHERE id_tipopersona = " + tipopersona.getIdtipopersona();

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
                String qry = "UPDATE gcbusiness.tipopersona SET estado = 'I' WHERE id_tipopersona = " + id;

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
    public List<BeanTipoPersona> accionListar() {
        BeanTipoPersona tipopersona = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanTipoPersona> listTipoPersona = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipopersona ORDER BY id_tipopersona";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listTipoPersona = new LinkedList<BeanTipoPersona>();

                while (rs.next()) {
                    tipopersona = new BeanTipoPersona();
                    tipopersona.setIdtipopersona(rs.getInt(1));
                    tipopersona.setDescripcion(rs.getString(2));
                    tipopersona.setAbreviatura(rs.getString(3));  
                    tipopersona.setEstado(rs.getString(4));     
                    tipopersona.setFechaInsercion(rs.getTimestamp(5));
                    tipopersona.setUsuarioInsercion(rs.getString(6));
                    tipopersona.setTerminalInsercion(rs.getString(7));
                    tipopersona.setIpInsercion(rs.getString(8));
                    tipopersona.setFechaModificacion(rs.getTimestamp(9));
                    tipopersona.setUsuarioModificacion(rs.getString(10));
                    tipopersona.setTerminalModificacion(rs.getString(11));
                    tipopersona.setIpModificacion(rs.getString(12));

                    listTipoPersona.add(tipopersona);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipopersona = null;
            }
        }

        return listTipoPersona;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipopersona SET estado = 'A' WHERE id_tipopersona = " + id;

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
