package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanUnidadMedida;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoUnidadMedidaImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanUnidadMedida unidadmedida = (BeanUnidadMedida) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.unidadmedida (codigo_sunat,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + unidadmedida.getCodigoSunat().toUpperCase() + "','" + unidadmedida.getDescripcion().toUpperCase() + "','"  + unidadmedida.getAbreviatura().toUpperCase() + "','" + unidadmedida.getEstado().toUpperCase() + "','"+unidadmedida.getFechaInsercion()
                        + "', '" + unidadmedida.getUsuarioInsercion()+"', '"+unidadmedida.getTerminalInsercion()+"', '"+unidadmedida.getIpInsercion()+"')";
                
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
    public BeanUnidadMedida accionObtener(Integer id) {
        BeanUnidadMedida unidadmedida = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.unidadmedida\n"
                        + "    WHERE id_unidadmedida = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    unidadmedida = new BeanUnidadMedida();
                    unidadmedida.setIdunidadmedida(rs.getInt(1));
                    unidadmedida.setCodigoSunat(rs.getString(2));
                    unidadmedida.setDescripcion(rs.getString(3));
                    unidadmedida.setAbreviatura(rs.getString(4));  
                    unidadmedida.setEstado(rs.getString(5));     
                    unidadmedida.setFechaInsercion(rs.getTimestamp(6));
                    unidadmedida.setUsuarioInsercion(rs.getString(7));
                    unidadmedida.setTerminalInsercion(rs.getString(8));
                    unidadmedida.setIpInsercion(rs.getString(9));
                    unidadmedida.setFechaModificacion(rs.getTimestamp(10));
                    unidadmedida.setUsuarioModificacion(rs.getString(11));
                    unidadmedida.setTerminalModificacion(rs.getString(12));
                    unidadmedida.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                unidadmedida = null;
            }
        }

        return unidadmedida;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanUnidadMedida unidadmedida = (BeanUnidadMedida) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.unidadmedida SET descripcion = '" + unidadmedida.getDescripcion().toUpperCase()
                        + "', codigo_sunat = '" + unidadmedida.getCodigoSunat().toUpperCase()
                        + "', abreviatura = '" + unidadmedida.getAbreviatura().toUpperCase()
                        + "', estado = '" + unidadmedida.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + unidadmedida.getFechaModificacion()
                        + "', usuario_modificacion = '" + unidadmedida.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + unidadmedida.getTerminalModificacion()
                        + "', ip_modificacion = '" + unidadmedida.getIpModificacion() + "' "
                        + "WHERE id_unidadmedida = " + unidadmedida.getIdunidadmedida();

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
                String qry = "UPDATE gcbusiness.unidadmedida SET estado = 'I' WHERE id_unidadmedida = " + id;

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
    public List<BeanUnidadMedida> accionListar() {
        BeanUnidadMedida unidadmedida = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanUnidadMedida> listUnidadMedida = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.unidadmedida ORDER BY id_unidadmedida";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listUnidadMedida = new LinkedList<BeanUnidadMedida>();

                while (rs.next()) {
                    unidadmedida = new BeanUnidadMedida();
                    unidadmedida.setIdunidadmedida(rs.getInt(1));
                    unidadmedida.setCodigoSunat(rs.getString(2));
                    unidadmedida.setDescripcion(rs.getString(3));
                    unidadmedida.setAbreviatura(rs.getString(4));  
                    unidadmedida.setEstado(rs.getString(5));     
                    unidadmedida.setFechaInsercion(rs.getTimestamp(6));
                    unidadmedida.setUsuarioInsercion(rs.getString(7));
                    unidadmedida.setTerminalInsercion(rs.getString(8));
                    unidadmedida.setIpInsercion(rs.getString(9));
                    unidadmedida.setFechaModificacion(rs.getTimestamp(10));
                    unidadmedida.setUsuarioModificacion(rs.getString(11));
                    unidadmedida.setTerminalModificacion(rs.getString(12));
                    unidadmedida.setIpModificacion(rs.getString(13));

                    listUnidadMedida.add(unidadmedida);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                unidadmedida = null;
            }
        }

        return listUnidadMedida;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.unidadmedida SET estado = 'A' WHERE id_unidadmedida = " + id;

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
