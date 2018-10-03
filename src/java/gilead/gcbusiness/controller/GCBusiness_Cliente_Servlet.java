package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoClienteImpl;
import gilead.gcbusiness.dao.impl.DaoTipoDocumentoImpl;
import gilead.gcbusiness.dao.impl.DaoTipoPersonaImpl;
import gilead.gcbusiness.dao.impl.DaoVendedorImpl;
import gilead.gcbusiness.model.BeanCliente;
import gilead.gcbusiness.model.BeanSucursal;
import gilead.gcbusiness.model.BeanTipoDocumento;
import gilead.gcbusiness.model.BeanTipoPersona;
import gilead.gcbusiness.model.BeanVendedor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Cliente_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION CLIENTE");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoClienteImpl daoClienteImpl = new DaoClienteImpl();
                DaoTipoDocumentoImpl daoTipoDocumentoImpl = new DaoTipoDocumentoImpl();
                DaoTipoPersonaImpl daoTipoPersonaImpl = new DaoTipoPersonaImpl();
                DaoVendedorImpl daoVendedorImpl = new DaoVendedorImpl();
                List<BeanCliente> listClientes = daoClienteImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listClientes.size(); i++) {
                    String acciones = "";
                   
                    BeanTipoDocumento tipodocumento = null;                  
                    tipodocumento = daoTipoDocumentoImpl.accionObtener(listClientes.get(i).getIdtipodocumento());
                     
                    BeanTipoPersona tipopersona = null;                  
                    tipopersona = daoTipoPersonaImpl.accionObtener(listClientes.get(i).getIdtipopersona());
                    
                    BeanVendedor vendedor = null;                  
                    vendedor = daoVendedorImpl.accionObtener(listClientes.get(i).getIdtipodocumento());
                    
                    if (listClientes.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listClientes.get(i).getIdcliente() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listClientes.get(i).getIdcliente() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listClientes.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listClientes.get(i).getIdcliente() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listClientes.get(i).getIdcliente() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("nombre", listClientes.get(i).getNombre());
                    obj.put("tipodocumento", tipodocumento.getAbreviatura());
                    obj.put("numerodocumento", listClientes.get(i).getNumerodocumento());
                    obj.put("direccion", listClientes.get(i).getNumerodocumento());
                    obj.put("telefono", listClientes.get(i).getNumerodocumento());
                    obj.put("correo", listClientes.get(i).getNumerodocumento());
                    obj.put("tipopersona", listClientes.get(i).getNumerodocumento());
                    obj.put("vendedor", vendedor.getDescripcion());
                    obj.put("estado", "A".equals(listClientes.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR CLIENTE");
            try (PrintWriter out = response.getWriter()) {
                Integer idtipodocumento = Integer.parseInt(request.getParameter("tipodocumento"));
                String numerodocumento = request.getParameter("numerodocumento");
                String nombre = request.getParameter("nombre");
                Integer idtipopersona = Integer.parseInt(request.getParameter("tipopersona"));
                Integer idvendedor = Integer.parseInt(request.getParameter("vendedor"));
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");
                String codigoubidistrito = request.getParameter("codigoubidistrito");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanCliente nuevo_cliente = new BeanCliente(null, idtipodocumento, numerodocumento, nombre, idtipopersona, idvendedor, direccion, telefono, correo, codigoubidistrito, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoClienteImpl daoClienteImpl = new DaoClienteImpl();
                String msg = daoClienteImpl.accionCrear(nuevo_cliente);
                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL CLIENTE</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        }else if (opcion.equals("actualizar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcliente = Integer.parseInt(request.getParameter("idcliente"));
                Integer idtipodocumento = Integer.parseInt(request.getParameter("tipodocumento"));
                String numerodocumento = request.getParameter("numerodocumento");
                String nombre = request.getParameter("nombre");
                Integer idtipopersona = Integer.parseInt(request.getParameter("tipopersona"));
                Integer idvendedor = Integer.parseInt(request.getParameter("vendedor"));
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");
                String codigoubidistrito = request.getParameter("codigoubidistrito");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanCliente nuevo_cliente = new BeanCliente(idcliente, idtipodocumento, numerodocumento, nombre, idtipopersona, idvendedor, direccion, telefono, correo, codigoubidistrito, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoClienteImpl daoClienteImpl = new DaoClienteImpl();
                String msg = daoClienteImpl.accionActualizar(nuevo_cliente);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE EL CLIENTE</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        } else if (opcion.equals("buscar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcliente = Integer.parseInt(request.getParameter("idcliente"));
                DaoClienteImpl daoClienteImpl = new DaoClienteImpl();
                BeanCliente beanCliente = (BeanCliente) daoClienteImpl.accionObtener(idcliente);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idcliente", beanCliente.getIdcliente());
                obj.put("nombre", beanCliente.getNombre());
                obj.put("tipodocumento", beanCliente.getIdtipodocumento());
                obj.put("numerodocumento", beanCliente.getNumerodocumento());
                obj.put("direccion", beanCliente.getDireccion());
                obj.put("telefono", beanCliente.getTelefono());
                obj.put("correo", beanCliente.getCorreo());
                obj.put("tipopersona", beanCliente.getIdtipopersona());
                obj.put("vendedor", beanCliente.getIdvendedor());
                obj.put("codigoubidistrito", beanCliente.getCodigoubidistrito());
                obj.put("estado", beanCliente.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcliente = Integer.parseInt(request.getParameter("idcliente"));
                DaoClienteImpl daoClienteImpl = new DaoClienteImpl();
                String msg = daoClienteImpl.accionEliminar(idcliente);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE EL CLIENTE</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        } else if (opcion.equals("activar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcliente = Integer.parseInt(request.getParameter("idcliente"));
                DaoClienteImpl daoClienteImpl = new DaoClienteImpl();
                String msg = daoClienteImpl.accionActivar(idcliente);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE EL CLIENTE</em>\" ";
                } else {
                    json = "{ \"mensaje\":\"<em>ERROR AL EJECUTAR LA CONSULTA</em>\" ";
                    json += ",";
                    json += " \"html\":\"<div class='alert alert-danger'><button class='close' data-dismiss='alert'><i class='ace-icon fa fa-times'></i></button><span class='ace-icon fa fa-hand-o-right'></span> " + msg.replace("\n", "") + "</div>\" ";
                }

                json += "}";
                System.out.println(json);
                out.print(json);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
