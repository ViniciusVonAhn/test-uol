package com.compasso.client.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="cidade")
public class CityEntity {

    public CityEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O Nome da cidade deve ser preenchido.")
    @NotBlank(message = "O Nome da cidade deve ser preenchido.")
    @Column(name = "nome")
    private String name;

    @NotNull(message = "O Estado da cidade deve ser preenchido.")
    @NotBlank(message = "O Estado da cidade deve ser preenchido.")
    @Column(name = "estado")
    private String state;

    @OneToMany(mappedBy = "cityEntity")
    private final List<ClientEntity> clientes = new ArrayList<ClientEntity>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityEntity that = (CityEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
