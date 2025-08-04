package Wallapick.Modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Arrays;
import java.util.Date;

@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    @Column(length = 1000)
    private String descripcion;

    @NotNull
    private String category; //No enum puesto que al recibirlo de front, se recibira desde un checkbox

    @Positive
    private double precio;

    private boolean enVenta = true;

    @PastOrPresent
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion = new Date();

    @ManyToOne
    @JoinColumn(name = "id_vendedor", nullable = false)
    private Usuario vendedor;

    // Relación 1:1 con Compra (un producto puede ser comprado una sola vez)
    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnore
    private Compra compra;

    //private byte[] image;

    private String estado; //No enum puesto que al recibirlo de front, se recibira desde un checkbox

    public Producto() {
    }
    public Producto(Long id, String nombre, String descripcion, String category, double precio, boolean enVenta, Date fechaPublicacion, Usuario vendedor /*,byte[] image */, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.category = category;
        this.precio = precio;
        this.enVenta = enVenta;
        this.fechaPublicacion = fechaPublicacion;
        this.vendedor = vendedor;
        //this.image = image;
        this.estado = estado;
    }

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

/*
    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
*/
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
