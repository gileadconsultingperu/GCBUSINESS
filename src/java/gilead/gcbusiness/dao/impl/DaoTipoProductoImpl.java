package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanTipoProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoTipoProductoImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoProducto tipoproducto = (BeanTipoProducto) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.tipoproducto (codigo_sunat,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + tipoproducto.getCodigoSunat().toUpperCase() + "','" + tipoproducto.getDescripcion().toUpperCase() + "','"  + tipoproducto.getAbreviatura().toUpperCase() + "','" + tipoproducto.getEstado().toUpperCase() + "','"+tipoproducto.getFechaInsercion()
                        + "', '" + tipoproducto.getUsuarioInsercion()+"', '"+tipoproducto.getTerminalInsercion()+"', '"+tipoproducto.getIpInsercion()+"')";
                
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
    public BeanTipoProducto accionObtener(Integer id) {
        BeanTipoProducto tipoproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipoproducto\n"
                        + "    WHERE id_tipoproducto = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    tipoproducto = new BeanTipoProducto();
                    tipoproducto.setIdtipoproducto(rs.getInt(1));
                    tipoproducto.setCodigoSunat(rs.getString(2));
                    tipoproducto.setDescripcion(rs.getString(3));
                    tipoproducto.setAbreviatura(rs.getString(4));  
                    tipoproducto.setEstado(rs.getString(5));     
                    tipoproducto.setFechaInsercion(rs.getTimestamp(6));
                    tipoproducto.setUsuarioInsercion(rs.getString(7));
                    tipoproducto.setTerminalInsercion(rs.getString(8));
                    tipoproducto.setIpInsercion(rs.getString(9));
                    tipoproducto.setFechaModificacion(rs.getTimestamp(10));
                    tipoproducto.setUsuarioModificacion(rs.getString(11));
                    tipoproducto.setTerminalModificacion(rs.getString(12));
                    tipoproducto.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipoproducto = null;
            }
        }

        return tipoproducto;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanTipoProducto tipoproducto = (BeanTipoProducto) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipoproducto SET descripcion = '" + tipoproducto.getDescripcion().toUpperCase()
                        + "', codigo_sunat = '" + tipoproducto.getCodigoSunat().toUpperCase()
                        + "', abreviatura = '" + tipoproducto.getAbreviatura().toUpperCase()
                        + "', estado = '" + tipoproducto.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + tipoproducto.getFechaModificacion()
                        + "', usuario_modificacion = '" + tipoproducto.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + tipoproducto.getTerminalModificacion()
                        + "', ip_modificacion = '" + tipoproducto.getIpModificacion() + "' "
                        + "WHERE id_tipoproducto = " + tipoproducto.getIdtipoproducto();

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
                String qry = "UPDATE gcbusiness.tipoproducto SET estado = 'I' WHERE id_tipoproducto = " + id;

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
    public List<BeanTipoProducto> accionListar() {
        BeanTipoProducto tipoproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanTipoProducto> listTipoProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.tipoproducto ORDER BY id_tipoproducto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listTipoProducto = new LinkedList<BeanTipoProducto>();

                while (rs.next()) {
                    tipoproducto = new BeanTipoProducto();
                    tipoproducto.setIdtipoproducto(rs.getInt(1));
                    tipoproducto.setCodigoSunat(rs.getString(2));
                    tipoproducto.setDescripcion(rs.getString(3));
                    tipoproducto.setAbreviatura(rs.getString(4));  
                    tipoproducto.setEstado(rs.getString(5));     
                    tipoproducto.setFechaInsercion(rs.getTimestamp(6));
                    tipoproducto.setUsuarioInsercion(rs.getString(7));
                    tipoproducto.setTerminalInsercion(rs.getString(8));
                    tipoproducto.setIpInsercion(rs.getString(9));
                    tipoproducto.setFechaModificacion(rs.getTimestamp(10));
                    tipoproducto.setUsuarioModificacion(rs.getString(11));
                    tipoproducto.setTerminalModificacion(rs.getString(12));
                    tipoproducto.setIpModificacion(rs.getString(13));

                    listTipoProducto.add(tipoproducto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                tipoproducto = null;
            }
        }

        return listTipoProducto;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.tipoproducto SET estado = 'A' WHERE id_tipoproducto = " + id;

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
