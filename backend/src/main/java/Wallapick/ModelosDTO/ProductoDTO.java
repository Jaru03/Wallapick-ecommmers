package Wallapick.ModelosDTO;

import Wallapick.Modelos.Producto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean enVenta;
    private Date fechaPublicacion;
    private UsuarioDTO vendedor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CompraDTO compra;

    // Constructor completo
    public ProductoDTO(Long id, String nombre, String descripcion, double precio, boolean enVenta, Date fechaPublicacion, UsuarioDTO vendedor, CompraDTO compra) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.enVenta = enVenta;
        this.fechaPublicacion = fechaPublicacion;
        this.vendedor = vendedor;
        this.compra = compra;
    }

    // Constructor por defecto – incluye compra solo si enVenta es false
    public ProductoDTO(Producto p) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.descripcion = p.getDescripcion();
        this.precio = p.getPrecio();
        this.enVenta = p.isEnVenta();
        this.fechaPublicacion = p.getFechaPublicacion();
        this.vendedor = new UsuarioDTO(p.getVendedor());

        // Solo se incluye la compra si el producto no está en venta
        if (!this.enVenta && p.getCompra() != null) {
            this.compra = new CompraDTO(p.getCompra(), false); // evita recursión
        }
    }

    public ProductoDTO() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isEnVenta() {
        return enVenta;
    }

    public void setEnVenta(boolean enVenta) {
        this.enVenta = enVenta;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public UsuarioDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(UsuarioDTO vendedor) {
        this.vendedor = vendedor;
    }

    public CompraDTO getCompra() {
        return !this.enVenta ? this.compra : null;
    }

    public void setCompra(CompraDTO compra) {
        this.compra = compra;
    }
}
