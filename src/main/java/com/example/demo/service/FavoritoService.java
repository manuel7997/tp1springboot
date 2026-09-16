package com.example.demo.service;

import com.example.demo.dto.favorito.FavoritoDTO;
import com.example.demo.dto.favorito.FavoritoRequestDTO;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.Favorito;
import com.example.demo.repository.FavoritoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;

    public FavoritoService(FavoritoRepository favoritoRepository) {
        this.favoritoRepository = favoritoRepository;
    }

    public List<FavoritoDTO> listar() {
        return favoritoRepository.buscarTodos().stream()
                .map(this::aDTO)
                .toList();
    }

    public FavoritoDTO obtenerPorId(Long id) {
        return favoritoRepository.buscarPorId(id)
                .map(this::aDTO)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un favorito con id " + id));
    }

    public FavoritoDTO crear(FavoritoRequestDTO request) {
        Favorito favorito = new Favorito(
                null,
                request.productoId(),
                request.nombreProducto(),
                request.comentario(),
                LocalDateTime.now());
        return aDTO(favoritoRepository.guardar(favorito));
    }

    public FavoritoDTO actualizar(Long id, FavoritoRequestDTO request) {
        Favorito existente = favoritoRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un favorito con id " + id));
        Favorito favorito = new Favorito(
                id,
                request.productoId(),
                request.nombreProducto(),
                request.comentario(),
                existente.getFechaAgregado());
        return aDTO(favoritoRepository.guardar(favorito));
    }

    public void eliminar(Long id) {
        if (!favoritoRepository.existePorId(id)) {
            throw new RecursoNoEncontradoException("No existe un favorito con id " + id);
        }
        favoritoRepository.eliminarPorId(id);
    }

    private FavoritoDTO aDTO(Favorito favorito) {
        return new FavoritoDTO(
                favorito.getId(),
                favorito.getProductoId(),
                favorito.getNombreProducto(),
                favorito.getComentario(),
                favorito.getFechaAgregado()
        );
    }
}
