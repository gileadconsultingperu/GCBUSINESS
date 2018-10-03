package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanClaseProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoClaseProductoImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanClaseProducto claseproducto = (BeanClaseProducto) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.claseproducto (id_familiaproducto,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ("+claseproducto.getIdfamiliaproducto() +",'" + claseproducto.getDescripcion().toUpperCase() + "','" + claseproducto.getAbreviatura().toUpperCase() + "','" + claseproducto.getEstado().toUpperCase() + "','"+claseproducto.getFechaInsercion()
                        + "', '" + claseproducto.getUsuarioInsercion()+"', '"+claseproducto.getTerminalInsercion()+"', '"+claseproducto.getIpInsercion()+"')";
                
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
    public BeanClaseProducto accionObtener(Integer id) {
        BeanClaseProducto claseproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.claseproducto\n"
                        + "    WHERE id_claseproducto = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    claseproducto = new BeanClaseProducto();
                    claseproducto.setIdclaseproducto(rs.getInt(1));
                    claseproducto.setIdfamiliaproducto(rs.getInt(2));
                    claseproducto.setDescripcion(rs.getString(3));
                    claseproducto.setAbreviatura(rs.getString(4));  
                    claseproducto.setEstado(rs.getString(5));     
                    claseproducto.setFechaInsercion(rs.getTimestamp(6));
                    claseproducto.setUsuarioInsercion(rs.getString(7));
                    claseproducto.setTerminalInsercion(rs.getString(8));
                    claseproducto.setIpInsercion(rs.getString(9));
                    claseproducto.setFechaModificacion(rs.getTimestamp(10));
                    claseproducto.setUsuarioModificacion(rs.getString(11));
                    claseproducto.setTerminalModificacion(rs.getString(12));
                    claseproducto.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                claseproducto = null;
            }
        }

        return claseproducto;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanClaseProducto claseproducto = (BeanClaseProducto) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.claseproducto SET descripcion = '" + claseproducto.getDescripcion().toUpperCase()
                        + "', id_familiaproducto = " + claseproducto.getIdfamiliaproducto()
                        + ", abreviatura = '" + claseproducto.getAbreviatura().toUpperCase()
                        + "', estado = '" + claseproducto.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + claseproducto.getFechaModificacion()
                        + "', usuario_modificacion = '" + claseproducto.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + claseproducto.getTerminalModificacion()
                        + "', ip_modificacion = '" + claseproducto.getIpModificacion() + "' "
                        + "WHERE id_claseproducto = " + claseproducto.getIdclaseproducto();

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
                String qry = "UPDATE gcbusiness.claseproducto SET estado = 'I' WHERE id_claseproducto = " + id;

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
    public List<BeanClaseProducto> accionListar() {
        BeanClaseProducto claseproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanClaseProducto> listClaseProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.claseproducto ORDER BY id_claseproducto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listClaseProducto = new LinkedList<BeanClaseProducto>();

                while (rs.next()) {
                    claseproducto = new BeanClaseProducto();
                    claseproducto.setIdclaseproducto(rs.getInt(1));
                    claseproducto.setIdfamiliaproducto(rs.getInt(2));
                    claseproducto.setDescripcion(rs.getString(3));
                    claseproducto.setAbreviatura(rs.getString(4));  
                    claseproducto.setEstado(rs.getString(5));     
                    claseproducto.setFechaInsercion(rs.getTimestamp(6));
                    claseproducto.setUsuarioInsercion(rs.getString(7));
                    claseproducto.setTerminalInsercion(rs.getString(8));
                    claseproducto.setIpInsercion(rs.getString(9));
                    claseproducto.setFechaModificacion(rs.getTimestamp(10));
                    claseproducto.setUsuarioModificacion(rs.getString(11));
                    claseproducto.setTerminalModificacion(rs.getString(12));
                    claseproducto.setIpModificacion(rs.getString(13));

                    listClaseProducto.add(claseproducto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                claseproducto = null;
            }
        }

        return listClaseProducto;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.claseproducto SET estado = 'A' WHERE id_claseproducto = " + id;

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
