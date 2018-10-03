/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoClaseProductoImpl;
import gilead.gcbusiness.dao.impl.DaoFamiliaProductoImpl;
import gilead.gcbusiness.model.BeanClaseProducto;
import gilead.gcbusiness.model.BeanFamiliaProducto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pablo
 */
public class GCBusiness_ClaseProducto_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION CLASE");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                DaoFamiliaProductoImpl daoFamiliaProductoImpl = new DaoFamiliaProductoImpl();
                List<BeanClaseProducto> listClaseProductos = daoClaseProductoImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listClaseProductos.size(); i++) {
                    String acciones = "";
                    
                    String descripcionFamiliaProducto = "";
                    
                    if(listClaseProductos.get(i).getIdfamiliaproducto()>0){
                        BeanFamiliaProducto familiaproducto = null;                  
                        familiaproducto = daoFamiliaProductoImpl.accionObtener(listClaseProductos.get(i).getIdfamiliaproducto());
                        descripcionFamiliaProducto= familiaproducto.getDescripcion();
                    }
                    
                    if (listClaseProductos.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listClaseProductos.get(i).getIdclaseproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listClaseProductos.get(i).getIdclaseproducto() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listClaseProductos.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listClaseProductos.get(i).getIdclaseproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listClaseProductos.get(i).getIdclaseproducto() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("familiaproducto", descripcionFamiliaProducto);
                    obj.put("estado", "A".equals(listClaseProductos.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("descripcion", listClaseProductos.get(i).getDescripcion());
                    obj.put("abreviatura", listClaseProductos.get(i).getAbreviatura());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR CLASE");
            try (PrintWriter out = response.getWriter()) {
                Integer idfamiliaproducto = Integer.parseInt(request.getParameter("idfamiliaproducto"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanClaseProducto nuevo_claseproducto = new BeanClaseProducto(null, idfamiliaproducto, descripcion, abreviatura, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                String msg = daoClaseProductoImpl.accionCrear(nuevo_claseproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE LA CLASE</em>\" ";
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
                Integer idclaseproducto = Integer.parseInt(request.getParameter("idclaseproducto"));
                Integer idfamiliaproducto = Integer.parseInt(request.getParameter("idfamiliaproducto"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanClaseProducto nuevo_claseproducto = new BeanClaseProducto(idclaseproducto, idfamiliaproducto, descripcion, abreviatura, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                String msg = daoClaseProductoImpl.accionActualizar(nuevo_claseproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA CLASE</em>\" ";
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
                Integer idclaseproducto = Integer.parseInt(request.getParameter("idclaseproducto"));
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                BeanClaseProducto beanClaseProducto = (BeanClaseProducto) daoClaseProductoImpl.accionObtener(idclaseproducto);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idclaseproducto", beanClaseProducto.getIdclaseproducto());
                obj.put("idfamiliaproducto", beanClaseProducto.getIdfamiliaproducto());
                obj.put("descripcion", beanClaseProducto.getDescripcion());
                obj.put("abreviatura", beanClaseProducto.getAbreviatura());
                obj.put("estado", beanClaseProducto.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idclaseproducto = Integer.parseInt(request.getParameter("idclaseproducto"));
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                String msg = daoClaseProductoImpl.accionEliminar(idclaseproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE LA CLASE</em>\" ";
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
                Integer idclaseproducto = Integer.parseInt(request.getParameter("idclaseproducto"));
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                String msg = daoClaseProductoImpl.accionActivar(idclaseproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE LA CLASE</em>\" ";
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
