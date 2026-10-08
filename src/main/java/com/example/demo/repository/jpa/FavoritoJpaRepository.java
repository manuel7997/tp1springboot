package com.example.demo.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data genera la implementación. Las consultas por lista son
 * derivadas del nombre (lista.id); no hace falta un @OneToMany del lado
 * de la lista.
 */
public interface FavoritoJpaRepository extends JpaRepository<FavoritoEntity, Long> {

    List<FavoritoEntity> findByListaId(Long listaId);

    boolean existsByListaId(Long listaId);
}
