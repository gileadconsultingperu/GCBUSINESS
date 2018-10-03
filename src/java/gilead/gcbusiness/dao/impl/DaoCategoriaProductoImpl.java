package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanCategoriaProducto;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoCategoriaProductoImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanCategoriaProducto categoriaproducto = (BeanCategoriaProducto) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.categoriaproducto (id_lineaproducto,descripcion,abreviatura,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ("+categoriaproducto.getIdlineaproducto() +",'" + categoriaproducto.getDescripcion().toUpperCase() + "','" + categoriaproducto.getAbreviatura().toUpperCase() + "','" + categoriaproducto.getEstado().toUpperCase() + "','"+categoriaproducto.getFechaInsercion()
                        + "', '" + categoriaproducto.getUsuarioInsercion()+"', '"+categoriaproducto.getTerminalInsercion()+"', '"+categoriaproducto.getIpInsercion()+"')";
                
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
    public BeanCategoriaProducto accionObtener(Integer id) {
        BeanCategoriaProducto categoriaproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.categoriaproducto\n"
                        + "    WHERE id_categoriaproducto = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    categoriaproducto = new BeanCategoriaProducto();
                    categoriaproducto.setIdcategoriaproducto(rs.getInt(1));
                    categoriaproducto.setIdlineaproducto(rs.getInt(2));
                    categoriaproducto.setDescripcion(rs.getString(3));
                    categoriaproducto.setAbreviatura(rs.getString(4));  
                    categoriaproducto.setEstado(rs.getString(5));     
                    categoriaproducto.setFechaInsercion(rs.getTimestamp(6));
                    categoriaproducto.setUsuarioInsercion(rs.getString(7));
                    categoriaproducto.setTerminalInsercion(rs.getString(8));
                    categoriaproducto.setIpInsercion(rs.getString(9));
                    categoriaproducto.setFechaModificacion(rs.getTimestamp(10));
                    categoriaproducto.setUsuarioModificacion(rs.getString(11));
                    categoriaproducto.setTerminalModificacion(rs.getString(12));
                    categoriaproducto.setIpModificacion(rs.getString(13));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                categoriaproducto = null;
            }
        }

        return categoriaproducto;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanCategoriaProducto categoriaproducto = (BeanCategoriaProducto) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.categoriaproducto SET descripcion = '" + categoriaproducto.getDescripcion().toUpperCase()
                        + "', id_lineaproducto = " + categoriaproducto.getIdlineaproducto()
                        + ", abreviatura = '" + categoriaproducto.getAbreviatura().toUpperCase()
                        + "', estado = '" + categoriaproducto.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + categoriaproducto.getFechaModificacion()
                        + "', usuario_modificacion = '" + categoriaproducto.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + categoriaproducto.getTerminalModificacion()
                        + "', ip_modificacion = '" + categoriaproducto.getIpModificacion() + "' "
                        + "WHERE id_categoriaproducto = " + categoriaproducto.getIdcategoriaproducto();

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
                String qry = "UPDATE gcbusiness.categoriaproducto SET estado = 'I' WHERE id_categoriaproducto = " + id;

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
    public List<BeanCategoriaProducto> accionListar() {
        BeanCategoriaProducto categoriaproducto = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanCategoriaProducto> listCategoriaProducto = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.categoriaproducto ORDER BY id_categoriaproducto";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listCategoriaProducto = new LinkedList<BeanCategoriaProducto>();

                while (rs.next()) {
                    categoriaproducto = new BeanCategoriaProducto();
                    categoriaproducto.setIdcategoriaproducto(rs.getInt(1));
                    categoriaproducto.setIdlineaproducto(rs.getInt(2));
                    categoriaproducto.setDescripcion(rs.getString(3));
                    categoriaproducto.setAbreviatura(rs.getString(4));  
                    categoriaproducto.setEstado(rs.getString(5));     
                    categoriaproducto.setFechaInsercion(rs.getTimestamp(6));
                    categoriaproducto.setUsuarioInsercion(rs.getString(7));
                    categoriaproducto.setTerminalInsercion(rs.getString(8));
                    categoriaproducto.setIpInsercion(rs.getString(9));
                    categoriaproducto.setFechaModificacion(rs.getTimestamp(10));
                    categoriaproducto.setUsuarioModificacion(rs.getString(11));
                    categoriaproducto.setTerminalModificacion(rs.getString(12));
                    categoriaproducto.setIpModificacion(rs.getString(13));

                    listCategoriaProducto.add(categoriaproducto);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                categoriaproducto = null;
            }
        }

        return listCategoriaProducto;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.categoriaproducto SET estado = 'A' WHERE id_categoriaproducto = " + id;

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
