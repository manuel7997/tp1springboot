package com.example.demo.repository;

import com.example.demo.model.Favorito;

import java.util.List;
import java.util.Optional;

/**
 * Puerto (contrato) del almacenamiento de favoritos. El dominio y el
 * service solo conocen esta interfaz; la implementación concreta es el
 * adapter {@code FavoritoRepositoryAdapter} (JPA + PostgreSQL). En el TP1
 * la implementación era una colección en memoria y el service no se enteró
 * del cambio.
 */
public interface FavoritoRepository {

    List<Favorito> buscarTodos();

    Optional<Favorito> buscarPorId(Long id);

    boolean existePorId(Long id);

    Favorito guardar(Favorito favorito);

    void eliminarPorId(Long id);

    List<Favorito> buscarPorListaId(Long listaId);

    boolean existePorListaId(Long listaId);
}
