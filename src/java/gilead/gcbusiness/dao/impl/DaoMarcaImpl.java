package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanMarca;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoMarcaImpl implements DaoAccion{
    
    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanMarca marca = (BeanMarca) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.marca (descripcion,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES ('" + marca.getDescripcion().toUpperCase() + "','" + marca.getEstado().toUpperCase() + "','"+marca.getFechaInsercion()
                        + "', '" + marca.getUsuarioInsercion()+"', '"+marca.getTerminalInsercion()+"', '"+marca.getIpInsercion()+"')";
                
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
    public BeanMarca accionObtener(Integer id) {
        BeanMarca marca = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.marca\n"
                        + "    WHERE id_marca = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    marca = new BeanMarca();
                    marca.setIdmarca(rs.getInt(1));
                    marca.setDescripcion(rs.getString(2));
                    marca.setEstado(rs.getString(3));     
                    marca.setFechaInsercion(rs.getTimestamp(4));
                    marca.setUsuarioInsercion(rs.getString(5));
                    marca.setTerminalInsercion(rs.getString(6));
                    marca.setIpInsercion(rs.getString(7));
                    marca.setFechaModificacion(rs.getTimestamp(8));
                    marca.setUsuarioModificacion(rs.getString(9));
                    marca.setTerminalModificacion(rs.getString(10));
                    marca.setIpModificacion(rs.getString(11));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                marca = null;
            }
        }

        return marca;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanMarca marca = (BeanMarca) obj;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.marca SET descripcion = '" + marca.getDescripcion().toUpperCase()
                        + "', estado = '" + marca.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + marca.getFechaModificacion()
                        + "', usuario_modificacion = '" + marca.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + marca.getTerminalModificacion()
                        + "', ip_modificacion = '" + marca.getIpModificacion() + "' "
                        + "WHERE id_marca = " + marca.getIdmarca();

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
                String qry = "UPDATE gcbusiness.marca SET estado = 'I' WHERE id_marca = " + id;

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
    public List<BeanMarca> accionListar() {
        BeanMarca marca = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanMarca> listMarca = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.marca ORDER BY id_marca";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listMarca = new LinkedList<BeanMarca>();

                while (rs.next()) {
                    marca = new BeanMarca();
                    marca.setIdmarca(rs.getInt(1));
                    marca.setDescripcion(rs.getString(2));
                    marca.setEstado(rs.getString(3));     
                    marca.setFechaInsercion(rs.getTimestamp(4));
                    marca.setUsuarioInsercion(rs.getString(5));
                    marca.setTerminalInsercion(rs.getString(6));
                    marca.setIpInsercion(rs.getString(7));
                    marca.setFechaModificacion(rs.getTimestamp(8));
                    marca.setUsuarioModificacion(rs.getString(9));
                    marca.setTerminalModificacion(rs.getString(10));
                    marca.setIpModificacion(rs.getString(11));

                    listMarca.add(marca);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                marca = null;
            }
        }

        return listMarca;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.marca SET estado = 'A' WHERE id_marca = " + id;

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
