package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanTipoDocumento;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoTipoDocumentoImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoDocumento tipodocumento = (BeanTipoDocumento) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.tipodocumento (codigo_sunat,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + tipodocumento.getDescripcion().toUpperCase() + "','" + tipodocumento.getAbreviatura().toUpperCase() + "','" + tipodocumento.getEstado().toUpperCase() + "','"+tipodocumento.getFechaInsercion()
                        + "', '" + tipodocumento.getUsuarioInsercion()+"', '"+tipodocumento.getTerminalInsercion()+"', '"+tipodocumento.getIpInsercion()+"')";
                
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
    public BeanTipoDocumento accionObtener(Integer id) {
        BeanTipoDocumento tipodocumento = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipodocumento\n"
                        + "    WHERE id_tipodocumento = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    tipodocumento = new BeanTipoDocumento();
                    tipodocumento.setIdtipodocumento(rs.getInt(1));
                    tipodocumento.setCodigoSunat(rs.getString(2));
                    tipodocumento.setDescripcion(rs.getString(3));
                    tipodocumento.setAbreviatura(rs.getString(4));  
                    tipodocumento.setEstado(rs.getString(5));     
                    tipodocumento.setFechaInsercion(rs.getTimestamp(6));
                    tipodocumento.setUsuarioInsercion(rs.getString(7));
                    tipodocumento.setTerminalInsercion(rs.getString(8));
                    tipodocumento.setIpInsercion(rs.getString(9));
                    tipodocumento.setFechaModificacion(rs.getTimestamp(10));
                    tipodocumento.setUsuarioModificacion(rs.getString(11));
                    tipodocumento.setTerminalModificacion(rs.getString(12));
                    tipodocumento.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipodocumento = null;
            }
        }

        return tipodocumento;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoDocumento tipodocumento = (BeanTipoDocumento) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipodocumento SET descripcion = '" + tipodocumento.getDescripcion().toUpperCase()
                        + "', codigo_sunat = '" + tipodocumento.getCodigoSunat().toUpperCase()
                        + "', abreviatura = '" + tipodocumento.getAbreviatura().toUpperCase()
                        + "', estado = '" + tipodocumento.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + tipodocumento.getFechaModificacion()
                        + "', usuario_modificacion = '" + tipodocumento.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + tipodocumento.getTerminalModificacion()
                        + "', ip_modificacion = '" + tipodocumento.getIpModificacion() + "' "
                        + "WHERE id_tipodocumento = " + tipodocumento.getIdtipodocumento();

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
                String qry = "UPDATE gcbusiness.tipodocumento SET estado = 'I' WHERE id_tipodocumento = " + id;

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
    public List<BeanTipoDocumento> accionListar() {
        BeanTipoDocumento tipodocumento = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanTipoDocumento> listTipoDocumento = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipodocumento ORDER BY id_tipodocumento";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listTipoDocumento = new LinkedList<BeanTipoDocumento>();

                while (rs.next()) {
                    tipodocumento = new BeanTipoDocumento();
                    tipodocumento.setIdtipodocumento(rs.getInt(1));
                    tipodocumento.setCodigoSunat(rs.getString(2));
                    tipodocumento.setDescripcion(rs.getString(3));
                    tipodocumento.setAbreviatura(rs.getString(4));  
                    tipodocumento.setEstado(rs.getString(5));     
                    tipodocumento.setFechaInsercion(rs.getTimestamp(6));
                    tipodocumento.setUsuarioInsercion(rs.getString(7));
                    tipodocumento.setTerminalInsercion(rs.getString(8));
                    tipodocumento.setIpInsercion(rs.getString(9));
                    tipodocumento.setFechaModificacion(rs.getTimestamp(10));
                    tipodocumento.setUsuarioModificacion(rs.getString(11));
                    tipodocumento.setTerminalModificacion(rs.getString(12));
                    tipodocumento.setIpModificacion(rs.getString(13));

                    listTipoDocumento.add(tipodocumento);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipodocumento = null;
            }
        }

        return listTipoDocumento;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipodocumento SET estado = 'A' WHERE id_tipodocumento = " + id;

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
