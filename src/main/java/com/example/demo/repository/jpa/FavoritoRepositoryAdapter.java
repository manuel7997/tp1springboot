package com.example.demo.repository.jpa;

import com.example.demo.model.Favorito;
import com.example.demo.repository.FavoritoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Adapter del puerto {@link FavoritoRepository} sobre JPA/PostgreSQL.
 * Hacia adentro habla en objetos de dominio ({@link Favorito}); hacia
 * afuera usa {@link FavoritoEntity} y delega en Spring Data.
 */
@Repository
@Transactional(readOnly = true)
public class FavoritoRepositoryAdapter implements FavoritoRepository {

    private final FavoritoJpaRepository favoritoJpaRepository;
    private final ListaJpaRepository listaJpaRepository;

    public FavoritoRepositoryAdapter(FavoritoJpaRepository favoritoJpaRepository,
                                     ListaJpaRepository listaJpaRepository) {
        this.favoritoJpaRepository = favoritoJpaRepository;
        this.listaJpaRepository = listaJpaRepository;
    }

    @Override
    public List<Favorito> buscarTodos() {
        return favoritoJpaRepository.findAll().stream().map(this::aDominio).toList();
    }

    @Override
    public Optional<Favorito> buscarPorId(Long id) {
        return favoritoJpaRepository.findById(id).map(this::aDominio);
    }

    @Override
    public boolean existePorId(Long id) {
        return favoritoJpaRepository.existsById(id);
    }

    @Override
    @Transactional
    public Favorito guardar(Favorito favorito) {
        return aDominio(favoritoJpaRepository.save(aEntidad(favorito)));
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        favoritoJpaRepository.deleteById(id);
    }

    @Override
    public List<Favorito> buscarPorListaId(Long listaId) {
        return favoritoJpaRepository.findByListaId(listaId).stream().map(this::aDominio).toList();
    }

    @Override
    public boolean existePorListaId(Long listaId) {
        return favoritoJpaRepository.existsByListaId(listaId);
    }

    private Favorito aDominio(FavoritoEntity e) {
        return new Favorito(
                e.getId(),
                e.getProductoId(),
                e.getNombreProducto(),
                e.getComentario(),
                e.getFechaAgregado(),
                e.getLista().getId());
    }

    private FavoritoEntity aEntidad(Favorito f) {
        FavoritoEntity e = new FavoritoEntity();
        e.setId(f.getId());
        e.setProductoId(f.getProductoId());
        e.setNombreProducto(f.getNombreProducto());
        e.setComentario(f.getComentario());
        e.setFechaAgregado(f.getFechaAgregado());
        // Referencia por id, sin consultar la lista: el service ya validó que existe.
        e.setLista(listaJpaRepository.getReferenceById(f.getListaId()));
        return e;
    }
}
