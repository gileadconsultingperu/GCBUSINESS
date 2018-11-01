package gilead.gcbusiness.model;

import java.sql.Timestamp;

public class BeanCuentaPagar {
    
    private Integer idcuentapagar;
    private Integer idproveedor;
    private Integer idcomprobante;
    private Integer idtipocomprobante;
    private Double saldo;
    private String estado;
    private Timestamp fechaInsercion; 
    private String usuarioInsercion; 
    private String terminalInsercion;
    private String ipInsercion;
    private Timestamp fechaModificacion; 
    private String usuarioModificacion; 
    private String terminalModificacion;
    private String ipModificacion;
    
}
