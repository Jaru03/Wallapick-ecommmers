package Wallapick.Modelos;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vendedor_id")
    private Usuario vendedor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP) //mapea la fecha en formato yyyy-MM-dd HH:mm:ss
    private Date fechaCompra;

    @NotNull
    private double precioFinal;

    public Compra() {}

    public Compra(Producto producto, Usuario vendedor, Usuario comprador, Date fechaCompra, double precioFinal) {
        this.producto = producto;
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.fechaCompra = fechaCompra;
        this.precioFinal = precioFinal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public Usuario getComprador() {
        return comprador;
    }

    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
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
}
