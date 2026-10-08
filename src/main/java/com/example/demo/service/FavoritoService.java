package com.example.demo.service;

import com.example.demo.dto.favorito.FavoritoDTO;
import com.example.demo.dto.favorito.FavoritoRequestDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.Favorito;
import com.example.demo.repository.FavoritoRepository;
import com.example.demo.repository.ListaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final ListaRepository listaRepository;

    public FavoritoService(FavoritoRepository favoritoRepository, ListaRepository listaRepository) {
        this.favoritoRepository = favoritoRepository;
        this.listaRepository = listaRepository;
    }

    public List<FavoritoDTO> listar() {
        return favoritoRepository.buscarTodos().stream()
                .map(this::aDTO)
                .toList();
    }

    public List<FavoritoDTO> listarPorLista(Long listaId) {
        validarListaExiste(listaId);
        return favoritoRepository.buscarPorListaId(listaId).stream()
                .map(this::aDTO)
                .toList();
    }

    public FavoritoDTO obtenerPorId(Long id) {
        return favoritoRepository.buscarPorId(id)
                .map(this::aDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un favorito con id " + id));
    }

    public FavoritoDTO crear(FavoritoRequestDTO request) {
        validarListaExiste(request.listaId());
        Favorito favorito = new Favorito(
                null,
                request.productoId(),
                request.nombreProducto(),
                request.comentario(),
                LocalDateTime.now(),
                request.listaId());
        return aDTO(favoritoRepository.guardar(favorito));
    }

    public FavoritoDTO actualizar(Long id, FavoritoRequestDTO request) {
        Favorito existente = favoritoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un favorito con id " + id));
        validarListaExiste(request.listaId());
        Favorito favorito = new Favorito(
                id,
                request.productoId(),
                request.nombreProducto(),
                request.comentario(),
                existente.getFechaAgregado(),
                request.listaId());
        return aDTO(favoritoRepository.guardar(favorito));
    }

    public void eliminar(Long id) {
        if (!favoritoRepository.existePorId(id)) {
            throw new RecursoNoEncontradoException("No existe un favorito con id " + id);
        }
        favoritoRepository.eliminarPorId(id);
    }

    private void validarListaExiste(Long listaId) {
        if (!listaRepository.existePorId(listaId)) {
            throw new RecursoNoEncontradoException("No existe una lista con id " + listaId);
        }
    }

    private FavoritoDTO aDTO(Favorito favorito) {
        return new FavoritoDTO(
                favorito.getId(),
                favorito.getProductoId(),
                favorito.getNombreProducto(),
                favorito.getComentario(),
                favorito.getFechaAgregado(),
                favorito.getListaId()
        );
    }
}
