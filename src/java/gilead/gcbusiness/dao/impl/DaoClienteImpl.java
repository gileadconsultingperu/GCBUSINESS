/**
 * Compañia            : Gilead Consulting S.A.C.
 * Sistema             : GC-Business
 * Módulo              : Seguridad
 * Nombre              : DaoClienteImpl.java
 * Versión             : 1.0
 * Fecha Creación      : 21-08-2018
 * Autor Creación      : Pablo Jimenez Aguado
 * Uso                 : Clase que accede a los datos de la tabla cliente
 */
package gilead.gcbusiness.dao.impl;

import gilead.gcbusiness.dao.DaoAccion;
import gilead.gcbusiness.model.BeanCliente;
import gilead.gcbusiness.sql.ConectaDb;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class DaoClienteImpl implements DaoAccion {

    @Override
    public String accionCrear(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        BeanCliente cliente = (BeanCliente) obj;

        if (cn != null) {
            try {
                String qry = "INSERT INTO gcbusiness.cliente (id_tipodocumento,numero_documento,nombre,id_tipopersona,id_vendedor,direccion,telefono,correo,codigo_ubidistrito,estado,fecha_insercion,usuario_insercion,terminal_insercion,ip_insercion) "
                        + "VALUES (" + cliente.getIdtipodocumento() + ",'" + cliente.getNumerodocumento().toUpperCase() + "','" + cliente.getNombre().toUpperCase() + "'," + cliente.getIdtipopersona() + "," + cliente.getIdvendedor()
                        + ",'" + cliente.getDireccion().toUpperCase() + "','" + cliente.getTelefono().toUpperCase() + "','" + cliente.getCorreo().toUpperCase() + "','" + cliente.getCodigoubidistrito().toUpperCase() + "','" + cliente.getEstado().toUpperCase() + "','" + cliente.getFechaInsercion()
                        + "', '" + cliente.getUsuarioInsercion() + "', '" + cliente.getTerminalInsercion() + "', '" + cliente.getIpInsercion() + "')";

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
    public BeanCliente accionObtener(Integer id) {
        BeanCliente cliente = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.cliente\n"
                        + "    WHERE id_cliente = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, id);

                rs = st.executeQuery();

                while (rs.next()) {
                    cliente = new BeanCliente();
                    cliente.setIdcliente(rs.getInt(1));
                    cliente.setIdtipodocumento(rs.getInt(2));
                    cliente.setNumerodocumento(rs.getString(3));
                    cliente.setNombre(rs.getString(4));
                    cliente.setIdtipopersona(rs.getInt(5));
                    cliente.setIdvendedor(rs.getInt(6));
                    cliente.setDireccion(rs.getString(7));
                    cliente.setTelefono(rs.getString(8));
                    cliente.setCorreo(rs.getString(9));
                    cliente.setCodigoubidistrito(rs.getString(10));
                    cliente.setEstado(rs.getString(11));
                    cliente.setFechaInsercion(rs.getTimestamp(12));
                    cliente.setUsuarioInsercion(rs.getString(13));
                    cliente.setTerminalInsercion(rs.getString(14));
                    cliente.setIpInsercion(rs.getString(15));
                    cliente.setFechaModificacion(rs.getTimestamp(16));
                    cliente.setUsuarioModificacion(rs.getString(17));
                    cliente.setTerminalModificacion(rs.getString(18));
                    cliente.setIpModificacion(rs.getString(19));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cliente = null;
            }
        }

        return cliente;
    }

    public BeanCliente accionObtenerExistencia(Integer idtipodocumento, String numerodocumento) {
        BeanCliente cliente = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.cliente\n"
                        + "    WHERE id_tipodocumento = ? AND numero_documento = ?";

                st = cn.prepareStatement(qry);
                st.setInt(1, idtipodocumento);
                st.setString(2, numerodocumento);

                rs = st.executeQuery();

                while (rs.next()) {
                    cliente = new BeanCliente();
                    cliente.setIdcliente(rs.getInt(1));
                    cliente.setIdtipodocumento(rs.getInt(2));
                    cliente.setNumerodocumento(rs.getString(3));
                    cliente.setNombre(rs.getString(4));
                    cliente.setIdtipopersona(rs.getInt(5));
                    cliente.setIdvendedor(rs.getInt(6));
                    cliente.setDireccion(rs.getString(7));
                    cliente.setTelefono(rs.getString(8));
                    cliente.setCorreo(rs.getString(9));
                    cliente.setCodigoubidistrito(rs.getString(10));
                    cliente.setEstado(rs.getString(11));
                    cliente.setFechaInsercion(rs.getTimestamp(12));
                    cliente.setUsuarioInsercion(rs.getString(13));
                    cliente.setTerminalInsercion(rs.getString(14));
                    cliente.setIpInsercion(rs.getString(15));
                    cliente.setFechaModificacion(rs.getTimestamp(16));
                    cliente.setUsuarioModificacion(rs.getString(17));
                    cliente.setTerminalModificacion(rs.getString(18));
                    cliente.setIpModificacion(rs.getString(19));
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cliente = null;
            }
        }

        return cliente;
    }

    @Override
    public String accionActualizar(Object obj) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;
        PreparedStatement ps = null;

        BeanCliente cliente = (BeanCliente) obj;

        if (cn != null) {
            try {
                /*String qry = "UPDATE gcbusiness.cliente SET nombre = '" + cliente.getNombre().toUpperCase()
                        + "', id_tipodocumento = " + cliente.getIdtipodocumento()
                        + ", numero_documento = '" + cliente.getNumerodocumento().toUpperCase()
                        + "', id_tipopersona = " + cliente.getIdtipopersona()
                        + ", id_vendedor = " + cliente.getIdvendedor()
                        + ", direccion = '" + cliente.getDireccion().toUpperCase()
                        + "', telefono = '" + cliente.getTelefono().toUpperCase()
                        + "', correo = '" + cliente.getCorreo().toUpperCase()
                        + "', codigo_ubidistrito = '" + cliente.getCodigoubidistrito().toUpperCase()
                        + "', estado = '" + cliente.getEstado().toUpperCase()
                        + "', fecha_modificacion = '" + cliente.getFechaModificacion()
                        + "', usuario_modificacion = '" + cliente.getUsuarioModificacion()
                        + "', terminal_modificacion = '" + cliente.getTerminalModificacion()
                        + "', ip_modificacion = '" + cliente.getIpModificacion() + "' "
                        + "WHERE id_cliente = " + cliente.getIdcliente();*/

                String qry = "UPDATE gcbusiness.cliente SET nombre = ?"
                        + ", id_tipodocumento = ?"
                        + ", numero_documento = ?"
                        + ", id_tipopersona = ?"
                        + ", id_vendedor = ?"
                        + ", direccion = ?"
                        + ", telefono = ?"
                        + ", correo = ?"
                        + ", codigo_ubidistrito = ?"
                        + ", estado = ?"
                        + ", fecha_modificacion = ?"
                        + ", usuario_modificacion = ?"
                        + ", terminal_modificacion = ?"
                        + ", ip_modificacion = ?"
                        + " WHERE id_cliente = ?";

                ps = cn.prepareStatement(qry);
                ps.setString(1, cliente.getNombre().toUpperCase());
                ps.setInt(2, cliente.getIdtipodocumento());
                ps.setString(3, cliente.getNumerodocumento().toUpperCase());
                ps.setInt(4, cliente.getIdtipopersona());
                ps.setInt(5, cliente.getIdvendedor());
                ps.setString(6, cliente.getDireccion().toUpperCase());
                ps.setString(7, cliente.getTelefono().toUpperCase());
                ps.setString(8, cliente.getCorreo().toUpperCase());
                ps.setString(9, cliente.getCodigoubidistrito().toUpperCase());
                ps.setString(10, cliente.getEstado().toUpperCase());
                ps.setTimestamp(11, cliente.getFechaModificacion());
                ps.setString(12, cliente.getUsuarioModificacion());
                ps.setString(13, cliente.getTerminalModificacion());
                ps.setString(14, cliente.getIpModificacion());
                ps.setInt(15, cliente.getIdcliente());

                int n = ps.executeUpdate();
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
                String qry = "UPDATE gcbusiness.cliente SET estado = 'I' WHERE id_cliente = " + id;

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
    public List<BeanCliente> accionListar() {
        BeanCliente cliente = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanCliente> listCliente = null;

        if (cn != null) {
            try {
                String qry = "SELECT *\n"
                        + "	FROM gcbusiness.cliente ORDER BY id_cliente";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listCliente = new LinkedList<BeanCliente>();

                while (rs.next()) {
                    cliente = new BeanCliente();
                    cliente.setIdcliente(rs.getInt(1));
                    cliente.setIdtipodocumento(rs.getInt(2));
                    cliente.setNumerodocumento(rs.getString(3));
                    cliente.setNombre(rs.getString(4));
                    cliente.setIdtipopersona(rs.getInt(5));
                    cliente.setIdvendedor(rs.getInt(6));
                    cliente.setDireccion(rs.getString(7));
                    cliente.setTelefono(rs.getString(8));
                    cliente.setCorreo(rs.getString(9));
                    cliente.setCodigoubidistrito(rs.getString(10));
                    cliente.setEstado(rs.getString(11));
                    cliente.setFechaInsercion(rs.getTimestamp(12));
                    cliente.setUsuarioInsercion(rs.getString(13));
                    cliente.setTerminalInsercion(rs.getString(14));
                    cliente.setIpInsercion(rs.getString(15));
                    cliente.setFechaModificacion(rs.getTimestamp(16));
                    cliente.setUsuarioModificacion(rs.getString(17));
                    cliente.setTerminalModificacion(rs.getString(18));
                    cliente.setIpModificacion(rs.getString(19));

                    listCliente.add(cliente);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cliente = null;
            }
        }

        return listCliente;
    }

    @Override
    public String accionActivar(Integer id) {
        String msg = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        Statement st = null;

        if (cn != null) {
            try {
                String qry = "UPDATE gcbusiness.cliente SET estado = 'A' WHERE id_cliente = " + id;

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

    public List<BeanCliente> accionListarClientes(String tipopersona, String vendedor) {
        BeanCliente cliente = null;

        ConectaDb db = new ConectaDb();
        Connection cn = db.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;

        List<BeanCliente> listCliente = null;

        if (cn != null) {
            try {
                String qry = "SELECT *"
                        + " FROM gcbusiness.cliente"
                        + " WHERE 1 = 1";

                if (!tipopersona.equals("0")) {
                    qry += " AND id_tipopersona = " + tipopersona;
                }
                if (!vendedor.equals("0")) {
                    qry += " AND id_vendedor = " + vendedor;
                }

                qry += " ORDER BY id_cliente DESC";

                st = cn.prepareStatement(qry);

                rs = st.executeQuery();

                listCliente = new LinkedList<BeanCliente>();

                while (rs.next()) {
                    cliente = new BeanCliente();
                    cliente.setIdcliente(rs.getInt(1));
                    cliente.setIdtipodocumento(rs.getInt(2));
                    cliente.setNumerodocumento(rs.getString(3));
                    cliente.setNombre(rs.getString(4));
                    cliente.setIdtipopersona(rs.getInt(5));
                    cliente.setIdvendedor(rs.getInt(6));
                    cliente.setDireccion(rs.getString(7));
                    cliente.setTelefono(rs.getString(8));
                    cliente.setCorreo(rs.getString(9));
                    cliente.setCodigoubidistrito(rs.getString(10));
                    cliente.setEstado(rs.getString(11));
                    cliente.setFechaInsercion(rs.getTimestamp(12));
                    cliente.setUsuarioInsercion(rs.getString(13));
                    cliente.setTerminalInsercion(rs.getString(14));
                    cliente.setIpInsercion(rs.getString(15));
                    cliente.setFechaModificacion(rs.getTimestamp(16));
                    cliente.setUsuarioModificacion(rs.getString(17));
                    cliente.setTerminalModificacion(rs.getString(18));
                    cliente.setIpModificacion(rs.getString(19));

                    listCliente.add(cliente);
                }

                cn.close();

            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
                cliente = null;
            }
        }

        return listCliente;
    }

}
