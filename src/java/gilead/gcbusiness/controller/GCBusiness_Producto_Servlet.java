package gilead.gcbusiness.controller;

import gilead.gcbusiness.dao.impl.DaoAlmacenProductoImpl;
import gilead.gcbusiness.dao.impl.DaoCategoriaProductoImpl;
import gilead.gcbusiness.dao.impl.DaoClaseProductoImpl;
import gilead.gcbusiness.dao.impl.DaoFamiliaProductoImpl;
import gilead.gcbusiness.dao.impl.DaoLineaProductoImpl;
import gilead.gcbusiness.dao.impl.DaoMarcaImpl;
import gilead.gcbusiness.dao.impl.DaoProductoImpl;
import gilead.gcbusiness.dao.impl.DaoUnidadMedidaImpl;
import gilead.gcbusiness.model.BeanCategoriaProducto;
import gilead.gcbusiness.model.BeanClaseProducto;
import gilead.gcbusiness.model.BeanFamiliaProducto;
import gilead.gcbusiness.model.BeanLineaProducto;
import gilead.gcbusiness.model.BeanMarca;
import gilead.gcbusiness.model.BeanProducto;
import gilead.gcbusiness.model.BeanUnidadMedida;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GCBusiness_Producto_Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtengo la sesion activa
        HttpSession session = request.getSession(false);    
        
        String opcion = request.getParameter("opcion");     
        System.out.println("Entro GESTION PRODUCTO");
        if (opcion.equals("listar")) {
            try (PrintWriter out = response.getWriter()) {
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                DaoMarcaImpl daoMarcaImpl = new DaoMarcaImpl();
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                DaoUnidadMedidaImpl daoUnidadMedidaImpl = new DaoUnidadMedidaImpl();
                List<BeanProducto> listProductos = daoProductoImpl.accionListar();

                org.json.simple.JSONArray datos = new org.json.simple.JSONArray();

                for (int i = 0; i < listProductos.size(); i++) {
                    String acciones = "";
                   
                    BeanMarca marca = null;                  
                    marca = daoMarcaImpl.accionObtener(listProductos.get(i).getIdmarca());
                     
                    BeanCategoriaProducto categoria = null;                  
                    categoria = daoCategoriaProductoImpl.accionObtener(listProductos.get(i).getIdcategoriaproducto());
                    
                    BeanUnidadMedida unidadmedida = null;                  
                    unidadmedida = daoUnidadMedidaImpl.accionObtener(listProductos.get(i).getIdunidadmedida());
                    
                    if (listProductos.get(i).getEstado().equals("A")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='eliminar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-danger eliminar' title='Inactivar'><i class=\"ace-icon fa fa-remove bigger-120\"></i></button>";
                    }
                    if (listProductos.get(i).getEstado().equals("I")) {
                        acciones = "<div class=\"hidden-sm hidden-xs btn-group\">"
                                + "<button type='button' name='actualizar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-info actualizar' title='Actualizar'><i class=\"ace-icon fa fa-pencil bigger-120\"></i></button>"
                                + "<button type='button' name='activar' id='" + listProductos.get(i).getIdproducto() + "' class='btn btn-xs btn-grey activar' title='Activar'><i class=\"ace-icon fa fa-check bigger-120\"></i></button>";
                    }

                    org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                    obj.put("codigo", listProductos.get(i).getCodigo());
                    obj.put("descripcion", listProductos.get(i).getDescripcion());
                    obj.put("marca", marca.getDescripcion());
                    obj.put("categoriaproducto", categoria.getDescripcion());
                    obj.put("unidadmedida", unidadmedida.getDescripcion());
                    obj.put("estado", "A".equals(listProductos.get(i).getEstado())?"ACTIVO":"INACTIVO");
                    obj.put("acciones", acciones);
                    datos.add(obj);
                }

                out.print(" {\"data\":" + datos.toJSONString() + "} ");
            }
        }else if (opcion.equals("insertar")) {  
            System.out.println("INSERTAR PRODUCTO");
            try (PrintWriter out = response.getWriter()) {
                Integer idmarca = Integer.parseInt(request.getParameter("idmarca"));
                String descripcion = request.getParameter("descripcion");
                String codigo = request.getParameter("codigo");
                String afectoigv = request.getParameter("afectoigv");
                String afectoisc = request.getParameter("afectoisc");
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idunidadmedida = Integer.parseInt(request.getParameter("idunidadmedida"));
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                Integer idtipoproducto = Integer.parseInt(request.getParameter("idtipoproducto"));
                Double preciocompra = Double.parseDouble(request.getParameter("preciocompra"));
                Integer idtipoisc = Integer.parseInt(request.getParameter("idtipoisc"));
                Double baseisc = Double.parseDouble(request.getParameter("baseisc"));
                Double factorisc = Double.parseDouble(request.getParameter("factorisc"));
                String codigoproveedor = request.getParameter("codigoproveedor");
                Double pesoproveedor = Double.parseDouble(request.getParameter("pesoproveedor"));
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanProducto nuevo_producto = new BeanProducto(null, idmarca, idcategoriaproducto, idmoneda, idtipoproducto, idunidadmedida, codigo, null, descripcion, afectoigv, afectoisc, idtipoisc, baseisc, factorisc, 0.00, 0.00, preciocompra, preciocompra, null, "A", codigoproveedor, pesoproveedor, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr(), null, null, null, null);
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionCrear(nuevo_producto);
                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE CREÓ CORRECTAMENTE EL PRODUCTO</em>\" ";
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
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                Integer idmarca = Integer.parseInt(request.getParameter("idmarca"));
                String descripcion = request.getParameter("descripcion");
                String codigo = request.getParameter("codigo");
                String afectoigv = request.getParameter("afectoigv");
                String afectoisc = request.getParameter("afectoisc");
                Integer idcategoriaproducto = Integer.parseInt(request.getParameter("idcategoriaproducto"));
                Integer idunidadmedida = Integer.parseInt(request.getParameter("idunidadmedida"));
                Integer idmoneda = Integer.parseInt(request.getParameter("idmoneda"));
                Integer idtipoproducto = Integer.parseInt(request.getParameter("idtipoproducto"));
                Double preciocompra = Double.parseDouble(request.getParameter("preciocompra"));
                Integer idtipoisc = Integer.parseInt(request.getParameter("idtipoisc"));
                Double baseisc = Double.parseDouble(request.getParameter("baseisc"));
                Double factorisc = Double.parseDouble(request.getParameter("factorisc"));
                String codigoproveedor = request.getParameter("codigoproveedor");
                Double pesoproveedor = Double.parseDouble(request.getParameter("pesoproveedor"));
                String estado = request.getParameter("estado");
                java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate.getTime());
                String login_usuario = (String) session.getAttribute("login_usuario");
                BeanProducto nuevo_producto = new BeanProducto(idproducto, idmarca, idcategoriaproducto, idmoneda, idtipoproducto, idunidadmedida, codigo, null, descripcion, afectoigv, afectoisc, idtipoisc, baseisc, factorisc, 0.00, 0.00, preciocompra, preciocompra, null, estado, codigoproveedor, pesoproveedor, null, null, null, null, ts, login_usuario, request.getRemoteHost(), request.getRemoteAddr());
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionActualizar(nuevo_producto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTUALIZÓ CORRECTAMENTE EL PRODUCTO</em>\" ";
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
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                BeanProducto beanProducto = (BeanProducto) daoProductoImpl.accionObtener(idproducto);
                
                DaoCategoriaProductoImpl daoCategoriaProductoImpl = new DaoCategoriaProductoImpl();
                BeanCategoriaProducto beanCategoriaProducto = (BeanCategoriaProducto) daoCategoriaProductoImpl.accionObtener(beanProducto.getIdcategoriaproducto());
                
                DaoLineaProductoImpl daoLineaProductoImpl = new DaoLineaProductoImpl();
                BeanLineaProducto beanLineaProducto = (BeanLineaProducto) daoLineaProductoImpl.accionObtener(beanCategoriaProducto.getIdlineaproducto());
                
                DaoClaseProductoImpl daoClaseProductoImpl = new DaoClaseProductoImpl();
                BeanClaseProducto beanClaseProducto = (BeanClaseProducto) daoClaseProductoImpl.accionObtener(beanLineaProducto.getIdclaseproducto());
                
                //DaoFamiliaProductoImpl daoFamiliaProductoImpl = new DaoFamiliaProductoImpl();
                //BeanFamiliaProducto beanFamiliaProducto = (BeanFamiliaProducto) daoFamiliaProductoImpl.accionObtener(beanClaseProducto.getIdfamiliaproducto());
                
                org.json.simple.JSONObject obj = new org.json.simple.JSONObject();
                obj.put("idproducto", beanProducto.getIdproducto());
                obj.put("codigo", beanProducto.getCodigo());
                obj.put("descripcion", beanProducto.getDescripcion());
                obj.put("familiaproducto", beanClaseProducto.getIdfamiliaproducto());
                obj.put("claseproducto", beanClaseProducto.getIdclaseproducto());
                obj.put("lineaproducto", beanLineaProducto.getIdlineaproducto());
                obj.put("categoriaproducto", beanCategoriaProducto.getIdcategoriaproducto());
                obj.put("afectoigv", beanProducto.getAfectoIGV());
                obj.put("afectoisc", beanProducto.getAfectoISC());
                obj.put("marca", beanProducto.getIdmarca());
                obj.put("unidadmedida", beanProducto.getIdunidadmedida());
                obj.put("moneda", beanProducto.getIdmoneda());
                obj.put("tipoproducto", beanProducto.getIdtipoproducto());
                obj.put("preciocompra", beanProducto.getPreciocompra());
                obj.put("tipoisc", beanProducto.getIdtipoISC());                
                obj.put("baseisc", beanProducto.getBaseISC());
                obj.put("factorisc", beanProducto.getFactorISC());
                obj.put("codigoproveedor", beanProducto.getCodigoproveedor());
                obj.put("pesoproveedor", beanProducto.getPesoproveedor());
                obj.put("estado", beanProducto.getEstado());
                out.print(obj.toJSONString());

                System.out.println("buscar -- " + obj.toJSONString());
            }
        } else if (opcion.equals("eliminar")) {
            try (PrintWriter out = response.getWriter()) {
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionEliminar(idproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE INACTIVÓ CORRECTAMENTE EL PRODUCTO</em>\" ";
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
                Integer idproducto = Integer.parseInt(request.getParameter("idproducto"));
                DaoProductoImpl daoProductoImpl = new DaoProductoImpl();
                String msg = daoProductoImpl.accionActivar(idproducto);

                String json = null;
                if (msg == null) {
                    json = "{ \"mensaje\":\"<em>SE ACTIVÓ CORRECTAMENTE EL PRODUCTO</em>\" ";
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
