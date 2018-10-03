package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanFamiliaProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoFamiliaProductoImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanFamiliaProducto familiaproducto = (BeanFamiliaProducto) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.familiaproducto (descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + familiaproducto.getDescripcion().toUpperCase() + "','" + familiaproducto.getAbreviatura().toUpperCase() + "','" + familiaproducto.getEstado().toUpperCase() + "','"+familiaproducto.getFechaInsercion()
                        + "', '" + familiaproducto.getUsuarioInsercion()+"', '"+familiaproducto.getTerminalInsercion()+"', '"+familiaproducto.getIpInsercion()+"')";
                
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
    public BeanFamiliaProducto accionObtener(Integer id) {
        BeanFamiliaProducto familiaproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.familiaproducto\n"
                        + "    WHERE id_familiaproducto = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    familiaproducto = new BeanFamiliaProducto();
                    familiaproducto.setIdfamiliaproducto(rs.getInt(1));
                    familiaproducto.setDescripcion(rs.getString(2));
                    familiaproducto.setAbreviatura(rs.getString(3));  
                    familiaproducto.setEstado(rs.getString(4));     
                    familiaproducto.setFechaInsercion(rs.getTimestamp(5));
                    familiaproducto.setUsuarioInsercion(rs.getString(6));
                    familiaproducto.setTerminalInsercion(rs.getString(7));
                    familiaproducto.setIpInsercion(rs.getString(8));
                    familiaproducto.setFechaModificacion(rs.getTimestamp(9));
                    familiaproducto.setUsuarioModificacion(rs.getString(10));
                    familiaproducto.setTerminalModificacion(rs.getString(11));
                    familiaproducto.setIpModificacion(rs.getString(12));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                familiaproducto = null;
            }
        }

        return familiaproducto;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanFamiliaProducto familiaproducto = (BeanFamiliaProducto) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.familiaproducto SET descripcion = '" + familiaproducto.getDescripcion().toUpperCase()
                        + "', abreviatura = '" + familiaproducto.getAbreviatura().toUpperCase()
                        + "', estado = '" + familiaproducto.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + familiaproducto.getFechaModificacion()
                        + "', usuario_modificacion = '" + familiaproducto.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + familiaproducto.getTerminalModificacion()
                        + "', ip_modificacion = '" + familiaproducto.getIpModificacion() + "' "
                        + "WHERE id_familiaproducto = " + familiaproducto.getIdfamiliaproducto();

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
                String qry = "UPDATE gcbusiness.familiaproducto SET estado = 'I' WHERE id_familiaproducto = " + id;

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
    public List<BeanFamiliaProducto> accionListar() {
        BeanFamiliaProducto familiaproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanFamiliaProducto> listFamiliaProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.familiaproducto ORDER BY id_familiaproducto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listFamiliaProducto = new LinkedList<BeanFamiliaProducto>();

                while (rs.next()) {
                    familiaproducto = new BeanFamiliaProducto();
                    familiaproducto.setIdfamiliaproducto(rs.getInt(1));
                    familiaproducto.setDescripcion(rs.getString(2));
                    familiaproducto.setAbreviatura(rs.getString(3));  
                    familiaproducto.setEstado(rs.getString(4));     
                    familiaproducto.setFechaInsercion(rs.getTimestamp(5));
                    familiaproducto.setUsuarioInsercion(rs.getString(6));
                    familiaproducto.setTerminalInsercion(rs.getString(7));
                    familiaproducto.setIpInsercion(rs.getString(8));
                    familiaproducto.setFechaModificacion(rs.getTimestamp(9));
                    familiaproducto.setUsuarioModificacion(rs.getString(10));
                    familiaproducto.setTerminalModificacion(rs.getString(11));
                    familiaproducto.setIpModificacion(rs.getString(12));

                    listFamiliaProducto.add(familiaproducto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                familiaproducto = null;
            }
        }

        return listFamiliaProducto;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.familiaproducto SET estado = 'A' WHERE id_familiaproducto = " + id;

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
