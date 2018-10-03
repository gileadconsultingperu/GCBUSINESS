package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoCategoriaProductoImpl;
import gilead.gcbusiness.dao.impl.DaoLineaProductoImpl;
import gilead.gcbusiness.model.BeanCategoriaProducto;
import gilead.gcbusiness.model.BeanLineaProducto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_CategoriaProducto_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION CATEGORIA");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                List<BeanCategoriaProducto> listCategoriaProductos = daoCategoriaProductoImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listCategoriaProductos.size(); i++) {
                    String acciones = "";
                    
                    String descripcionLineaProducto = "";
                    
                    if(listCategoriaProductos.get(i).getIdlineaproducto()>0){
                        BeanLineaProducto lineaproducto = null;                  
                        lineaproducto = daoLineaProductoImpl.accionObtener(listCategoriaProductos.get(i).getIdlineaproducto());
                        descripcionLineaProducto= lineaproducto.getDescripcion();
                    }
                    
                    if (listCategoriaProductos.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listCategoriaProductos.get(i).getIdcategoriaproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listCategoriaProductos.get(i).getIdcategoriaproducto() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listCategoriaProductos.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listCategoriaProductos.get(i).getIdcategoriaproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listCategoriaProductos.get(i).getIdcategoriaproducto() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("lineaproducto", descripcionLineaProducto);
                    obj.put("estado", "A".equals(listCategoriaProductos.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("descripcion", listCategoriaProductos.get(i).getDescripcion());
                    obj.put("abreviatura", listCategoriaProductos.get(i).getAbreviatura());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR CATEGORIA");
            try (PrintWriter out = response.getWriter()) {
                Integer idlineaproducto = Integer.parseInt(request.getParameter("idlineaproducto"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanCategoriaProducto nuevo_categoriaproducto = new BeanCategoriaProducto(null, idlineaproducto, descripcion, abreviatura, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                String msg = daoCategoriaProductoImpl.accionCrear(nuevo_categoriaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE LA CATEGORIA</em>\" ";
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
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idlineaproducto = Integer.parseInt(request.getParameter("idlineaproducto"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanCategoriaProducto nuevo_categoriaproducto = new BeanCategoriaProducto(idcategoriaproducto, idlineaproducto, descripcion, abreviatura, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                String msg = daoCategoriaProductoImpl.accionActualizar(nuevo_categoriaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA CATEGORIA</em>\" ";
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
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                BeanCategoriaProducto beanCategoriaProducto = (BeanCategoriaProducto) daoCategoriaProductoImpl.accionObtener(idcategoriaproducto);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idcategoriaproducto", beanCategoriaProducto.getIdcategoriaproducto());
                obj.put("idlineaproducto", beanCategoriaProducto.getIdlineaproducto());
                obj.put("descripcion", beanCategoriaProducto.getDescripcion());
                obj.put("abreviatura", beanCategoriaProducto.getAbreviatura());
                obj.put("estado", beanCategoriaProducto.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                String msg = daoCategoriaProductoImpl.accionEliminar(idcategoriaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE LA CATEGORIA</em>\" ";
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
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                String msg = daoCategoriaProductoImpl.accionActivar(idcategoriaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE LA CATEGORIA</em>\" ";
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
