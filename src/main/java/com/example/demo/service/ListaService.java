package com.example.demo.service;

import com.example.demo.dto.favorito.FavoritoDTO;
import com.example.demo.dto.lista.ListaDTO;
import com.example.demo.dto.lista.ListaRequestDTO;
import com.example.demo.dto.lista.MoverFavoritosResponseDTO;
import com.example.demo.exception.ConflictoException;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.exception.SolicitudInvalidaException;
import com.example.demo.model.Favorito;
import com.example.demo.model.Lista;
import com.example.demo.repository.FavoritoRepository;
import com.example.demo.repository.ListaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ListaService {

    private final ListaRepository listaRepository;
    private final FavoritoRepository favoritoRepository;
    private final FavoritoService favoritoService;

    public ListaService(ListaRepository listaRepository,
                        FavoritoRepository favoritoRepository,
                        FavoritoService favoritoService) {
        this.listaRepository = listaRepository;
        this.favoritoRepository = favoritoRepository;
        this.favoritoService = favoritoService;
    }

    public ListaDTO crear(ListaRequestDTO request) {
        Lista lista = new Lista(null, request.nombre().trim());
        return aDTO(listaRepository.guardar(lista));
    }

    public List<ListaDTO> listar() {
        return listaRepository.buscarTodas().stream()
                .map(this::aDTO)
                .toList();
    }

    public ListaDTO obtenerPorId(Long id) {
        return listaRepository.buscarPorId(id)
                .map(this::aDTO)
                .orElseThrow(() -> noExiste(id));
    }

    public List<FavoritoDTO> favoritosDeLista(Long id) {
        return favoritoService.listarPorLista(id);
    }

    /**
     * Elimina una lista solo si está vacía. Si todavía tiene favoritos
     * responde 409 en vez de dejar que la base falle con un 500.
     */
    @Transactional
    public void eliminar(Long id) {
        if (!listaRepository.existePorId(id)) {
            throw noExiste(id);
        }
        if (favoritoRepository.existePorListaId(id)) {
            throw new ConflictoException(
                    "No se puede eliminar la lista " + id + ": todavía tiene favoritos");
        }
        listaRepository.eliminarPorId(id);
    }

    /**
     * Reasigna todos los favoritos de la lista origen a la lista destino y
     * elimina la lista origen. Es una operación atómica: con
     * {@code @Transactional}, si cualquier escritura falla se hace rollback
     * de todas las anteriores y la base queda como estaba.
     */
    @Transactional
    public MoverFavoritosResponseDTO moverFavoritos(Long origenId, Long destinoId) {
        if (origenId.equals(destinoId)) {
            throw new SolicitudInvalidaException("La lista origen y la lista destino no pueden ser la misma");
        }
        if (!listaRepository.existePorId(origenId)) {
            throw noExiste(origenId);
        }
        if (!listaRepository.existePorId(destinoId)) {
            throw noExiste(destinoId);
        }

        List<Favorito> favoritos = favoritoRepository.buscarPorListaId(origenId);
        for (Favorito favorito : favoritos) {
            favorito.setListaId(destinoId);
            favoritoRepository.guardar(favorito);
        }
        listaRepository.eliminarPorId(origenId);

        return new MoverFavoritosResponseDTO(origenId, destinoId, favoritos.size());
    }

    private RecursoNoEncontradoException noExiste(Long id) {
        return new RecursoNoEncontradoException("No existe una lista con id " + id);
    }

    private ListaDTO aDTO(Lista lista) {
        return new ListaDTO(lista.getId(), lista.getNombre());
    }
}
