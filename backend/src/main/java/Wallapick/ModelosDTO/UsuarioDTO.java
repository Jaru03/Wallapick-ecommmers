package Wallapick.ModelosDTO;

import Wallapick.Modelos.Usuario;

import java.util.List;

public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private String name;
    private String lastname;


    public UsuarioDTO(Long id, String username, String email, String name, String lastname, List<ProductoDTO> vendedor, List<CompraDTO> compras) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.lastname = lastname;

    }

    public UsuarioDTO(Usuario user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastname = user.getLastname();
    }

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


}
