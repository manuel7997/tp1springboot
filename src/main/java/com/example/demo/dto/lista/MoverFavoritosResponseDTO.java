package com.example.demo.dto.lista;

/**
 * Resultado de mover los favoritos de una lista a otra: la lista origen
 * ya no existe, los favoritos quedaron en la lista destino.
 */
public record MoverFavoritosResponseDTO(
        Long listaOrigenEliminadaId,
        Long listaDestinoId,
        int favoritosMovidos
) {
}
