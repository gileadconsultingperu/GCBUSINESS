package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoLineaProductoImpl;
import gilead.gcbusiness.dao.impl.DaoClaseProductoImpl;
import gilead.gcbusiness.model.BeanLineaProducto;
import gilead.gcbusiness.model.BeanClaseProducto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_LineaProducto_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION LINEA");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                List<BeanLineaProducto> listLineaProductos = daoLineaProductoImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listLineaProductos.size(); i++) {
                    String acciones = "";
                    
                    String descripcionClaseProducto = "";
                    
                    if(listLineaProductos.get(i).getIdclaseproducto()>0){
                        BeanClaseProducto claseproducto = null;                  
                        claseproducto = daoClaseProductoImpl.accionObtener(listLineaProductos.get(i).getIdclaseproducto());
                        descripcionClaseProducto= claseproducto.getDescripcion();
                    }
                    
                    if (listLineaProductos.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listLineaProductos.get(i).getIdlineaproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listLineaProductos.get(i).getIdlineaproducto() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listLineaProductos.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listLineaProductos.get(i).getIdlineaproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listLineaProductos.get(i).getIdlineaproducto() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("nro", (i+1));
                    obj.put("claseproducto", descripcionClaseProducto);
                    obj.put("estado", "A".equals(listLineaProductos.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("descripcion", listLineaProductos.get(i).getDescripcion());
                    obj.put("abreviatura", listLineaProductos.get(i).getAbreviatura());
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR LINEA");
            try (PrintWriter out = response.getWriter()) {
                Integer idclaseproducto = Integer.parseInt(request.getParameter("idclaseproducto"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanLineaProducto nuevo_lineaproducto = new BeanLineaProducto(null, idclaseproducto, descripcion, abreviatura, "A", ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                String msg = daoLineaProductoImpl.accionCrear(nuevo_lineaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE LA LINEA</em>\" ";
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
                Integer idlineaproducto = Integer.parseInt(request.getParameter("idlineaproducto"));
                Integer idclaseproducto = Integer.parseInt(request.getParameter("idclaseproducto"));
                String descripcion = request.getParameter("descripcion");
                String abreviatura = request.getParameter("abreviatura");
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanLineaProducto nuevo_lineaproducto = new BeanLineaProducto(idlineaproducto, idclaseproducto, descripcion, abreviatura, estado, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                String msg = daoLineaProductoImpl.accionActualizar(nuevo_lineaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE LA LINEA</em>\" ";
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
                Integer idlineaproducto = Integer.parseInt(request.getParameter("idlineaproducto"));
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                BeanLineaProducto beanLineaProducto = (BeanLineaProducto) daoLineaProductoImpl.accionObtener(idlineaproducto);

                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idlineaproducto", beanLineaProducto.getIdlineaproducto());
                obj.put("idclaseproducto", beanLineaProducto.getIdclaseproducto());
                obj.put("descripcion", beanLineaProducto.getDescripcion());
                obj.put("abreviatura", beanLineaProducto.getAbreviatura());
                obj.put("estado", beanLineaProducto.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idlineaproducto = Integer.parseInt(request.getParameter("idlineaproducto"));
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                String msg = daoLineaProductoImpl.accionEliminar(idlineaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE LA LINEA</em>\" ";
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
                Integer idlineaproducto = Integer.parseInt(request.getParameter("idlineaproducto"));
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                String msg = daoLineaProductoImpl.accionActivar(idlineaproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE LA LINEA</em>\" ";
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        DaoLineaProductoImpl daoClase = new DaoLineaProductoImpl();
        
        Integer claseproducto = Integer.parseInt(request.getParameter("claseproducto"));
        
        if(claseproducto>0){
            List<BeanLineaProducto> listaLinea = daoClase.accionListar();
            
            int contador=0;
            
            if(listaLinea.size()>0){
                for(BeanLineaProducto lineaproducto : listaLinea){
                    if(lineaproducto.getIdclaseproducto() == claseproducto){
                        out.println("<option value='"+lineaproducto.getIdlineaproducto()+"' >"
                                +lineaproducto.getDescripcion()+"</option>");
                        contador++;
                    }
                }
            }else{
                out.println("<option value='0' selected>NO HAY LÍNEAS</option>");
            }
            
            if(contador==0)
                out.println("<option value='0'>NO HAY LÍNEAS</option>");
        }else{
            out.println("<option value='0'>NO HAY LÍNEAS</option>");
        }
    }

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
