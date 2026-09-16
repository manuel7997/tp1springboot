package com.example.demo.repository;

import com.example.demo.model.Favorito;

import java.util.List;
import java.util.Optional;

/**
 * Contrato del almacenamiento de favoritos. La implementación en memoria
 * es {@link FavoritoRepositoryEnMemoria}; separar la interfaz permite
 * cambiar de implementación (por ejemplo a una con base de datos en el
 * TP2) sin tocar FavoritoService.
 */
public interface FavoritoRepository {

    List<Favorito> buscarTodos();

    Optional<Favorito> buscarPorId(Long id);

    boolean existePorId(Long id);

    Favorito guardar(Favorito favorito);

    void eliminarPorId(Long id);
}
