package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoProveedorImpl;
import gilead.gcbusiness.dao.impl.DaoTipoDocumentoImpl;
import gilead.gcbusiness.dao.impl.DaoTipoPersonaImpl;
import gilead.gcbusiness.model.BeanProveedor;
import gilead.gcbusiness.model.BeanTipoDocumento;
import gilead.gcbusiness.model.BeanTipoPersona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Proveedor_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION PROVEEDOR");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoProveedorImpl daoProveedorImpl = new DaoProveedorImpl();
                DaoTipoDocumentoImpl daoTipoDocumentoImpl = new DaoTipoDocumentoImpl();
                DaoTipoPersonaImpl daoTipoPersonaImpl = new DaoTipoPersonaImpl();
                List<BeanProveedor> listProveedors = daoProveedorImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listProveedors.size(); i++) {
                    String acciones = "";
                   
                    BeanTipoDocumento tipodocumento = null;                  
                    tipodocumento = daoTipoDocumentoImpl.accionObtener(listProveedors.get(i).getIdtipodocumento());
                     
                    BeanTipoPersona tipopersona = null;                  
                    tipopersona = daoTipoPersonaImpl.accionObtener(listProveedors.get(i).getIdtipopersona());

                    if (listProveedors.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProveedors.get(i).getIdproveedor() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listProveedors.get(i).getIdproveedor() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listProveedors.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProveedors.get(i).getIdproveedor() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listProveedors.get(i).getIdproveedor() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("nombre", listProveedors.get(i).getNombre());
                    obj.put("tipodocumento", tipodocumento.getAbreviatura());
                    obj.put("numerodocumento", listProveedors.get(i).getNumerodocumento());
                    obj.put("direccion", listProveedors.get(i).getDireccion());
                    obj.put("telefono", listProveedors.get(i).getTelefono());
                    obj.put("correo", listProveedors.get(i).getCorreo());
                    obj.put("tipopersona", tipopersona.getDescripcion());
                    obj.put("estado", "A".equals(listProveedors.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR PROVEEDOR");
            try (PrintWriter out = response.getWriter()) {
                Integer idtipodocumento = Integer.parseInt(request.getParameter("idtipodocumento"));
                String numerodocumento = request.getParameter("numerodocumento");
                String nombre = request.getParameter("nombre");
                Integer idtipopersona = Integer.parseInt(request.getParameter("idtipopersona"));
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");
                String codigoubidistrito = request.getParameter("codigoubidistrito");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanProveedor nuevo_proveedor = new BeanProveedor(null, idtipodocumento, numerodocumento, nombre, idtipopersona, direccion, telefono, correo, codigoubidistrito, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoProveedorImpl daoProveedorImpl = new DaoProveedorImpl();
                String msg = daoProveedorImpl.accionCrear(nuevo_proveedor);
                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL PROVEEDOR</em>\" ";
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
                Integer idproveedor = Integer.parseInt(request.getParameter("idproveedor"));
                Integer idtipodocumento = Integer.parseInt(request.getParameter("idtipodocumento"));
                String numerodocumento = request.getParameter("numerodocumento");
                String nombre = request.getParameter("nombre");
                Integer idtipopersona = Integer.parseInt(request.getParameter("idtipopersona"));
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String correo = request.getParameter("correo");
                String codigoubidistrito = request.getParameter("codigoubidistrito");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanProveedor nuevo_proveedor = new BeanProveedor(idproveedor, idtipodocumento, numerodocumento, nombre, idtipopersona, direccion, telefono, correo, codigoubidistrito, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoProveedorImpl daoProveedorImpl = new DaoProveedorImpl();
                String msg = daoProveedorImpl.accionActualizar(nuevo_proveedor);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE EL PROVEEDOR</em>\" ";
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
                Integer idproveedor = Integer.parseInt(request.getParameter("idproveedor"));
                DaoProveedorImpl daoProveedorImpl = new DaoProveedorImpl();
                BeanProveedor beanProveedor = (BeanProveedor) daoProveedorImpl.accionObtener(idproveedor);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idproveedor", beanProveedor.getIdproveedor());
                obj.put("nombre", beanProveedor.getNombre());
                obj.put("tipodocumento", beanProveedor.getIdtipodocumento());
                obj.put("numerodocumento", beanProveedor.getNumerodocumento());
                obj.put("direccion", beanProveedor.getDireccion());
                obj.put("telefono", beanProveedor.getTelefono());
                obj.put("correo", beanProveedor.getCorreo());
                obj.put("tipopersona", beanProveedor.getIdtipopersona());
                obj.put("codigoubidistrito", beanProveedor.getCodigoubidistrito());
                obj.put("estado", beanProveedor.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproveedor = Integer.parseInt(request.getParameter("idproveedor"));
                DaoProveedorImpl daoProveedorImpl = new DaoProveedorImpl();
                String msg = daoProveedorImpl.accionEliminar(idproveedor);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE EL PROVEEDOR</em>\" ";
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
                Integer idproveedor = Integer.parseInt(request.getParameter("idproveedor"));
                DaoProveedorImpl daoProveedorImpl = new DaoProveedorImpl();
                String msg = daoProveedorImpl.accionActivar(idproveedor);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE EL PROVEEDOR</em>\" ";
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
