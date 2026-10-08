package com.example.demo.controller;

import com.example.demo.dto.favorito.FavoritoDTO;
import com.example.demo.dto.lista.ListaDTO;
import com.example.demo.dto.lista.ListaRequestDTO;
import com.example.demo.dto.lista.MoverFavoritosRequestDTO;
import com.example.demo.dto.lista.MoverFavoritosResponseDTO;
import com.example.demo.service.ListaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/listas")
@Tag(name = "Listas", description = "Listas para organizar favoritos (por ejemplo \"Regalos\"), guardadas en PostgreSQL")
public class ListaController {

    private final ListaService listaService;

    public ListaController(ListaService listaService) {
        this.listaService = listaService;
    }

    @PostMapping
    @Operation(summary = "Crear una lista", description = "Crea una lista nueva. 400 si el nombre está vacío o supera los 100 caracteres.")
    public ResponseEntity<ListaDTO> crear(@Valid @RequestBody ListaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(listaService.crear(request));
    }

    @GetMapping
    @Operation(summary = "Listar listas", description = "Devuelve todas las listas existentes.")
    public List<ListaDTO> listar() {
        return listaService.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una lista por id", description = "404 si la lista no existe.")
    public ListaDTO obtenerPorId(@PathVariable Long id) {
        return listaService.obtenerPorId(id);
    }

    @GetMapping("/{id}/favoritos")
    @Operation(summary = "Favoritos de una lista", description = "Devuelve los favoritos que pertenecen a la lista. 404 si la lista no existe.")
    public List<FavoritoDTO> favoritosDeLista(@PathVariable Long id) {
        return listaService.favoritosDeLista(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una lista vacía",
            description = "204 si se eliminó; 404 si no existe; 409 si todavía tiene favoritos.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        listaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{origenId}/mover-favoritos")
    @Operation(summary = "Mover favoritos a otra lista y eliminar la lista origen",
            description = "Reasigna todos los favoritos de la lista origen a la lista destino y elimina la origen, "
                    + "todo en una única transacción (o se hace todo o no se hace nada). "
                    + "404 si alguna de las dos listas no existe; 400 si origen y destino son la misma.")
    public MoverFavoritosResponseDTO moverFavoritos(@PathVariable Long origenId,
                                                   @Valid @RequestBody MoverFavoritosRequestDTO request) {
        return listaService.moverFavoritos(origenId, request.listaDestinoId());
    }
}
