package Wallapick.Repositorios;

import Wallapick.Modelos.Compra;
import Wallapick.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepositorio extends JpaRepository<Compra,Long> {
}
