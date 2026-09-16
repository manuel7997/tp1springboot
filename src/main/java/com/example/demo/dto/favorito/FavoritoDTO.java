package com.example.demo.dto.favorito;

import java.time.LocalDateTime;

public record FavoritoDTO(
        Long id,
        Long productoId,
        String nombreProducto,
        String comentario,
        LocalDateTime fechaAgregado
) {
}
