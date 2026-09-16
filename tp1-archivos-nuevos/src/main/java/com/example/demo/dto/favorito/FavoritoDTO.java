package com.example.demo.dto.favorito;

public record FavoritoDTO(
        Long id,
        Long productoId,
        String nombreProducto,
        String comentario
) {
}
