package com.example.demo.repository;

import com.example.demo.model.Favorito;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementación de FavoritoRepository basada en una colección en memoria
 * (sin JPA, sin base de datos). Los datos se pierden al reiniciar la
 * aplicación.
 */
@Repository
public class FavoritoRepositoryEnMemoria implements FavoritoRepository {

    private final Map<Long, Favorito> favoritos = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong(0);

    @Override
    public List<Favorito> buscarTodos() {
        return new ArrayList<>(favoritos.values());
    }

    @Override
    public Optional<Favorito> buscarPorId(Long id) {
        return Optional.ofNullable(favoritos.get(id));
    }

    @Override
    public boolean existePorId(Long id) {
        return favoritos.containsKey(id);
    }

    @Override
    public Favorito guardar(Favorito favorito) {
        if (favorito.getId() == null) {
            favorito.setId(secuencia.incrementAndGet());
        }
        favoritos.put(favorito.getId(), favorito);
        return favorito;
    }

    @Override
    public void eliminarPorId(Long id) {
        favoritos.remove(id);
    }
}
