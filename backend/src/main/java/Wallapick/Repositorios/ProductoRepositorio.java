package Wallapick.Repositorios;

import Wallapick.Modelos.Producto;
import Wallapick.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombreParcial);
    List<Producto> findByVendedor_Id(Long vendedorId);

}
