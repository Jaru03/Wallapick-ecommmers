package Wallapick.Repositorios;

import Wallapick.Modelos.Compra;
import Wallapick.Modelos.Producto;
import Wallapick.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra,Long> {
    List<Compra> findByComprador_Id(Long Id);
}
