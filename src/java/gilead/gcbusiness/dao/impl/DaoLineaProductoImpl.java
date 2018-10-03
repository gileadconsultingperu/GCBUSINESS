package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanLineaProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoLineaProductoImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanLineaProducto lineaproducto = (BeanLineaProducto) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.lineaproducto (id_claseproducto,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ("+lineaproducto.getIdclaseproducto() +",'" + lineaproducto.getDescripcion().toUpperCase() + "','" + lineaproducto.getAbreviatura().toUpperCase() + "','" + lineaproducto.getEstado().toUpperCase() + "','"+lineaproducto.getFechaInsercion()
                        + "', '" + lineaproducto.getUsuarioInsercion()+"', '"+lineaproducto.getTerminalInsercion()+"', '"+lineaproducto.getIpInsercion()+"')";
                
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
    public BeanLineaProducto accionObtener(Integer id) {
        BeanLineaProducto lineaproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.lineaproducto\n"
                        + "    WHERE id_lineaproducto = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    lineaproducto = new BeanLineaProducto();
                    lineaproducto.setIdlineaproducto(rs.getInt(1));
                    lineaproducto.setIdclaseproducto(rs.getInt(2));
                    lineaproducto.setDescripcion(rs.getString(3));
                    lineaproducto.setAbreviatura(rs.getString(4));  
                    lineaproducto.setEstado(rs.getString(5));     
                    lineaproducto.setFechaInsercion(rs.getTimestamp(6));
                    lineaproducto.setUsuarioInsercion(rs.getString(7));
                    lineaproducto.setTerminalInsercion(rs.getString(8));
                    lineaproducto.setIpInsercion(rs.getString(9));
                    lineaproducto.setFechaModificacion(rs.getTimestamp(10));
                    lineaproducto.setUsuarioModificacion(rs.getString(11));
                    lineaproducto.setTerminalModificacion(rs.getString(12));
                    lineaproducto.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                lineaproducto = null;
            }
        }

        return lineaproducto;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanLineaProducto lineaproducto = (BeanLineaProducto) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.lineaproducto SET descripcion = '" + lineaproducto.getDescripcion().toUpperCase()
                        + "', id_claseproducto = " + lineaproducto.getIdclaseproducto()
                        + ", abreviatura = '" + lineaproducto.getAbreviatura().toUpperCase()
                        + "', estado = '" + lineaproducto.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + lineaproducto.getFechaModificacion()
                        + "', usuario_modificacion = '" + lineaproducto.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + lineaproducto.getTerminalModificacion()
                        + "', ip_modificacion = '" + lineaproducto.getIpModificacion() + "' "
                        + "WHERE id_lineaproducto = " + lineaproducto.getIdlineaproducto();

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
                String qry = "UPDATE gcbusiness.lineaproducto SET estado = 'I' WHERE id_lineaproducto = " + id;

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
    public List<BeanLineaProducto> accionListar() {
        BeanLineaProducto lineaproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanLineaProducto> listLineaProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.lineaproducto ORDER BY id_lineaproducto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listLineaProducto = new LinkedList<BeanLineaProducto>();

                while (rs.next()) {
                    lineaproducto = new BeanLineaProducto();
                    lineaproducto.setIdlineaproducto(rs.getInt(1));
                    lineaproducto.setIdclaseproducto(rs.getInt(2));
                    lineaproducto.setDescripcion(rs.getString(3));
                    lineaproducto.setAbreviatura(rs.getString(4));  
                    lineaproducto.setEstado(rs.getString(5));     
                    lineaproducto.setFechaInsercion(rs.getTimestamp(6));
                    lineaproducto.setUsuarioInsercion(rs.getString(7));
                    lineaproducto.setTerminalInsercion(rs.getString(8));
                    lineaproducto.setIpInsercion(rs.getString(9));
                    lineaproducto.setFechaModificacion(rs.getTimestamp(10));
                    lineaproducto.setUsuarioModificacion(rs.getString(11));
                    lineaproducto.setTerminalModificacion(rs.getString(12));
                    lineaproducto.setIpModificacion(rs.getString(13));

                    listLineaProducto.add(lineaproducto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                lineaproducto = null;
            }
        }

        return listLineaProducto;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.lineaproducto SET estado = 'A' WHERE id_lineaproducto = " + id;

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
