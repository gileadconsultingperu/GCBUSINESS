package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoSucursalImpl;
import gilead.gcbusiness.model.BeanSucursal;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Sucursal_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION SUCURSAL");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                List<BeanSucursal> listSucursales = daoSucursalImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listSucursales.size(); i++) {
                    String acciones = "";
                    if (listSucursales.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listSucursales.get(i).getIdsucursal() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listSucursales.get(i).getIdsucursal() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listSucursales.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listSucursales.get(i).getIdsucursal() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listSucursales.get(i).getIdsucursal() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("telefono", listSucursales.get(i).getTelefono());
                    obj.put("direccion", listSucursales.get(i).getDireccion());
                    obj.put("estado", "A".equals(listSucursales.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("descripcion", listSucursales.get(i).getDescripcion());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR SUCURSAL");
            try (PrintWriter out = response.getWriter()) {
                String descripcion = request.getParameter("descripcion");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanSucursal nuevo_sucursal = new BeanSucursal(null, 1, descripcion, direccion, telefono, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                String msg = daoSucursalImpl.accionCrear(nuevo_sucursal);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE LA SUCURSAL</em>\" ";
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
                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                String descripcion = request.getParameter("descripcion");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanSucursal nuevo_sucursal = new BeanSucursal(idsucursal, 1, descripcion, direccion, telefono, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                String msg = daoSucursalImpl.accionActualizar(nuevo_sucursal);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA SUCURSAL</em>\" ";
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
                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                BeanSucursal beanSucursal = (BeanSucursal) daoSucursalImpl.accionObtener(idsucursal);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idsucursal", beanSucursal.getIdsucursal());
                obj.put("descripcion", beanSucursal.getDescripcion());
                obj.put("direccion", beanSucursal.getDireccion());
                obj.put("telefono", beanSucursal.getTelefono());
                obj.put("estado", beanSucursal.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                String msg = daoSucursalImpl.accionEliminar(idsucursal);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE LA SUCURSAL</em>\" ";
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
                Integer idsucursal = Integer.parseInt(request.getParameter("idsucursal"));
                DaoSucursalImpl daoSucursalImpl = new DaoSucursalImpl();
                String msg = daoSucursalImpl.accionActivar(idsucursal);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE LA SUCURSAL</em>\" ";
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
