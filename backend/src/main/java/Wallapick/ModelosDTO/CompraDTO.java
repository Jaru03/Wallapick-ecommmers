package Wallapick.ModelosDTO;

import Wallapick.Modelos.Compra;

import java.util.Date;

public class CompraDTO {
    private Long id;
    private String producto;
    private String comprador;
    private String vendedor;
    private Date fechaCompra;
    private double precioFinal;




    public CompraDTO(Compra c, boolean incluirProducto) {
        this.id = c.getId();

        if (incluirProducto && c.getProducto() != null) {
            this.producto = c.getProducto().getNombre(); // o cualquier otro atributo relevante
        }
        this.comprador = c.getComprador().getUsername();
        this.vendedor = c.getVendedor().getUsername();
        this.fechaCompra = c.getFechaCompra();
        this.precioFinal = c.getPrecioFinal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComprador() {
        return comprador;
    }

    public void setComprador(String comprador) {
        this.comprador = comprador;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String p) {
        this.producto = p;
    }
}
