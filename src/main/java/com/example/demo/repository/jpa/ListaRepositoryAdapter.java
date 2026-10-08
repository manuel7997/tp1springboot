package com.example.demo.repository.jpa;

import com.example.demo.model.Lista;
import com.example.demo.repository.ListaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Adapter del puerto {@link ListaRepository} sobre JPA/PostgreSQL. */
@Repository
@Transactional(readOnly = true)
public class ListaRepositoryAdapter implements ListaRepository {

    private final ListaJpaRepository listaJpaRepository;

    public ListaRepositoryAdapter(ListaJpaRepository listaJpaRepository) {
        this.listaJpaRepository = listaJpaRepository;
    }

    @Override
    public List<Lista> buscarTodas() {
        return listaJpaRepository.findAll().stream().map(this::aDominio).toList();
    }

    @Override
    public Optional<Lista> buscarPorId(Long id) {
        return listaJpaRepository.findById(id).map(this::aDominio);
    }

    @Override
    public boolean existePorId(Long id) {
        return listaJpaRepository.existsById(id);
    }

    @Override
    @Transactional
    public Lista guardar(Lista lista) {
        ListaEntity guardada = listaJpaRepository.save(new ListaEntity(lista.getId(), lista.getNombre()));
        return aDominio(guardada);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        listaJpaRepository.deleteById(id);
    }

    private Lista aDominio(ListaEntity e) {
        return new Lista(e.getId(), e.getNombre());
    }
}
