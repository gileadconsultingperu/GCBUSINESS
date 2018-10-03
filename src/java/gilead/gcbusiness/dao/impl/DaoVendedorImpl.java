package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanVendedor;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoVendedorImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanVendedor vendedor = (BeanVendedor) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.vendedor (descripcion,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + vendedor.getDescripcion().toUpperCase() + "','" + vendedor.getEstado().toUpperCase() + "','"+vendedor.getFechaInsercion()
                        + "', '" + vendedor.getUsuarioInsercion()+"', '"+vendedor.getTerminalInsercion()+"', '"+vendedor.getIpInsercion()+"')";
                
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
    public BeanVendedor accionObtener(Integer id) {
        BeanVendedor vendedor = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.vendedor\n"
                        + "    WHERE id_vendedor = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    vendedor = new BeanVendedor();
                    vendedor.setIdvendedor(rs.getInt(1));
                    vendedor.setDescripcion(rs.getString(2));
                    vendedor.setEstado(rs.getString(3));     
                    vendedor.setFechaInsercion(rs.getTimestamp(4));
                    vendedor.setUsuarioInsercion(rs.getString(5));
                    vendedor.setTerminalInsercion(rs.getString(6));
                    vendedor.setIpInsercion(rs.getString(7));
                    vendedor.setFechaModificacion(rs.getTimestamp(8));
                    vendedor.setUsuarioModificacion(rs.getString(9));
                    vendedor.setTerminalModificacion(rs.getString(10));
                    vendedor.setIpModificacion(rs.getString(11));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                vendedor = null;
            }
        }

        return vendedor;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanVendedor vendedor = (BeanVendedor) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.vendedor SET descripcion = '" + vendedor.getDescripcion().toUpperCase()
                        + "', estado = '" + vendedor.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + vendedor.getFechaModificacion()
                        + "', usuario_modificacion = '" + vendedor.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + vendedor.getTerminalModificacion()
                        + "', ip_modificacion = '" + vendedor.getIpModificacion() + "' "
                        + "WHERE id_vendedor = " + vendedor.getIdvendedor();

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
                String qry = "UPDATE gcbusiness.vendedor SET estado = 'I' WHERE id_vendedor = " + id;

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
    public List<BeanVendedor> accionListar() {
        BeanVendedor vendedor = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanVendedor> listVendedor = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.vendedor ORDER BY id_vendedor";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listVendedor = new LinkedList<BeanVendedor>();

                while (rs.next()) {
                    vendedor = new BeanVendedor();
                    vendedor.setIdvendedor(rs.getInt(1));
                    vendedor.setDescripcion(rs.getString(2));
                    vendedor.setEstado(rs.getString(3));     
                    vendedor.setFechaInsercion(rs.getTimestamp(4));
                    vendedor.setUsuarioInsercion(rs.getString(5));
                    vendedor.setTerminalInsercion(rs.getString(6));
                    vendedor.setIpInsercion(rs.getString(7));
                    vendedor.setFechaModificacion(rs.getTimestamp(8));
                    vendedor.setUsuarioModificacion(rs.getString(9));
                    vendedor.setTerminalModificacion(rs.getString(10));
                    vendedor.setIpModificacion(rs.getString(11));

                    listVendedor.add(vendedor);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                vendedor = null;
            }
        }

        return listVendedor;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.vendedor SET estado = 'A' WHERE id_vendedor = " + id;

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
